package cn.yh.ysyx.activity.service.impl;

import cn.yh.ysyx.activity.service.CouponRangeService;
import cn.yh.ysyx.enums.CouponRangeType;
import cn.yh.ysyx.model.activity.CouponInfo;
import cn.yh.ysyx.activity.mapper.CouponInfoMapper;
import cn.yh.ysyx.model.activity.CouponRange;
import cn.yh.ysyx.model.product.Category;
import cn.yh.ysyx.model.product.SkuInfo;
import cn.yh.ysyx.product.ProductFeignClient;
import cn.yh.ysyx.vo.activity.CouponRuleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yh.ysyx.activity.service.CouponInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 优惠券信息 服务实现类
 * </p>
 *
 * @author hd
 * @since 2025-10-17
 */
@Slf4j
@Service
@Transactional
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements CouponInfoService {

    @Resource
    private CouponRangeService couponRangeService;
    @Resource
    private ProductFeignClient productFeignClient;

    /**
     * 分页查询
     * @param pageParam
     * @return IPage<CouponInfo>
     * @throws
     */
    @Override
    public IPage<CouponInfo> selectPage(Page<CouponInfo> pageParam) {
        LambdaQueryWrapper<CouponInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(CouponInfo::getId);
        IPage<CouponInfo> couponInfoPage = baseMapper.selectPage(pageParam, queryWrapper);
        couponInfoPage.getRecords().forEach(couponInfo -> {
            couponInfo.setCouponTypeString(couponInfo.getCouponType().getComment());
            couponInfo.setRangeTypeString(couponInfo.getRangeType().getComment());
        });
        return couponInfoPage;
    }

    /**
     * 根据优惠卷id获取优惠券规则信息
     * @param couponId
     * @return Map<Object>
     * @throws
     */
    @Override
    public Map<String, Object> findCouponRuleList(Long couponId) {
        Map<String, Object> result = new HashMap<>();
        // 优惠卷信息
        CouponInfo couponInfo = baseMapper.selectById(couponId);

        // 根据优惠卷id获取优惠卷分类集合
        List<CouponRange> couponRangeList = couponRangeService.list(
                new LambdaQueryWrapper<CouponRange>().eq(CouponRange::getCouponId, couponId)
        );
        List<Long> idList = couponRangeList.stream()
                .map(CouponRange::getRangeId)
                .collect(Collectors.toList());

        // 优惠卷类型
        CouponRangeType rangeType = couponInfo.getRangeType();
        // 判断优惠卷类型
        if (rangeType == CouponRangeType.SKU) {
            List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoList(idList);
            result.put("skuInfoList", skuInfoList);
        } else if (rangeType == CouponRangeType.CATEGORY){
            List<Category> categoryList = productFeignClient.findCategoryList(idList);
            result.put("categoryList", categoryList);
        }
        return result;
    }

    /**
     * 新增优惠规则
     * @param couponRuleVo
     * @return
     * @throws
     */
    @Override
    public void saveCouponRule(CouponRuleVo couponRuleVo) {
        Long couponId = couponRuleVo.getCouponId(); // 优惠卷id
        // 根据优惠卷id删除原有范围列表
        couponRangeService.remove(
                new LambdaUpdateWrapper<CouponRange>().eq(CouponRange::getCouponId, couponId)
        );
        // 更新优惠卷信息
        CouponInfo couponInfo = baseMapper.selectById(couponId);
        couponInfo.setRangeType(couponRuleVo.getRangeType()); // 范围类型
        couponInfo.setAmount(couponRuleVo.getAmount());       // 优惠金额
        couponInfo.setConditionAmount(couponRuleVo.getConditionAmount()); // 使用门槛金额
        baseMapper.updateById(couponInfo);
        // 新增优惠卷范围列表
        Set<CouponRange> couponRangeList = new HashSet<>(couponRuleVo.getCouponRangeList()); // Set集合去重
        couponRangeList.forEach(couponRange -> {
            couponRange.setCouponId(couponId);
        });
        couponRangeService.saveBatch(couponRangeList);
    }
}

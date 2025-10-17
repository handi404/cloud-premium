package cn.yh.ysyx.activity.service.impl;

import cn.yh.ysyx.activity.service.ActivityRuleService;
import cn.yh.ysyx.activity.service.ActivitySkuService;
import cn.yh.ysyx.model.activity.ActivityInfo;
import cn.yh.ysyx.activity.mapper.ActivityInfoMapper;
import cn.yh.ysyx.model.activity.ActivityRule;
import cn.yh.ysyx.model.activity.ActivitySku;
import cn.yh.ysyx.model.product.SkuInfo;
import cn.yh.ysyx.product.ProductFeignClient;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yh.ysyx.activity.service.ActivityInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 活动表 服务实现类
 * </p>
 *
 * @author hd
 * @since 2025-10-17
 */
@Slf4j
@Service
@Transactional
public class ActivityInfoServiceImpl extends ServiceImpl<ActivityInfoMapper, ActivityInfo> implements ActivityInfoService {

    @Resource
    private ActivityRuleService activityRuleService;
    @Resource
    private ActivitySkuService activitySkuService;
    @Resource
    private ProductFeignClient productFeignClient;

    /**
     * 活动分页列表
     * @param pageParam     封装页码与页大小
     * @return IPage<Admin> 分页查询结果
     * @throws
     */
    @Override
    public IPage<ActivityInfo> selectPage(Page<ActivityInfo> pageParam) {
        LambdaQueryWrapper<ActivityInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(ActivityInfo::getId);
        IPage<ActivityInfo> activityInfoPage = baseMapper.selectPage(pageParam, queryWrapper);
        activityInfoPage.getRecords().forEach(
                activityInfo -> activityInfo.setActivityTypeString(activityInfo.getActivityType().getComment())
        );
        return activityInfoPage;
    }

    /**
     * 活动规则列表
     * @param activityId
     * @return Map<Object>
     * @throws
     */
    @Override
    public Map<String, Object> findActivityRuleList(Long activityId) {
        Map<String, Object> result = new HashMap<>();

        // 活动规则列表
        LambdaQueryWrapper<ActivityRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ActivityRule::getActivityId, activityId);
        List<ActivityRule> activityRuleList = activityRuleService.list(queryWrapper);
        result.put("activityRuleList", activityRuleList);

        // 活动范围列表: activity_sku 是 activity_info 与 sku_info 的中间表
        List<ActivitySku> activitySkuList = activitySkuService.list(
                new LambdaQueryWrapper<ActivitySku>().eq(ActivitySku::getActivityId, activityId)
        );
        // 得到 skuId 集合
        List<Long> skuIdList = activitySkuList.stream()
                .map(ActivitySku::getSkuId)
                .collect(Collectors.toList());
        // 通过 OpenFeign 远程调用，获取 sku 列表
        List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoList(skuIdList);
        result.put("skuInfoList", skuInfoList);

        return result;
    }

    /**
     * 根据关键字查询sku信息列表
     * @param keyword
     * @return List<SkuInfo>
     * @throws
     */
    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
        return productFeignClient.findSkuInfoByKeyword(keyword);
    }
}

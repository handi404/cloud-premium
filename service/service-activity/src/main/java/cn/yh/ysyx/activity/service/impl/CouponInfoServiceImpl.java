package cn.yh.ysyx.activity.service.impl;

import cn.yh.ysyx.model.activity.CouponInfo;
import cn.yh.ysyx.activity.mapper.CouponInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yh.ysyx.activity.service.CouponInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}

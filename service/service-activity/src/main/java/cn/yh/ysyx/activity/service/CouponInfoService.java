package cn.yh.ysyx.activity.service;

import cn.yh.ysyx.model.activity.CouponInfo;
import cn.yh.ysyx.vo.activity.CouponRuleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 优惠券信息 服务类
 * </p>
 *
 * @author hd
 * @since 2025-10-17
 */
public interface CouponInfoService extends IService<CouponInfo> {

    /**
     * 分页查询
     * @param pageParam
     * @return IPage<CouponInfo>
     * @throws
     */
    IPage<CouponInfo> selectPage(Page<CouponInfo> pageParam);

    /**
     * 根据优惠卷id获取优惠券规则信息
     * @param couponId
     * @return Map<Object>
     * @throws
     */
    Map<String, Object> findCouponRuleList(Long couponId);

    /**
     * 新增优惠规则
     * @param couponRuleVo
     * @return
     * @throws
     */
    void saveCouponRule(CouponRuleVo couponRuleVo);
}

package cn.yh.ysyx.activity.service;

import cn.yh.ysyx.model.activity.CouponInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

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
}

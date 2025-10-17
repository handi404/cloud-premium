package cn.yh.ysyx.activity.service.impl;

import cn.yh.ysyx.model.activity.CouponInfo;
import cn.yh.ysyx.activity.mapper.CouponInfoMapper;
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

}

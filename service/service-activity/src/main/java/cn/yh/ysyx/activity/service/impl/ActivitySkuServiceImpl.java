package cn.yh.ysyx.activity.service.impl;

import cn.yh.ysyx.model.activity.ActivitySku;
import cn.yh.ysyx.activity.mapper.ActivitySkuMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yh.ysyx.activity.service.ActivitySkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 活动参与商品 服务实现类
 * </p>
 *
 * @author hd
 * @since 2025-10-17
 */
@Slf4j
@Service
@Transactional
public class ActivitySkuServiceImpl extends ServiceImpl<ActivitySkuMapper, ActivitySku> implements ActivitySkuService {

}

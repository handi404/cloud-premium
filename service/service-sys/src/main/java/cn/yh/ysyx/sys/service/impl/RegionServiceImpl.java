package cn.yh.ysyx.sys.service.impl;

import cn.yh.ysyx.model.sys.Region;
import cn.yh.ysyx.sys.mapper.RegionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yh.ysyx.sys.service.RegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 地区表 服务实现类
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
@Slf4j
@Service
@Transactional
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {

}

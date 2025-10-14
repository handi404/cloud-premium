package cn.yh.ysyx.product.service.impl;

import cn.yh.ysyx.model.product.SkuPoster;
import cn.yh.ysyx.product.mapper.SkuPosterMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yh.ysyx.product.service.SkuPosterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 商品海报表 服务实现类
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
@Slf4j
@Service
@Transactional
public class SkuPosterServiceImpl extends ServiceImpl<SkuPosterMapper, SkuPoster> implements SkuPosterService {

    /**
     * @param id
     * @return
     */
    @Override
    public List<SkuPoster> findBySkuId(Long id) {
        return baseMapper.selectList(
                new LambdaQueryWrapper<SkuPoster>().eq(SkuPoster::getSkuId, id)
        );
    }
}

package cn.yh.ysyx.product.service.impl;

import cn.yh.ysyx.model.product.SkuImage;
import cn.yh.ysyx.product.mapper.SkuImageMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yh.ysyx.product.service.SkuImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 商品图片 服务实现类
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
@Slf4j
@Service
@Transactional
public class SkuImageServiceImpl extends ServiceImpl<SkuImageMapper, SkuImage> implements SkuImageService {

    /**
     * @param id
     * @return
     */
    @Override
    public List<SkuImage> findBySkuId(Long id) {
        return baseMapper.selectList(
                new LambdaQueryWrapper<SkuImage>().eq(SkuImage::getSkuId, id)
        );
    }
}

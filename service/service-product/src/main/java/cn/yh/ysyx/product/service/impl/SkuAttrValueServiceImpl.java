package cn.yh.ysyx.product.service.impl;

import cn.yh.ysyx.model.product.SkuAttrValue;
import cn.yh.ysyx.product.mapper.SkuAttrValueMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yh.ysyx.product.service.SkuAttrValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * spu属性值 服务实现类
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
@Slf4j
@Service
@Transactional
public class SkuAttrValueServiceImpl extends ServiceImpl<SkuAttrValueMapper, SkuAttrValue> implements SkuAttrValueService {

    /**
     * @param id
     * @return
     */
    @Override
    public List<SkuAttrValue> findBySkuId(Long id) {
        return baseMapper.selectList(
                new LambdaQueryWrapper<SkuAttrValue>().eq(SkuAttrValue::getSkuId, id)
        );
    }
}

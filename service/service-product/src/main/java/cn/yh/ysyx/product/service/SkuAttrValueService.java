package cn.yh.ysyx.product.service;

import cn.yh.ysyx.model.product.SkuAttrValue;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * spu属性值 服务类
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
public interface SkuAttrValueService extends IService<SkuAttrValue> {

    List<SkuAttrValue> findBySkuId(Long id);
}

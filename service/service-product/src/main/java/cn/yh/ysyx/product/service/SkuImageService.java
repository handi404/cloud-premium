package cn.yh.ysyx.product.service;

import cn.yh.ysyx.model.product.SkuImage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品图片 服务类
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
public interface SkuImageService extends IService<SkuImage> {

    List<SkuImage> findBySkuId(Long id);
}

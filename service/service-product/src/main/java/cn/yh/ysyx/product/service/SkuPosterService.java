package cn.yh.ysyx.product.service;

import cn.yh.ysyx.model.product.SkuPoster;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品海报表 服务类
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
public interface SkuPosterService extends IService<SkuPoster> {

    List<SkuPoster> findBySkuId(Long id);
}

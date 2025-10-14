package cn.yh.ysyx.product.service;

import cn.yh.ysyx.model.product.SkuInfo;
import cn.yh.ysyx.vo.product.SkuInfoQueryVo;
import cn.yh.ysyx.vo.product.SkuInfoVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * sku信息 服务类
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
public interface SkuInfoService extends IService<SkuInfo> {

    /**
     * 分页获取商品SKU列表
     * @param pageParam
     * @param skuInfoQueryVo
     * @return IPage<SkuInfo>
     * @throws
     */
    IPage<SkuInfo> selectPage(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo);

    /**
     * 新增商品SKU
     * @param skuInfoVo
     * @return
     * @throws
     */
    void saveSkuInfo(SkuInfoVo skuInfoVo);
}

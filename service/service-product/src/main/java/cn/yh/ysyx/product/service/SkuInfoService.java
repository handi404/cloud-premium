package cn.yh.ysyx.product.service;

import cn.yh.ysyx.model.product.SkuInfo;
import cn.yh.ysyx.vo.product.SkuInfoQueryVo;
import cn.yh.ysyx.vo.product.SkuInfoVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    /**
     * 根据id获取商品SKU
     * @param id
     * @return SkuInfoVo
     * @throws
     */
    SkuInfoVo getSkuInfoVoById(Long id);

    /**
     * 修改商品SKU(info,image,poster.attr_value)
     * @param skuInfoVo
     * @return 
     * @throws 
     */
    void updateSkuInfo(SkuInfoVo skuInfoVo);

    /**
     * 修改商品SKU的审核状态
     * @param id 商品SKU id
     * @param status 审核状态：0->未审核；1->审核通过
     * @return
     * @throws
     */
    void checkStatus(Long id, Integer status);

    /**
     * 修改商品SKU的上下架状态 (审核通过后才可上架)
     * @param id 商品SKU id
     * @param status 上架状态：0->下架；1->上架
     * @return
     * @throws
     */
    void publishStatus(Long id, Integer status);

    /**
     * 修改商品SKU是否新人专享
     * @param id 商品SKU id
     * @param status 是否新人专享：0->否；1->是
     * @return
     * @throws
     */
    void isNewPerson(Long id, Integer status);

    /**
     * 根据id集合, 批量获取sku信息
     * @param skuIdList id集合
     * @return List<SkuInfo>
     * @throws
     */
    List<SkuInfo> findSkuInfoList(List<Long> skuIdList);

    /**
     * 根据关键字批量获取sku信息
     * @param keyword 关键字
     * @return List<SkuInfo>
     * @throws
     */
    List<SkuInfo> findSkuInfoByKeyword(String keyword);
}

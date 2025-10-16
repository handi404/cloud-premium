package cn.yh.ysyx.search.service;


public interface SkuService {
    
    /**
     * 上架商品
     * @param skuId
     * @return 
     * @throws 
     */
    void upperSku(Long skuId);

    /**
     * 下架商品
     * @param skuId
     * @return 
     * @throws 
     */
    void lowerSku(Long skuId);
}

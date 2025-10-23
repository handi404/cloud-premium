package cn.yh.ysyx.search.service;


import cn.yh.ysyx.model.search.SkuEs;

import java.util.List;

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

    /**
     * 获取爆款商品: 商品热度 hotScore
     * @param
     * @return List<SkuEs>
     * @throws
     */
    List<SkuEs> findHotSkuList();
}

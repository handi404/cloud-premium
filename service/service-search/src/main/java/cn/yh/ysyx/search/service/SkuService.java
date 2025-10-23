package cn.yh.ysyx.search.service;


import cn.yh.ysyx.model.search.SkuEs;
import cn.yh.ysyx.vo.search.SkuEsQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    /**
     * 根据分类与关键字搜索商品
     * @param pageParam
     * @param skuEsQueryVo
     * @return Page<SkuEs>
     * @throws
     */
    Page<SkuEs> search(Pageable pageParam, SkuEsQueryVo skuEsQueryVo);
}

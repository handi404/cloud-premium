package cn.yh.ysyx.search.repository;

import cn.yh.ysyx.model.search.SkuEs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuRepository extends ElasticsearchRepository<SkuEs, Long> {

    Page<SkuEs> findByOrderByHotScoreDesc(Pageable pageRequest);

    /**
     * 根据分类ID和仓库ID查询商品信息
     * @param pageParam
     * @param categoryId
     * @param wareId
     * @return Page<SkuEs>
     * @throws
     */
    Page<SkuEs> findByCategoryIdAndWareId(Pageable pageParam, Long categoryId, Long wareId);

    /**
     * 根据关键字和仓库ID查询商品信息
     * @param pageParam 
     * @param keyword 
     * @param wareId
     * @return Page<SkuEs> 
     * @throws 
     */
    Page<SkuEs> findByKeywordAndWareId(Pageable pageParam, String keyword, Long wareId);
}

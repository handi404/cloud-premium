package cn.yh.ysyx.search.repository;

import cn.yh.ysyx.model.search.SkuEs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuRepository extends ElasticsearchRepository<SkuEs, Long> {

    Page<SkuEs> findByOrderByHotScoreDesc(Pageable pageRequest);
}

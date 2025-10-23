package cn.yh.ysyx.search;

import cn.yh.ysyx.model.search.SkuEs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("service-search")
public interface SearchFeignClient {

    /**
     * 获取爆款商品
     * @param
     * @return List<SkuEs>
     * @throws
     */
    @GetMapping("/api/search/sku/inner/findHotSkuList")
    public List<SkuEs> findHotSkuList();
}

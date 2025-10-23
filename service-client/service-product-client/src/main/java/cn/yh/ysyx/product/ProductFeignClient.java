package cn.yh.ysyx.product;

import cn.yh.ysyx.model.product.Category;
import cn.yh.ysyx.model.product.SkuInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "service-product")
public interface ProductFeignClient {

    @GetMapping("/api/product/inner/getCategory/{categoryId}")
    public Category getCategory(@PathVariable("categoryId") Long categoryId);

    @GetMapping("/api/product/inner/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo(@PathVariable("skuId") Long skuId);

    @PostMapping("/api/product/inner/findSkuInfoList")
    public List<SkuInfo> findSkuInfoList(@RequestBody List<Long> skuIdList);

    @GetMapping("/api/product/inner/findSkuInfoByKeyword/{keyword}")
    public List<SkuInfo> findSkuInfoByKeyword(@PathVariable("keyword") String keyword);

    @PostMapping("/api/product/inner/findCategoryList")
    public List<Category> findCategoryList(@RequestBody List<Long> categoryIdList);

    /**
     * 所有商品分类
     * @param
     * @return List<Category>
     * @throws
     */
    @GetMapping("/api/product/inner/findAllCategoryList")
    public List<Category> findAllCategoryList();

    /**
     * 新人专享商品
     * @param
     * @return List<SkuInfo>
     * @throws
     */
    @GetMapping("/api/product/inner/findNewPersonSkuInfoList")
    public List<SkuInfo> findNewPersonSkuInfoList();
}

package cn.yh.ysyx.product.api;

import cn.yh.ysyx.model.product.Category;
import cn.yh.ysyx.model.product.SkuInfo;
import cn.yh.ysyx.product.service.CategoryService;
import cn.yh.ysyx.product.service.SkuInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/product")
public class ProductInnerController {

    @Resource
    private CategoryService categoryService;
    @Resource
    private SkuInfoService skuInfoService;

    @ApiOperation(value = "根据分类id获取商品分类信息")
    @GetMapping("/inner/getCategory/{categoryId}")
    public Category getCategory(@PathVariable("categoryId") Long categoryId) {
        return categoryService.getById(categoryId);
    }

    @ApiOperation(value = "根据分类id获取sku信息")
    @GetMapping("/inner/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo(@PathVariable("skuId") Long skuId) {
        return skuInfoService.getById(skuId);
    }
}

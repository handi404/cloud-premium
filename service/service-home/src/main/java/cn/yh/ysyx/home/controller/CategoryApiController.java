package cn.yh.ysyx.home.controller;

import cn.yh.ysyx.common.result.Result;
import cn.yh.ysyx.product.ProductFeignClient;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/home")
public class CategoryApiController {

    @Resource
    private ProductFeignClient productFeignClient;

    @ApiOperation("获取商品分类列表")
    @GetMapping("/category")
    public Result<?> getCategory() {
        return Result.ok(productFeignClient.findAllCategoryList());
    }
}

package cn.yh.ysyx.product.controller;


import cn.yh.ysyx.common.result.Result;
import cn.yh.ysyx.model.product.SkuInfo;
import cn.yh.ysyx.vo.product.SkuInfoQueryVo;
import cn.yh.ysyx.vo.product.SkuInfoVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import cn.yh.ysyx.product.service.SkuInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * <p>
 * sku信息 前端控制器
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
@Slf4j
@RestController
@RequestMapping("/admin/product/skuInfo")
@Api(tags = "商品SKU管理")
@CrossOrigin
public class SkuInfoController {

    @Resource
    private SkuInfoService skuInfoService;

    @ApiOperation(value = "获取商品SKU列表")
    @GetMapping("{page}/{limit}")
    public Result<?> page(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "skuInfoQueryVo", value = "查询对象", required = false)
            SkuInfoQueryVo skuInfoQueryVo
    ) {
        Page<SkuInfo> pageParam = new Page<>(page, limit);
        IPage<SkuInfo> pageModel = skuInfoService.selectPage(pageParam, skuInfoQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation("新增商品SKU")
    @PostMapping("/save")
    public Result<?> save(@RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.saveSkuInfo(skuInfoVo);
        return Result.ok(null);
    }

}

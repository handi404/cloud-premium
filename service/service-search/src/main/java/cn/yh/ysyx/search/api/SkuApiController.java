package cn.yh.ysyx.search.api;

import cn.yh.ysyx.common.result.Result;
import cn.yh.ysyx.model.search.SkuEs;
import cn.yh.ysyx.search.service.SkuService;
import cn.yh.ysyx.vo.search.SkuEsQueryVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/search/sku")
public class SkuApiController {

    @Resource
    private SkuService skuService;

    @ApiOperation(value = "上架商品")
    @GetMapping("/inner/upperSku/{skuId}")
    public Result<?> upperGoods(@PathVariable Long skuId) {
        skuService.upperSku(skuId);
        return Result.ok(null);
    }

    @ApiOperation("下架商品")
    @GetMapping("/inner/lowerSku/{skuId}")
    public Result<?> lowerGoods(@PathVariable Long skuId) {
        skuService.lowerSku(skuId);
        return Result.ok(null);
    }

    @ApiOperation("获取爆品商品")
    @GetMapping("/inner/findHotSkuList")
    public List<SkuEs> findHotSkuList() {
        return skuService.findHotSkuList();
    }

    @ApiOperation("根据分类与关键字搜索商品")
    @GetMapping("/{page}/{limit}")
    public Result<?> search(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Integer page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Integer limit,
            @ApiParam(name = "skuEsQueryVo", value = "查询对象", required = false)
            SkuEsQueryVo skuEsQueryVo
    ) {
        Pageable pageParam = PageRequest.of(page, limit);
        Page<SkuEs> result = skuService.search(pageParam, skuEsQueryVo);
        return Result.ok(result);
    }
}

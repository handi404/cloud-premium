package cn.yh.ysyx.sys.controller;


import cn.yh.ysyx.common.result.Result;
import cn.yh.ysyx.model.sys.RegionWare;
import cn.yh.ysyx.vo.sys.RegionWareQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import cn.yh.ysyx.sys.service.RegionWareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * <p>
 * 城市仓库关联表 前端控制器
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
@Slf4j
@RestController
@RequestMapping("/admin/sys/regionWare")
@Api(tags = "区域仓库管理")
public class RegionWareController {

    @Resource
    private RegionWareService regionWareService;

    @ApiOperation("获取开通区域列表")
    @GetMapping("/{page}/{limit}")
    public Result<?> index(
            @PathVariable Long page,
            @PathVariable Long limit,
            RegionWareQueryVo regionWareQueryVo
    ) {
        Page<RegionWare> pageParam = new Page<>(page, limit);
        IPage<RegionWare> pageModel = regionWareService.selectPage(pageParam, regionWareQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation("新增开通区域")
    @PostMapping("/save")
    public Result<?> save(@RequestBody RegionWare regionWare) {
        regionWareService.saveRegionWare(regionWare);
        return Result.ok(null);
    }
}

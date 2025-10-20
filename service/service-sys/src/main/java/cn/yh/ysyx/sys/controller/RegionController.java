package cn.yh.ysyx.sys.controller;


import cn.yh.ysyx.common.result.Result;
import cn.yh.ysyx.model.sys.Region;
import org.springframework.web.bind.annotation.*;
import cn.yh.ysyx.sys.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 地区表 前端控制器
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
@Slf4j
@RestController
@RequestMapping("/admin/sys/region")
@Api(tags = "区域管理")
//@CrossOrigin
public class RegionController {

    @Resource
    private RegionService regionService;

    @ApiOperation("根据关键字获取地区列表")
    @GetMapping("/findRegionByKeyword/{keyword}")
    public Result<?> findRegionByKeyword(@PathVariable String keyword) {
        return Result.ok(regionService.findRegionByKeyword(keyword));
    }
}

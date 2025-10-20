package cn.yh.ysyx.sys.controller;


import cn.yh.ysyx.common.result.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.yh.ysyx.sys.service.WareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * <p>
 * 仓库表 前端控制器
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
@Slf4j
@RestController
@RequestMapping("/admin/sys/ware")
@Api(tags = "仓库管理")
//@CrossOrigin
public class WareController {

    @Resource
    private WareService wareService;

    @ApiOperation("获取全部仓库")
    @GetMapping("/findAllList")
    public Result<?> findAllList() {
        return Result.ok(wareService.list());
    }
}

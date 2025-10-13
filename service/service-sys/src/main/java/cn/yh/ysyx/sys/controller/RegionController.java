package cn.yh.ysyx.sys.controller;


import org.springframework.web.bind.annotation.RestController;
import cn.yh.ysyx.sys.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

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
@Api(value = "", tags = "", description="")
public class RegionController {

    @Resource
    private RegionService regionService;
}

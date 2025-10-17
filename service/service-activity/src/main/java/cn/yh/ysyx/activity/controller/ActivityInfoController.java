package cn.yh.ysyx.activity.controller;


import org.springframework.web.bind.annotation.RestController;
import cn.yh.ysyx.activity.service.ActivityInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * <p>
 * 活动表 前端控制器
 * </p>
 *
 * @author hd
 * @since 2025-10-17
 */
@Slf4j
@RestController
@Api(value = "", tags = "", description="")
public class ActivityInfoController {

    @Resource
    private ActivityInfoService activityInfoService;
}

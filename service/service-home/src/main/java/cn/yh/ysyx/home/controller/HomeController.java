package cn.yh.ysyx.home.controller;

import cn.yh.ysyx.common.result.Result;
import cn.yh.ysyx.common.util.AuthContextHolder;
import cn.yh.ysyx.home.service.HomeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    @Resource
    private HomeService homeService;

    @ApiOperation("首页数据")
    @GetMapping("/index")
    public Result<?> index() {
        // 登录用户id
        Long userId = AuthContextHolder.getUserId();
        Map<String, Object> result = homeService.home(userId);
        return Result.ok(result);
    }
}

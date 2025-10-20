package cn.yh.ysyx.acl.controller;

import cn.yh.ysyx.common.result.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: IndexController
 * @Description:
 * @Version 1.0.0
 * @Author 86166
 * @Date 2025/10/9 16:59
 */
@RestController
@RequestMapping("/admin/acl/index")
@Api(tags = "登录模块")
//@CrossOrigin // 跨域请求
public class IndexController {

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login() {
        Map<String,Object> map = new HashMap<>();
        map.put("token","admin-token");
        return Result.ok(map);
    }

    /**
     * 用户信息
     */
    @GetMapping("/info")
    public Result info() {
        Map<String,Object> map = new HashMap<>();
        map.put("name","yh");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Result.ok(map);
    }

    /**
     * 退出
     */
    @PostMapping("/logout")
    public Result logout() {
        return Result.ok(null);
    }
}

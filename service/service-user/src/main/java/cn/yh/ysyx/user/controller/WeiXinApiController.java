package cn.yh.ysyx.user.controller;

import cn.yh.ysyx.common.result.Result;
import cn.yh.ysyx.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/api/user/weixin")
public class WeiXinApiController {

    @Resource
    private UserService userService;

    /**
     * 微信登录
     * @param code 临时登录凭证code
     * @return Result<?>
     * @throws
     */
    @GetMapping("/wxLogin/{code}")
    public Result<?> login(@PathVariable String code) {
        Map<String, Object> result = userService.weiXinLogin(code);
        return Result.ok(result);
    }
}

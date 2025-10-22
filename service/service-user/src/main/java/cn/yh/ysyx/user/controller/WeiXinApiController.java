package cn.yh.ysyx.user.controller;

import cn.yh.ysyx.common.result.Result;
import cn.yh.ysyx.common.util.AuthContextHolder;
import cn.yh.ysyx.model.user.User;
import cn.yh.ysyx.user.service.UserService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/auth/updateUser")
    public Result<?> updateUser(@RequestBody User user) {
        // 获取登录用户的id
        Long userId = AuthContextHolder.getUserId();
        User userLogin = userService.getById(userId);
        // 通过请求传递的用户信息更新本地用户信息(名与头像地址)
        userLogin.setNickName(user.getNickName().replaceAll("[ue000-uefff]", "*"));//[ue000-uefff]特殊符合编码
        userLogin.setPhotoUrl(user.getPhotoUrl());
        userService.updateById(userLogin);
        return Result.ok(userLogin);
    }
}

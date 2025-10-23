package cn.yh.ysyx.user.controller;

import cn.yh.ysyx.user.service.UserService;
import cn.yh.ysyx.vo.user.LeaderAddressVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/user/leader")
public class LeaderApiController {

    @Resource
    private UserService userService;

    @GetMapping("/inner/getUserAddressByUserId/{userId}")
    public LeaderAddressVo getUserAddressByUserId(@PathVariable("userId") Long userId) {
        return userService.findLeaderAddressVoByUserId(userId);
    }
}

package cn.yh.ysyx.user;

import cn.yh.ysyx.vo.user.LeaderAddressVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-user")
public interface UserFeignClient {

    /**
     * 根据用户id获取提货地点
     * @param userId
     * @return LeaderAddressVo 
     * @throws 
     */
    @GetMapping("/api/user/leader/inner/getUserAddressByUserId/{userId}")
    public LeaderAddressVo getUserAddressByUserId(@PathVariable("userId") Long userId);
}

package cn.yh.ysyx.user.service;


import cn.yh.ysyx.model.user.User;
import cn.yh.ysyx.vo.user.LeaderAddressVo;
import cn.yh.ysyx.vo.user.UserLoginVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface UserService extends IService<User> {

    /**
     * 微信登录
     * @param code 临时登录凭证code
     * @return Map<Object>
     * @throws
     */
    Map<String, Object> weiXinLogin(String code);

    /**
     * 根据openid获取用户信息
     * @param openId
     * @return User
     * @throws
     */
    User getUserByOpenId(String openId);
    
    /**
     * 根据用户编号查询提货地址信息
     * @param userId
     * @return LeaderAddressVo 
     * @throws 
     */
    LeaderAddressVo findLeaderAddressVoByUserId(Long userId);

    /**
     * 根据用户编号获取用户登录信息
     * @param userId
     * @return UserLoginVo 
     * @throws 
     */
    UserLoginVo getUserLoginVo(Long userId);
}

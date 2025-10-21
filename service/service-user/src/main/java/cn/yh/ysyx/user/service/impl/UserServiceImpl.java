package cn.yh.ysyx.user.service.impl;

import cn.yh.ysyx.common.constant.RedisConst;
import cn.yh.ysyx.common.exception.YsyxException;
import cn.yh.ysyx.common.result.ResultCodeEnum;
import cn.yh.ysyx.common.utils.helper.JwtHelper;
import cn.yh.ysyx.enums.UserType;
import cn.yh.ysyx.model.user.Leader;
import cn.yh.ysyx.model.user.User;
import cn.yh.ysyx.model.user.UserDelivery;
import cn.yh.ysyx.user.mapper.UserMapper;
import cn.yh.ysyx.user.service.LeaderService;
import cn.yh.ysyx.user.service.UserDeliveryService;
import cn.yh.ysyx.user.service.UserService;
import cn.yh.ysyx.user.utils.HttpClientUtils;
import cn.yh.ysyx.vo.user.LeaderAddressVo;
import cn.yh.ysyx.vo.user.UserLoginVo;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static cn.yh.ysyx.user.utils.ConstantPropertiesUtil.*;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserDeliveryService userDeliveryService;
    @Resource
    private LeaderService leaderService;
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 实现微信登录
     * @param code 临时登录凭证code
     * @return Map<Object>
     * @throws
     */
    @Override
    public Map<String, Object> weiXinLogin(String code) {
        Map<String, Object> result = new HashMap<>();
        // 1.构建请求url: https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        StringBuilder requestUrl = new StringBuilder()
                .append("https://api.weixin.qq.com/sns/jscode2session")
                .append("?appid=%s")
                .append("&secret=%s")
                .append("&js_code=%s")
                .append("&grant_type=authorization_code");
        String url = String.format(requestUrl.toString(), WX_OPEN_APP_ID, WX_OPEN_APP_SECRET, code);

        // 2.向微信服务器发送认证请求
        String response = null;
        try {
            response = HttpClientUtils.get(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 3.从响应的结果中解析数据
        //{
        //"openid":"xxxxxx",
        //"session_key":"xxxxx",
        //"unionid":"xxxxx",
        //"errcode":0,
        //"errmsg":"xxxxx"
        //}
        JSONObject jsonObject = JSON.parseObject(response);
        if (jsonObject.get("errcode") != null) {
            throw new YsyxException(ResultCodeEnum.FETCH_USERINFO_ERROR);
        }
        String openId = jsonObject.getString("openid"); // 用户唯一标识 OpenID
        String sessionKey = jsonObject.getString("session_key");
        // 4.根据openid获取用户信息
        User user = getUserByOpenId(openId);
        if (user == null) {
            user = new User();
            user.setOpenId(openId);
            user.setNickName(openId);
            user.setUserType(UserType.USER);
            user.setIsNew(0);
            this.save(user);//保存用户
        }
        result.put("user", user);
        // 5.根据用户的id和name生成token
        String token = JwtHelper.createToken(user.getId(), user.getNickName());
        result.put("token", token);
        // 6.获取提货地址数据(根据用户id-->user_delivery中的leaderId--->leader信息)
        LeaderAddressVo leaderAddressVo = findLeaderAddressVoByUserId(user.getId());
        result.put("leaderAddressVo", leaderAddressVo);
        // 7.将用户登录信息保存到redis中
        UserLoginVo userLoginVo = getUserLoginVo(user.getId());
        redisTemplate.opsForValue().set(RedisConst.USER_LOGIN_KEY_PREFIX+user.getId(), userLoginVo);
        return result;
    }

    /**
     * 根据openid获取用户信息
     * @param openId
     * @return User
     * @throws
     */
    @Override
    public User getUserByOpenId(String openId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getOpenId, openId);
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 根据用户编号查询提货地址信息
     * @param userId
     * @return LeaderAddressVo
     * @throws
     */
    @Override
    public LeaderAddressVo findLeaderAddressVoByUserId(Long userId) {
        // 根据用户id和默认地址查找UserDelivery信息
        UserDelivery userDelivery = userDeliveryService.getOne(
                new LambdaQueryWrapper<UserDelivery>()
                        .eq(UserDelivery::getUserId, userId)
                        .eq(UserDelivery::getIsDefault, 1)
        );
        if (userDelivery == null) {
            return null;
        }
        // 根据leaderId查找Leader信息
        Leader leader = leaderService.getById(userDelivery.getLeaderId());
        LeaderAddressVo leaderAddressVo = new LeaderAddressVo();
        BeanUtils.copyProperties(leader, leaderAddressVo);
        // 填充数据
        leaderAddressVo.setLeaderId(leader.getId());
        leaderAddressVo.setLeaderName(leader.getName());
        leaderAddressVo.setLeaderPhone(leader.getPhone());
        leaderAddressVo.setWareId(userDelivery.getWareId());
        return leaderAddressVo;
    }

    /**
     * 根据用户编号获取用户登录信息
     * @param userId
     * @return UserLoginVo
     * @throws
     */
    @Override
    public UserLoginVo getUserLoginVo(Long userId) {
        UserLoginVo userLoginVo = new UserLoginVo();
        User user = baseMapper.selectById(userId);
        BeanUtils.copyProperties(user, userLoginVo);
        // 根据用户id和是否默认地址查询UserDelivery信息
        UserDelivery userDelivery = userDeliveryService.getOne(
                new LambdaQueryWrapper<UserDelivery>()
                        .eq(UserDelivery::getUserId, userId)
                        .eq(UserDelivery::getIsDefault, 1)
        );
        if (userDelivery != null) {
            userLoginVo.setLeaderId(userDelivery.getLeaderId()); // 登录用户团长id
            userLoginVo.setWareId(userDelivery.getWareId());     // 仓库id
        } else {
            userLoginVo.setLeaderId(1L);
            userLoginVo.setWareId(1L);
        }
        return userLoginVo;
    }
}

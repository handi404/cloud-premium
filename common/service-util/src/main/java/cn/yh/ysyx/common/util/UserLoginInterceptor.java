package cn.yh.ysyx.common.util;

import cn.yh.ysyx.common.constant.RedisConst;
import cn.yh.ysyx.common.utils.helper.JwtHelper;
import cn.yh.ysyx.vo.user.UserLoginVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UserLoginInterceptor implements HandlerInterceptor {

    private RedisTemplate redisTemplate;

    public UserLoginInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 执行时机：在业务方法执行之前执行
     * @param request
     * @param response
     * @param handler
     * @return 是否放行: 如果值为true则放行;否则拦截
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取 token
        String token = request.getHeader("token");
        if (StringUtils.hasText(token)) {
            Long userId = JwtHelper.getUserId(token);
            // 从 redis 中获取用户数据
            UserLoginVo userLoginVo = (UserLoginVo)redisTemplate.opsForValue().get(RedisConst.USER_LOGIN_KEY_PREFIX + userId);
            if (userLoginVo != null) {
                AuthContextHolder.setUserId(userLoginVo.getUserId());
                AuthContextHolder.setWareId(userLoginVo.getWareId());
            }
        }
        return true;
    }
}

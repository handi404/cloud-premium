package cn.yh.ysyx.home.service;


import java.util.Map;

public interface HomeService {
    
    /**
     * 获取主页数据：提货地点，所有商品分类，新人专享商品，爆款商品
     * @param userId
     * @return Map<Object> 
     * @throws 
     */
    Map<String, Object> home(Long userId);
}

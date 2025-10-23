package cn.yh.ysyx.home.service.impl;

import cn.yh.ysyx.home.service.HomeService;
import cn.yh.ysyx.model.product.Category;
import cn.yh.ysyx.model.product.SkuInfo;
import cn.yh.ysyx.model.search.SkuEs;
import cn.yh.ysyx.product.ProductFeignClient;
import cn.yh.ysyx.search.SearchFeignClient;
import cn.yh.ysyx.user.UserFeignClient;
import cn.yh.ysyx.vo.user.LeaderAddressVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomeServiceImpl implements HomeService {

    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    private ProductFeignClient productFeignClient;
    @Resource
    private SearchFeignClient searchFeignClient;

    /**
     * 获取主页数据：提货地点，所有商品分类，新人专享商品，爆款商品
     * @param userId
     * @return Map<Object>
     * @throws
     */
    @Override
    public Map<String, Object> home(Long userId) {
        Map<String, Object> result = new HashMap<>();
        // 获取提货地点
        LeaderAddressVo leaderAddressVo = userFeignClient.getUserAddressByUserId(userId);
        result.put("leaderAddressVo", leaderAddressVo);
        // 获取所有商品分类
        List<Category> categoryList = productFeignClient.findAllCategoryList();
        result.put("categoryList", categoryList);
        // 获取新人专享商品
        List<SkuInfo> newPersonSkuInfoList = productFeignClient.findNewPersonSkuInfoList();
        result.put("newPersonSkuInfoList", newPersonSkuInfoList);
        // 后去爆款商品
        List<SkuEs> hotSkuList = searchFeignClient.findHotSkuList();
        result.put("hotSkuList", hotSkuList);
        return result;
    }
}

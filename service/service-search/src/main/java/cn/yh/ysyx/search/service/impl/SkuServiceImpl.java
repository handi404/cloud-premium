package cn.yh.ysyx.search.service.impl;

import cn.yh.ysyx.model.product.Category;
import cn.yh.ysyx.model.product.SkuInfo;
import cn.yh.ysyx.model.search.SkuEs;
import cn.yh.ysyx.product.ProductFeignClient;
import cn.yh.ysyx.search.repository.SkuRepository;
import cn.yh.ysyx.search.service.SkuService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class SkuServiceImpl implements SkuService {

    @Resource
    private SkuRepository skuRepository;
    @Resource
    private ProductFeignClient productFeignClient;

    /**
     * 上架商品
     * @param skuId
     * @return
     * @throws
     */
    @Override
    public void upperSku(Long skuId) {
        SkuEs skuEs = new SkuEs();
        // 查询 sku 信息
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        if (skuInfo == null) {
            return;
        }
        // 查询商品分类
        Category category = productFeignClient.getCategory(skuInfo.getCategoryId());
        // 构建 skuEs
        BeanUtils.copyProperties(skuInfo, skuEs);
        skuEs.setKeyword(skuInfo.getSkuName() + "," + category.getName()); // 构建关键词：sku名+","+类型名
        skuEs.setTitle(skuInfo.getSkuName());
        SkuEs save = skuRepository.save(skuEs);
        System.out.println(save);
    }

    /**
     * 下架商品
     * @param skuId
     * @return
     * @throws
     */
    @Override
    public void lowerSku(Long skuId) {
        skuRepository.deleteById(skuId);
    }

    /**
     * 获取爆款商品
     * @return List<SkuEs>
     * @throws
     */
    @Override
    public List<SkuEs> findHotSkuList() {
        // 分页并根据hotScore降序排序
        Pageable pageRequest = PageRequest.of(0, 10);
        Page<SkuEs> skuEsPage = skuRepository.findByOrderByHotScoreDesc(pageRequest);
        return skuEsPage.getContent();
    }
}

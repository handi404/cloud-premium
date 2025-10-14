package cn.yh.ysyx.product.service.impl;

import cn.yh.ysyx.model.product.SkuAttrValue;
import cn.yh.ysyx.model.product.SkuImage;
import cn.yh.ysyx.model.product.SkuInfo;
import cn.yh.ysyx.model.product.SkuPoster;
import cn.yh.ysyx.product.mapper.SkuInfoMapper;
import cn.yh.ysyx.product.service.SkuAttrValueService;
import cn.yh.ysyx.product.service.SkuImageService;
import cn.yh.ysyx.product.service.SkuPosterService;
import cn.yh.ysyx.vo.product.SkuInfoQueryVo;
import cn.yh.ysyx.vo.product.SkuInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yh.ysyx.product.service.SkuInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * sku信息 服务实现类
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
@Slf4j
@Service
@Transactional
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {

    @Resource
    private SkuPosterService skuPosterService;
    @Resource
    private SkuImageService skuImageService;
    @Resource
    private SkuAttrValueService skuAttrValueService;

    /**
     * 分页获取商品SKU列表
     * @param pageParam
     * @param skuInfoQueryVo
     * @return IPage<SkuInfo>
     * @throws
     */
    @Override
    public IPage<SkuInfo> selectPage(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo) {
        // 条件信息
        String keyword = skuInfoQueryVo.getKeyword();
        String skuType = skuInfoQueryVo.getSkuType();
        Long categoryId = skuInfoQueryVo.getCategoryId();

        // 构建条件
        LambdaQueryWrapper<SkuInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)) {
            queryWrapper.like(SkuInfo::getSkuName, keyword);
        }
        if (!StringUtils.isEmpty(skuType)) {
            queryWrapper.like(SkuInfo::getSkuType, skuType);
        }
        if (!StringUtils.isEmpty(categoryId)) {
            queryWrapper.like(SkuInfo::getCategoryId, categoryId);
        }

        return baseMapper.selectPage(pageParam, queryWrapper);
    }

    /**
     * 新增商品SKU
     * @param skuInfoVo
     * @return
     * @throws
     */
    @Override
    public void saveSkuInfo(SkuInfoVo skuInfoVo) {
        // 1.保存sku信息 (sku_info)
        SkuInfo skuInfo = new SkuInfo();
        // 相同属性，值复制
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        this.save(skuInfo);

        // 2.保存sku海报 (sku_poster)
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        // if (skuPosterList != null && !skuPosterList.isEmpty())
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            for (SkuPoster skuPoster : skuPosterList) {
                skuPoster.setSkuId(skuInfo.getId());
            }
            skuPosterService.saveBatch(skuPosterList);
        }

        // 3.保存sku图片 (sku_image)
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImagesList)) {
            int sort = 1;
            for (SkuImage skuImage : skuImagesList) {
                skuImage.setSkuId(skuInfo.getId());
                skuImage.setSort(sort++); // 顺序
            }
            skuImageService.saveBatch(skuImagesList);
        }

        // 4.保存sku平台属性 (sku_attr_value)
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            int sort = 1;
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuInfo.getId());
                skuAttrValue.setSort(sort++);
            }
            skuAttrValueService.saveBatch(skuAttrValueList);
        }
    }
}

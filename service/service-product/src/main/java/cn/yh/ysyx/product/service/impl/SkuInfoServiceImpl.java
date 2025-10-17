package cn.yh.ysyx.product.service.impl;

import cn.yh.ysyx.common.constant.MqConst;
import cn.yh.ysyx.common.service.RabbitService;
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
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
import java.util.Collections;
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
    @Resource
    private RabbitService rabbitService;

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

    /**
     * 根据id获取商品SKU
     * @param id
     * @return SkuInfoVo
     * @throws
     */
    @Override
    public SkuInfoVo getSkuInfoVoById(Long id) {
        // 根据 id 查询sku_info
        SkuInfo skuInfo = baseMapper.selectById(id);

        // 根据 SkuId 查询sku_image
        List<SkuImage> skuImageList = skuImageService.findBySkuId(id);
        // 根据 SkuId 查询sku_poster
        List<SkuPoster> skuPosterList = skuPosterService.findBySkuId(id);
        // 根据 SkuId 查询sku_attr_value
        List<SkuAttrValue> skuAttrValueList = skuAttrValueService.findBySkuId(id);

        // 构建skuInfoVo
        SkuInfoVo skuInfoVo = new SkuInfoVo();
        BeanUtils.copyProperties(skuInfo, skuInfoVo);
        skuInfoVo.setSkuPosterList(skuPosterList);
        skuInfoVo.setSkuImagesList(skuImageList);
        skuInfoVo.setSkuAttrValueList(skuAttrValueList);

        return skuInfoVo;
    }

    /**
     * 修改商品SKU(info,image,poster.attr_value)
     * @param skuInfoVo
     * @return
     * @throws
     */
    @Override
    public void updateSkuInfo(SkuInfoVo skuInfoVo) {
        // 1.更新sku_info
        baseMapper.updateById(skuInfoVo);
        Long skuId = skuInfoVo.getId();
        // 2.删除原有图片，再添加
        skuImageService.remove(new LambdaUpdateWrapper<SkuImage>().eq(SkuImage::getSkuId, skuId));
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImagesList)) {
            int sort = 1;
            for (SkuImage skuImage : skuImagesList) {
                skuImage.setSkuId(skuId);
                skuImage.setSort(sort++);
            }
            skuImageService.saveBatch(skuImagesList);
        }
        // 3.删除原有海报，再添加
        skuPosterService.remove(new LambdaUpdateWrapper<SkuPoster>().eq(SkuPoster::getSkuId, skuId));
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            for (SkuPoster skuPoster : skuPosterList) {
                skuPoster.setSkuId(skuId);
            }
            skuPosterService.saveBatch(skuPosterList);
        }
        // 4.删除原有平台属性值，再添加
        skuAttrValueService.remove(new LambdaUpdateWrapper<SkuAttrValue>().eq(SkuAttrValue::getSkuId, skuId));
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            int sort = 1;
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuId);
                skuAttrValue.setSort(sort++);
            }
            skuAttrValueService.saveBatch(skuAttrValueList);
        }
    }

    /**
     * 修改商品SKU的审核状态
     * @param id     商品SKU id
     * @param status 审核状态：0->未审核；1->审核通过
     * @return
     * @throws
     */
    @Override
    public void checkStatus(Long id, Integer status) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(id);
        skuInfo.setCheckStatus(status);
        baseMapper.updateById(skuInfo);
    }

    /**
     * 修改商品SKU上架状态
     * @param skuId     商品SKU id
     * @param status 上架状态：0->下架；1->上架
     * @return
     * @throws
     */
    @Override
    public void publishStatus(Long skuId, Integer status) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        if (status == 1) {
            skuInfo.setPublishStatus(status);
            baseMapper.updateById(skuInfo);
            // 商品上架 发送mq消息更新es数据
            rabbitService.sendMessage(MqConst.EXCHANGE_GOODS_DIRECT, MqConst.ROUTING_GOODS_UPPER, skuId);
        } else {
            skuInfo.setPublishStatus(0);
            baseMapper.updateById(skuInfo);
            // 商品下架 发送mq消息更新es数据
            rabbitService.sendMessage(MqConst.EXCHANGE_GOODS_DIRECT, MqConst.ROUTING_GOODS_LOWER, skuId);
        }
    }

    /**
     * 修改商品SKU是否新人专享
     * @param id     商品SKU id
     * @param status 是否新人专享：0->否；1->是
     * @return
     * @throws
     */
    @Override
    public void isNewPerson(Long id, Integer status) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(id);
        skuInfo.setIsNewPerson(status);
        baseMapper.updateById(skuInfo);
    }

    /**
     * 根据id集合, 批量获取sku信息
     * @param skuIdList id集合
     * @return List<SkuInfo>
     * @throws
     */
    @Override
    public List<SkuInfo> findSkuInfoList(List<Long> skuIdList) {
        return baseMapper.selectBatchIds(skuIdList);
    }

    /**
     * 根据关键字批量获取sku信息
     * @param keyword 关键字
     * @return List<SkuInfo>
     * @throws
     */
    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
        LambdaQueryWrapper<SkuInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(SkuInfo::getSkuName, keyword);
        return baseMapper.selectList(queryWrapper);
    }
}

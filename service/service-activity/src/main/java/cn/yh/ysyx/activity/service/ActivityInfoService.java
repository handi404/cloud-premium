package cn.yh.ysyx.activity.service;

import cn.yh.ysyx.model.activity.ActivityInfo;
import cn.yh.ysyx.model.product.SkuInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 活动表 服务类
 * </p>
 *
 * @author hd
 * @since 2025-10-17
 */
public interface ActivityInfoService extends IService<ActivityInfo> {

    /**
     * 活动分页列表
     * @param pageParam     封装页码与页大小
     * @return IPage<Admin> 分页查询结果
     * @throws
     */
    IPage<ActivityInfo> selectPage(Page<ActivityInfo> pageParam);

    /**
     * 活动规则列表
     * @param activityId
     * @return Map<Object> "activityRuleList":List<ActivityRule>,"skuInfoList":List<SkuInfo>
     * @throws 
     */
    Map<String, Object> findActivityRuleList(Long activityId);

    /**
     * 根据关键字查询sku信息列表
     * @param keyword
     * @return List<SkuInfo>
     * @throws
     */
    List<SkuInfo> findSkuInfoByKeyword(String keyword);
}

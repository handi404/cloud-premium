package cn.yh.ysyx.activity.service.impl;

import cn.yh.ysyx.activity.service.ActivityRuleService;
import cn.yh.ysyx.activity.service.ActivitySkuService;
import cn.yh.ysyx.enums.ActivityType;
import cn.yh.ysyx.model.activity.ActivityInfo;
import cn.yh.ysyx.activity.mapper.ActivityInfoMapper;
import cn.yh.ysyx.model.activity.ActivityRule;
import cn.yh.ysyx.model.activity.ActivitySku;
import cn.yh.ysyx.model.product.SkuInfo;
import cn.yh.ysyx.product.ProductFeignClient;
import cn.yh.ysyx.vo.activity.ActivityRuleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yh.ysyx.activity.service.ActivityInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 活动表 服务实现类
 * </p>
 *
 * @author hd
 * @since 2025-10-17
 */
@Slf4j
@Service
@Transactional
public class ActivityInfoServiceImpl extends ServiceImpl<ActivityInfoMapper, ActivityInfo> implements ActivityInfoService {

    @Resource
    private ActivityRuleService activityRuleService;
    @Resource
    private ActivitySkuService activitySkuService;
    @Resource
    private ProductFeignClient productFeignClient;

    /**
     * 活动分页列表
     * @param pageParam     封装页码与页大小
     * @return IPage<Admin> 分页查询结果
     * @throws
     */
    @Override
    public IPage<ActivityInfo> selectPage(Page<ActivityInfo> pageParam) {
        LambdaQueryWrapper<ActivityInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(ActivityInfo::getId);
        IPage<ActivityInfo> activityInfoPage = baseMapper.selectPage(pageParam, queryWrapper);
        activityInfoPage.getRecords().forEach(
                activityInfo -> activityInfo.setActivityTypeString(activityInfo.getActivityType().getComment())
        );
        return activityInfoPage;
    }

    /**
     * 活动规则列表
     * @param activityId
     * @return Map<Object>
     * @throws
     */
    @Override
    public Map<String, Object> findActivityRuleList(Long activityId) {
        Map<String, Object> result = new HashMap<>();

        // 活动规则列表
        LambdaQueryWrapper<ActivityRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ActivityRule::getActivityId, activityId);
        List<ActivityRule> activityRuleList = activityRuleService.list(queryWrapper);
        result.put("activityRuleList", activityRuleList);

        // 活动范围列表: activity_sku 是 activity_info 与 sku_info 的中间表
        List<ActivitySku> activitySkuList = activitySkuService.list(
                new LambdaQueryWrapper<ActivitySku>().eq(ActivitySku::getActivityId, activityId)
        );
        // 得到 skuId 集合
        List<Long> skuIdList = activitySkuList.stream()
                .map(ActivitySku::getSkuId)
                .collect(Collectors.toList());
        // 通过 OpenFeign 远程调用，根据 skuId 集合获取 sku 列表
        List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoList(skuIdList);
        result.put("skuInfoList", skuInfoList);

        return result;
    }

    /**
     * 根据关键字查询sku信息列表
     * @param keyword
     * @return List<SkuInfo>
     * @throws
     */
    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
        List<SkuInfo> skuInfoByKeyword = productFeignClient.findSkuInfoByKeyword(keyword);
        // 将模糊查询到商品skuId添加到集合中
        List<Long> skuIdList = skuInfoByKeyword.stream()
                .map(SkuInfo::getId)
                .collect(Collectors.toList());
        // 查询当前时间已参加过(活动时间在当前时间有效的)活动的skuId
        List<Long> existsSkuIdList = baseMapper.findExistsSkuIdList(skuIdList);
        // 移除已参加过(活动时间在当前时间有效的)活动的活动信息
        List<SkuInfo> notExistsSkuInfoList = skuInfoByKeyword.stream()
                .filter(skuInfo -> !existsSkuIdList.contains(skuInfo.getId()))
                .collect(Collectors.toList());
        return notExistsSkuInfoList;
    }

    /**
     * 修改活动规则：删除原有的，再新增
     * @param activityRuleVo
     * @return
     * @throws
     */
    @Override
    public void saveActivityRule(ActivityRuleVo activityRuleVo) {
        Long activityId = activityRuleVo.getActivityId();

        // 删除原有活动规则 activity_rule，再批量添加
        List<ActivityRule> activityRuleList = activityRuleVo.getActivityRuleList();
        activityRuleService.remove(
                new LambdaUpdateWrapper<ActivityRule>().eq(ActivityRule::getActivityId, activityId)
        );
        activityRuleService.saveBatch(activityRuleList);

        // 删除原有活动范围 activity_sku, 再批量添加
        activitySkuService.remove(
                new LambdaUpdateWrapper<ActivitySku>().eq(ActivitySku::getActivityId, activityId)
        );
        List<ActivitySku> activitySkuList = activityRuleVo.getActivitySkuList();
        activitySkuList.forEach(activitySku -> activitySku.setActivityId(activityId));
        // 将activitySkuList转换为Set集合(利用Set集合中的元素不重复实现去重)
        Set<ActivitySku> activitySkuSet = new HashSet<>(activitySkuList);
        activitySkuService.saveBatch(activitySkuSet);
    }

    /**
     * 根据商品编号查询商品活动规则内容列表
     * @param skuIdList
     * @return Map<List < ActivityRule>>
     * @throws
     */
    @Override
    public Map<Long, List<String>> findActivity(List<Long> skuIdList) {
        if (CollectionUtils.isEmpty(skuIdList)) {
            return Collections.emptyMap();
        }
        Map<Long, List<String>> result = new HashMap<>();
        for (Long skuId : skuIdList) {
            // 根据商品id查询活动规则
            List<ActivityRule> activityRuleList = baseMapper.findActivityRuleList(skuId);
            if (!CollectionUtils.isEmpty(activityRuleList)) {
                List<String> ruleList = new ArrayList<>(); // 商品的规则描述集合
                for (ActivityRule activityRule : activityRuleList) {
                    // 获取规则的描述
                    String ruleDesc = getRuleDesc(activityRule);
                    ruleList.add(ruleDesc);
                }
                result.put(skuId, ruleList);
            }
        }
        return result;
    }

    /**
     * 根据规则获取规则的描述
     * @param activityRule
     * @return
     */
    private String getRuleDesc(ActivityRule activityRule) {
        StringBuffer ruleDesc = new StringBuffer();
        if(activityRule.getActivityType()== ActivityType.FULL_REDUCTION){ //满减
            ruleDesc.append("满")
                    .append(activityRule.getConditionAmount())
                    .append("元减")
                    .append(activityRule.getBenefitAmount())
                    .append("元");
        }else if(activityRule.getActivityType()== ActivityType.FULL_DISCOUNT){ //满量打折
            ruleDesc.append("满")
                    .append(activityRule.getConditionNum())
                    .append("件打")
                    .append(activityRule.getBenefitDiscount())
                    .append("折");
        }
        return ruleDesc.toString();
    }
}

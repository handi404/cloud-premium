package cn.yh.ysyx.activity.mapper;

import cn.yh.ysyx.model.activity.ActivityInfo;
import cn.yh.ysyx.model.activity.ActivityRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 活动表 Mapper 接口
 * </p>
 *
 * @author hd
 * @since 2025-10-17
 */
@Repository
public interface ActivityInfoMapper extends BaseMapper<ActivityInfo> {

    /**
     * 查询"根据关键字查询的skuId集合"中当前时间已参加过(活动时间在当前时间有效的)活动的skuId
     * @param skuIdList 根据关键字查询的skuId集合
     * @return List<Long>
     * @throws
     */
    List<Long> findExistsSkuIdList(@Param("skuIdList")List<Long> skuIdList);

    /**
     * 根据商品id查询活动规则列表
     * @param skuId
     * @return List<ActivityRule> 
     * @throws 
     */
    List<ActivityRule> findActivityRuleList(Long skuId);
}

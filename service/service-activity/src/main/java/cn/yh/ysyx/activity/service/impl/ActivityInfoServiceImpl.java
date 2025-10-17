package cn.yh.ysyx.activity.service.impl;

import cn.yh.ysyx.model.activity.ActivityInfo;
import cn.yh.ysyx.activity.mapper.ActivityInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yh.ysyx.activity.service.ActivityInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}

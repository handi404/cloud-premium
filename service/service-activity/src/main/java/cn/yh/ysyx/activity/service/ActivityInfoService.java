package cn.yh.ysyx.activity.service;

import cn.yh.ysyx.model.activity.ActivityInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

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
}

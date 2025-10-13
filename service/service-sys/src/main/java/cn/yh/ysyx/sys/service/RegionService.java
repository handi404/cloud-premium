package cn.yh.ysyx.sys.service;

import cn.yh.ysyx.model.sys.Region;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 地区表 服务类
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
public interface RegionService extends IService<Region> {

    /**
     * 根据关键字获取地区列表
     * @param keyword 关键字
     * @return List<Region> 
     * @throws
     */
    List<Region> findRegionByKeyword(String keyword);
}

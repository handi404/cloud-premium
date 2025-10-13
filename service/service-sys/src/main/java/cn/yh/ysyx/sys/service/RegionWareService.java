package cn.yh.ysyx.sys.service;

import cn.yh.ysyx.model.sys.RegionWare;
import cn.yh.ysyx.vo.sys.RegionWareQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 城市仓库关联表 服务类
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
public interface RegionWareService extends IService<RegionWare> {

    /**
     * 获取区域开通列表
     * @param pageParam 封装页码与页大小
     * @param regionWareQueryVo 封装查询条件
     * @return IPage<RegionWare> 分页查询结果
     * @throws
     */
    IPage<RegionWare> selectPage(Page<RegionWare> pageParam, RegionWareQueryVo regionWareQueryVo);

    /**
     * 新增开通区域
     * @param regionWare
     * @return
     * @throws
     */
    void saveRegionWare(RegionWare regionWare);
}

package cn.yh.ysyx.sys.service.impl;

import cn.yh.ysyx.common.exception.YsyxException;
import cn.yh.ysyx.common.result.ResultCodeEnum;
import cn.yh.ysyx.model.sys.RegionWare;
import cn.yh.ysyx.sys.mapper.RegionWareMapper;
import cn.yh.ysyx.vo.sys.RegionWareQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yh.ysyx.sys.service.RegionWareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 城市仓库关联表 服务实现类
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
@Slf4j
@Service
@Transactional
public class RegionWareServiceImpl extends ServiceImpl<RegionWareMapper, RegionWare> implements RegionWareService {

    /**
     * 获取区域开通列表
     * @param pageParam         封装页码与页大小
     * @param regionWareQueryVo 封装查询条件
     * @return IPage<RegionWare> 分页查询结果
     * @throws
     */
    @Override
    public IPage<RegionWare> selectPage(Page<RegionWare> pageParam, RegionWareQueryVo regionWareQueryVo) {
        LambdaQueryWrapper<RegionWare> queryWrapper = new LambdaQueryWrapper<>();
        // 获取查询关键字
        String keyword = regionWareQueryVo.getKeyword();
        // 拼接条件
        if (!StringUtils.isEmpty(keyword)) {
            queryWrapper.like(RegionWare::getRegionName, keyword)
                    .or().like(RegionWare::getWareName, keyword);
        }
        return baseMapper.selectPage(pageParam, queryWrapper);
    }

    /**
     * 新增开通区域
     * @param regionWare
     * @return
     * @throws
     */
    @Override
    public void saveRegionWare(RegionWare regionWare) throws YsyxException {
        // 判断此区域是否已开通
        Integer count = baseMapper.selectCount(
                new LambdaQueryWrapper<RegionWare>().eq(RegionWare::getRegionName, regionWare.getRegionName())
        );
        if (count > 0) {
            throw new YsyxException(ResultCodeEnum.REGION_OPEN);
        }
        // 开通区域
        baseMapper.insert(regionWare);
    }
}

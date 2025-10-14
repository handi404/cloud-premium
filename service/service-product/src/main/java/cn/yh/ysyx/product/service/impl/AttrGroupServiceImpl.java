package cn.yh.ysyx.product.service.impl;

import cn.yh.ysyx.model.product.AttrGroup;
import cn.yh.ysyx.product.mapper.AttrGroupMapper;
import cn.yh.ysyx.vo.product.AttrGroupQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yh.ysyx.product.service.AttrGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 属性分组 服务实现类
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
@Slf4j
@Service
@Transactional
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroup> implements AttrGroupService {

    /**
     * 商品分类分页列表
     * @param pageParam        封装页码与页大小
     * @param attrGroupQueryVo 封装查询条件
     * @return IPage<AttrGroup> 分页查询结果
     * @throws
     */
    @Override
    public IPage<AttrGroup> selectPage(Page<AttrGroup> pageParam, AttrGroupQueryVo attrGroupQueryVo) {
        // 获取条件
        String name = attrGroupQueryVo.getName();
        LambdaQueryWrapper<AttrGroup> queryWrapper = new LambdaQueryWrapper<>();
        // 构建条件
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like(AttrGroup::getName, name);
        }
        return baseMapper.selectPage(pageParam, queryWrapper);
    }

    /**
     * 根据sort排序平台属性列表
     * @return List<AttrGroup>
     * @throws
     */
    @Override
    public List<AttrGroup> findAllList() {
        LambdaQueryWrapper<AttrGroup> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(AttrGroup::getSort);
        return baseMapper.selectList(queryWrapper);
    }
}

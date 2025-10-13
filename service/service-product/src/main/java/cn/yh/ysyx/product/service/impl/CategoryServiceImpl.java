package cn.yh.ysyx.product.service.impl;

import cn.yh.ysyx.model.product.Category;
import cn.yh.ysyx.product.mapper.CategoryMapper;
import cn.yh.ysyx.vo.product.CategoryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yh.ysyx.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 商品三级分类 服务实现类
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
@Slf4j
@Service
@Transactional
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    /**
     * 商品分类分页列表
     * @param pageParam  封装页码与页大小
     * @param categoryVo 封装查询条件
     * @return IPage<Admin> 分页查询结果
     * @throws
     */
    @Override
    public IPage<Category> selectPage(Page<Category> pageParam, CategoryVo categoryVo) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        // 获取查询条件按
        String name = categoryVo.getName();
        // 构建查询条件
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like(Category::getName, name);
        }
        queryWrapper.orderByAsc(Category::getSort);
        return baseMapper.selectPage(pageParam, queryWrapper);
    }

    /**
     * 根据sort排序的商品分类列表
     * @return List<Category>
     * @throws
     */
    @Override
    public List<Category> findAllList() {
        return baseMapper.selectList(
                new LambdaQueryWrapper<Category>().orderByAsc(Category::getSort)
        );
    }
}

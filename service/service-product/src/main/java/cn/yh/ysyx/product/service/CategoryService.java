package cn.yh.ysyx.product.service;

import cn.yh.ysyx.model.product.Category;
import cn.yh.ysyx.vo.product.CategoryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品三级分类 服务类
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
public interface CategoryService extends IService<Category> {

    /**
     * 商品分类分页列表
     * @param pageParam 封装页码与页大小
     * @param categoryVo 封装查询条件
     * @return IPage<Admin> 分页查询结果
     * @throws
     */
    IPage<Category> selectPage(Page<Category> pageParam, CategoryVo categoryVo);

    /**
     * 根据sort排序的商品分类列表
     * @param 
     * @return List<Category> 
     * @throws 
     */
    List<Category> findAllList();
}

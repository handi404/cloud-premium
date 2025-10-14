package cn.yh.ysyx.product.service;

import cn.yh.ysyx.model.product.AttrGroup;
import cn.yh.ysyx.vo.product.AttrGroupQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 属性分组 服务类
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
public interface AttrGroupService extends IService<AttrGroup> {

    /**
     * 商品分类分页列表
     * @param pageParam 封装页码与页大小
     * @param attrGroupQueryVo 封装查询条件
     * @return IPage<AttrGroup> 分页查询结果
     * @throws
     */
    IPage<AttrGroup> selectPage(Page<AttrGroup> pageParam, AttrGroupQueryVo attrGroupQueryVo);

    /**
     * 根据sort排序平台属性列表
     * @param
     * @return List<AttrGroup>
     * @throws
     */
    List<AttrGroup> findAllList();
}

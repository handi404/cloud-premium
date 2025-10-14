package cn.yh.ysyx.product.service;

import cn.yh.ysyx.model.product.Attr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
public interface AttrService extends IService<Attr> {

    /**
     * 根据属性分组id获取列表
     * @param groupId 属性分组id
     * @return List<Attr>
     * @throws
     */
    List<Attr> findByAttrGroupId(Long groupId);
}

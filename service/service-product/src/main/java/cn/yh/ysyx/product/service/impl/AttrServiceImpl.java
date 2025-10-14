package cn.yh.ysyx.product.service.impl;

import cn.yh.ysyx.model.product.Attr;
import cn.yh.ysyx.product.mapper.AttrMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yh.ysyx.product.service.AttrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
@Slf4j
@Service
@Transactional
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements AttrService {

    /**
     * 根据属性分组id获取列表
     * @param groupId 属性分组id
     * @return List<Attr>
     * @throws
     */
    @Override
    public List<Attr> findByAttrGroupId(Long groupId) {
        List<Attr> attrList = baseMapper.selectList(
                new LambdaQueryWrapper<Attr>().eq(Attr::getAttrGroupId, groupId)
        );
        return attrList;
    }
}

package cn.yh.ysyx.acl.service.impl;

import cn.yh.ysyx.acl.mapper.RoleMapper;
import cn.yh.ysyx.acl.service.RoleService;
import cn.yh.ysyx.model.acl.Role;
import cn.yh.ysyx.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    /**
     * 查询角色分页列表
     * @param pageParam   封装页码与页大小
     * @param roleQueryVo 封装查询条件
     * @return IPage<Role> 分页查询结果
     * @throws
     */
    @Override
    public IPage<Role> selectPage(Page<Role> pageParam, RoleQueryVo roleQueryVo) {
        // 获取条件值：角色名称
        String roleName = roleQueryVo.getRoleName();
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        //判断条件值是否为空
        if (!StringUtils.isEmpty(roleName)) {
            queryWrapper.like(Role::getRoleName, roleName);
        }
        return baseMapper.selectPage(pageParam, queryWrapper);
    }
}

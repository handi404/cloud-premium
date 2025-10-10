package cn.yh.ysyx.acl.service.impl;

import cn.yh.ysyx.acl.mapper.RoleMapper;
import cn.yh.ysyx.acl.service.AdminRoleService;
import cn.yh.ysyx.acl.service.RoleService;
import cn.yh.ysyx.model.acl.AdminRole;
import cn.yh.ysyx.model.acl.Role;
import cn.yh.ysyx.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private AdminRoleService adminRoleService;

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

    /**
     * 方法1: 查询所有角色和用户拥有的角色
     * @param adminId
     * @return Map<String, List<Role>>
     * @throws
     */
    @Override
    public Map<String, List<Role>> findRoleByAdminId(Long adminId) {
        Map<String, List<Role>> roleMap = new HashMap<>();
        // 所有角色
        List<Role> allRolesList = baseMapper.selectList(null);
        roleMap.put("allRolesList", allRolesList);

        // 用户已拥有的角色
        List<Role> assignRoles = baseMapper.selectRolesByAdminId(adminId);
        roleMap.put("assignRoles", assignRoles);

        return roleMap;
    }
    /**
     * 方法2: 查询所有角色和用户拥有的角色
     * @param adminId
     * @return Map<String, List<Role>>
     * @throws
     */
    @Override
    public Map<String, List<Role>> findRoleByUserId(Long adminId) {
        Map<String, List<Role>> roleMap =  new HashMap<>();
        // 所有角色
        List<Role> allRolesList = baseMapper.selectList(null);
        roleMap.put("allRolesList", allRolesList);

        // 根据用户id获得其拥有的角色id
        List<AdminRole> existAdminRoleList = adminRoleService.list(
                new LambdaQueryWrapper<AdminRole>().eq(AdminRole::getAdminId, adminId)
                        .select(AdminRole::getRoleId)
        );
        List<Long> existRoleList = existAdminRoleList.stream()
                .map(AdminRole::getRoleId)
                .collect(Collectors.toList());

        // 角色分类：已分配
        List<Role> assignRoles = new ArrayList<>();
        for (Role role : allRolesList) {
            if (existRoleList.contains(role.getId())) {
                assignRoles.add(role);
            }
        }
        roleMap.put("assignRoles", assignRoles);

        return roleMap;
    }

    /**
     * 根据用户分配角色: 删除用户分配角色数据，再分配角色
     * @param adminId 用户id
     * @param roleIds  角色id数组
     * @return
     */
    @Transactional
    @Override
    public void saveAdminRole(Long adminId, Long[] roleIds) {
        // 删除用户分配角色 (admin_role)
        adminRoleService.remove(new LambdaUpdateWrapper<AdminRole>().eq(AdminRole::getAdminId, adminId));

        // 分配角色
        List<AdminRole> adminRoles = new ArrayList<>();
        for (Long roleId : roleIds) {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(roleId);
            adminRoles.add(adminRole);
        }
        adminRoleService.saveBatch(adminRoles);
    }
}

package cn.yh.ysyx.acl.service;

import cn.yh.ysyx.model.acl.Role;
import cn.yh.ysyx.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface RoleService extends IService<Role> {

    /**
     * 查询角色分页列表
     * @param pageParam 封装页码与页大小
     * @param roleQueryVo 封装查询条件
     * @return IPage<Role> 分页查询结果
     * @throws 
     */
    IPage<Role> selectPage(Page<Role> pageParam, RoleQueryVo roleQueryVo);

    /**
     * 方法1: 查询所有角色和用户拥有的角色
     * @param adminId
     * @return Map<String, List<Role>> {"assignRoles":[用户已有角色],"allRoleList":[所有角色]}
     * @throws
     */
    Map<String, List<Role>> findRoleByAdminId(Long adminId);
    /**
     * 方法2: 查询所有角色和用户拥有的角色
     * @param adminId
     * @return Map<String, List<Role>> {"assignRoles":[用户已有角色],"allRoleList":[所有角色]}
     * @throws 
     */
    Map<String, List<Role>> findRoleByUserId(Long adminId);

    /**
     * 根据用户分配角色: 删除用户已有角色，再分配角色
     * @param adminId 用户id
     * @param roleIds 角色id数组
     * @return
     * @throws
     */
    void saveAdminRole(Long adminId, Long[] roleIds);
}

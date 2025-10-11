package cn.yh.ysyx.acl.service;

import cn.yh.ysyx.model.acl.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

public interface RolePermissionService extends IService<RolePermission> {

    /**
     * 给角色授权: 删除角色已有权限，再分配权限
     * @param roleId 角色id
     * @param permissionId 权限id数组
     * @return
     * @throws
     */
    void saveRolePermission(Long roleId, Long[] permissionId);
}

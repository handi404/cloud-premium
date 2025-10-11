package cn.yh.ysyx.acl.service.impl;

import cn.yh.ysyx.acl.mapper.RolePermissionMapper;
import cn.yh.ysyx.acl.service.RolePermissionService;
import cn.yh.ysyx.model.acl.RolePermission;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    /**
     * 给角色授权: 删除角色已有权限，再分配权限
     * @param roleId       角色id
     * @param permissionId 权限id数组
     * @return
     * @throws
     */
    @Transactional
    @Override
    public void saveRolePermission(Long roleId, Long[] permissionId) {
        // 删除角色已有权限
        this.remove(
                new LambdaUpdateWrapper<RolePermission>().eq(RolePermission::getRoleId, roleId)
        );
        // 分配权限
        List<RolePermission> rolePermissionList = new ArrayList<>();
        for (Long pid : permissionId) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(pid);
            rolePermissionList.add(rolePermission);
        }
        this.saveBatch(rolePermissionList);
    }
}

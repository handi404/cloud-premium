package cn.yh.ysyx.acl.service.impl;

import cn.yh.ysyx.acl.mapper.PermissionMapper;
import cn.yh.ysyx.acl.service.PermissionService;
import cn.yh.ysyx.acl.util.PermissionHelper;
import cn.yh.ysyx.model.acl.Permission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    /**
     * 获取所有菜单列表
     * @return List<Permission> 树形结构菜单
     * @throws
     */
    @Override
    public List<Permission> queryAllMenus() {
        // 所有菜单
        List<Permission> permissions = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

        // 把菜单数据构建成树形结构数据
        return PermissionHelper.build(permissions);
    }

    /**
     * 递归删除菜单
     * @param id
     * @return
     * @throws
     */
    @Override
    public void removeChildById(Long id) {
        List<Long> idList = new ArrayList<>();
        selectChildListById(id, idList);
        idList.add(id);
        this.removeByIds(idList);
    }

    /**
     * 查询所有权限与角色拥有权限
     * @param roleId
     * @return List<Permission>
     * @throws
     */
    @Override
    public List<Permission> findPermissionByRoleId(Long roleId) {
        // 所有权限
        List<Permission> allPermissions = baseMapper.selectList(
                new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)")
        );
        // 角色拥有权限
        List<Permission> rolePermission = baseMapper.selectPermissionsByRoleId(roleId);
        // 所有权限中标记角色拥有权限: isSelect=true
        for (Permission permission : allPermissions) {
            if (rolePermission.contains(permission)) {
                permission.setSelect(true);
            }
        }
        // 将权限数据转换为树状菜单结构
        return PermissionHelper.build(allPermissions);
    }

    /**
     * 递归获取子节点
     * @param id 父节点id
     * @param idList
     * @return 
     * @throws 
     */
    private void selectChildListById(Long id, List<Long> idList) {
        // 父节点的子节点
        List<Permission> permissions = baseMapper.selectList(
                new LambdaQueryWrapper<Permission>().eq(Permission::getPid, id)
        );
        for (Permission permission : permissions) {
            idList.add(permission.getId());
            // 递归：为子节点继续找它的子节点
            selectChildListById(permission.getId(), idList);
        }
    }
}

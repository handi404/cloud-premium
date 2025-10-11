package cn.yh.ysyx.acl.service;


import cn.yh.ysyx.model.acl.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    
    /**
     * 获取所有菜单列表
     * @return List<Permission> 树形结构菜单
     * @throws 
     */
    List<Permission> queryAllMenus();

    /**
     * 递归删除菜单
     * @param id
     * @return 
     * @throws 
     */
    void removeChildById(Long id);
}

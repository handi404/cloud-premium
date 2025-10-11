package cn.yh.ysyx.acl.mapper;

import cn.yh.ysyx.model.acl.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据角色的获取权限列表
     * @param id
     * @return List<Permission>
     * @throws
     */
    @Select("SELECT p.* FROM permission p, role_permission rp where rp.role_id = #{id} and p.id = rp.permission_id and rp.is_deleted=0")
    List<Permission> selectPermissionsByRoleId(Long id);
}

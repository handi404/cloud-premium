package cn.yh.ysyx.acl.mapper;

import cn.yh.ysyx.model.acl.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    @Select("select r.* from role r, admin_role ar where ar.role_id=r.id and ar.admin_id=#{adminId} and ar.is_deleted=0")
    List<Role> selectRolesByAdminId(Long adminId);
}

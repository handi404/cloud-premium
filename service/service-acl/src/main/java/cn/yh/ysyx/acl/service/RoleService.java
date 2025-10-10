package cn.yh.ysyx.acl.service;

import cn.yh.ysyx.model.acl.Role;
import cn.yh.ysyx.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface RoleService extends IService<Role> {

    /**
     * 查询角色分页列表
     * @param pageParam 封装页码与页大小
     * @param roleQueryVo 封装查询条件
     * @return IPage<Role> 分页查询结果
     * @throws 
     */
    IPage<Role> selectPage(Page<Role> pageParam, RoleQueryVo roleQueryVo);
}

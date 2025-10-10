package cn.yh.ysyx.acl.service;

import cn.yh.ysyx.model.acl.Admin;
import cn.yh.ysyx.model.user.User;
import cn.yh.ysyx.vo.acl.AdminQueryVo;
import cn.yh.ysyx.vo.user.UserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AdminService extends IService<Admin> {

    /**
     * 用户分页列表
     * @param pageParam 封装页码与页大小
     * @param adminQueryVo 封装查询条件
     * @return IPage<Admin> 分页查询结果
     * @throws
     */
    IPage<Admin> selectPage(Page<Admin> pageParam, AdminQueryVo adminQueryVo);
}

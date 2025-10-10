package cn.yh.ysyx.acl.service.impl;

import cn.yh.ysyx.acl.mapper.AdminMapper;
import cn.yh.ysyx.acl.service.AdminService;
import cn.yh.ysyx.model.acl.Admin;
import cn.yh.ysyx.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    /**
     * 用户分页列表
     * @param pageParam    封装页码与页大小
     * @param adminQueryVo 封装查询条件
     * @return IPage<Admin> 分页查询结果
     * @throws
     */
    @Override
    public IPage<Admin> selectPage(Page<Admin> pageParam, AdminQueryVo adminQueryVo) {
        // 获取查询条件按
        String name = adminQueryVo.getName();
        //创建条件构造器
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            //封装条件
            queryWrapper.like(Admin::getName, name);
        }
        return baseMapper.selectPage(pageParam, queryWrapper);
    }
}

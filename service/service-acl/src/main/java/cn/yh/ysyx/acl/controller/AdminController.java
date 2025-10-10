package cn.yh.ysyx.acl.controller;

import cn.yh.ysyx.acl.service.AdminRoleService;
import cn.yh.ysyx.acl.service.AdminService;
import cn.yh.ysyx.acl.service.RoleService;
import cn.yh.ysyx.common.result.Result;
import cn.yh.ysyx.model.acl.Admin;
import cn.yh.ysyx.model.acl.Role;
import cn.yh.ysyx.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/admin/acl/user")
@Api(tags = "用户管理")
@CrossOrigin
public class AdminController {

    @Resource
    private AdminService adminService;
    @Resource
    private RoleService roleService;

    @ApiOperation(value = "获取管理用户分页列表")
    @GetMapping("{page}/{limit}")
    public Result<?> page(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "userQueryVo", value = "查询对象", required = false)
            AdminQueryVo adminQueryVo
    ) {
        Page<Admin> pageParam = new Page<>(page, limit);
        IPage<Admin> pageModel = adminService.selectPage(pageParam, adminQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "根据id获取管理用户")
    @GetMapping("/get/{id}")
    public Result<?> getById(@PathVariable Long id) {
        Admin admin = adminService.getById(id);
        return Result.ok(admin);
    }

    @ApiOperation(value = "新增管理用户")
    @PostMapping("/save")
    public Result<?> save(@RequestBody Admin admin) {
        adminService.save(admin);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改管理用户")
    @PutMapping("/update")
    public Result<?> update(@RequestBody Admin admin) {
        adminService.updateById(admin);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除管理用户")
    @DeleteMapping("/remove/{id}")
    public Result<?> remove(@PathVariable Long id) {
        adminService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "批量删除管理用户")
    @DeleteMapping("batchRemove")
    public Result<?> batchRemove(@RequestBody List<Long> ids) {
        adminService.removeByIds(ids);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据用户获取角色")
    @GetMapping("/toAssign/{adminId}")
    public Result<?> toAssign(@PathVariable Long adminId) {
        Map<String, List<Role>> roleMap =  roleService.findRoleByUserId(adminId);
        return Result.ok(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public Result<?> doAssign(
            @RequestParam Long adminId,
            @RequestParam Long[] roleId
    ) {
        // 删除用户已有角色，再分配角色
        roleService.saveAdminRole(adminId, roleId);
        return Result.ok(null);
    }
}

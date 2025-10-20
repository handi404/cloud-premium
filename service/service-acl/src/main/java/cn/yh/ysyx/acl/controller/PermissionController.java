package cn.yh.ysyx.acl.controller;

import cn.yh.ysyx.acl.service.PermissionService;
import cn.yh.ysyx.acl.service.RolePermissionService;
import cn.yh.ysyx.common.result.Result;
import cn.yh.ysyx.model.acl.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/acl/permission")
@Api(tags = "菜单管理")
//@CrossOrigin
public class PermissionController {

    @Resource
    private PermissionService permissionService;
    @Resource
    private RolePermissionService rolePermissionService;

    @ApiOperation("获取权限(菜单/功能)列表")
    @GetMapping
    public Result index() {
        List<Permission> permissions = permissionService.queryAllMenus();
        return Result.ok(permissions);
    }

    @ApiOperation("保存一个权限项")
    @PostMapping("/save")
    public Result<?> save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.ok(null);
    }

    @ApiOperation("更新一个权限项")
    @PutMapping("/update")
    public Result<?> update(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.ok(null);
    }

    @ApiOperation("删除一个权限项")
    @DeleteMapping("/remove/{id}")
    public Result<?> remove(@PathVariable Long id) {
        // 唯一删除
        // permissionService.removeById(id);
        // 递归删除
        permissionService.removeChildById(id);
        return Result.ok(null);
    }


    @ApiOperation("获取一个角色的所有权限列表")
    @GetMapping("/toAssign/{roleId}")
    public Result<?> toAssign(@PathVariable Long roleId) {
        List<Permission> permissions = permissionService.findPermissionByRoleId(roleId);
        return Result.ok(permissions);
    }

    @ApiOperation("给某个角色授权")
    @PostMapping("/doAssign")
    public Result<?> doAssign(
            @RequestParam Long roleId,
            @RequestParam Long[] permissionId
    ) {
        // 删除角色已有权限，再分配权限
        rolePermissionService.saveRolePermission(roleId, permissionId);
        return Result.ok(null);
    }

}

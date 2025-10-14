package cn.yh.ysyx.product.controller;


import cn.yh.ysyx.common.result.Result;
import cn.yh.ysyx.model.product.AttrGroup;
import cn.yh.ysyx.vo.product.AttrGroupQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import cn.yh.ysyx.product.service.AttrGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 属性分组 前端控制器
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
@Slf4j
@RestController
@RequestMapping("/admin/product/attrGroup")
@Api(tags = "平台属性分组管理")
@CrossOrigin
public class AttrGroupController {

    @Resource
    private AttrGroupService attrGroupService;

    @ApiOperation(value = "获取平台属性分组列表")
    @GetMapping("{page}/{limit}")
    public Result<?> page(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "attrGroupQueryVo", value = "查询对象", required = false)
            AttrGroupQueryVo attrGroupQueryVo
    ) {
        Page<AttrGroup> pageParam = new Page<>(page, limit);
        IPage<AttrGroup> pageModel = attrGroupService.selectPage(pageParam, attrGroupQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "根据id获取平台属性分组")
    @GetMapping("/get/{id}")
    public Result<?> getById(@PathVariable Long id) {
        AttrGroup attrGroup = attrGroupService.getById(id);
        return Result.ok(attrGroup);
    }

    @ApiOperation(value = "新增平台属性分组")
    @PostMapping("/save")
    public Result<?> save(@RequestBody AttrGroup attrGroup) {
        attrGroupService.save(attrGroup);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改平台属性分组")
    @PutMapping("/update")
    public Result<?> update(@RequestBody AttrGroup attrGroup) {
        attrGroupService.updateById(attrGroup);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除平台属性分组")
    @DeleteMapping("/remove/{id}")
    public Result<?> remove(@PathVariable Long id) {
        attrGroupService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "批量删除平台属性分组")
    @DeleteMapping("/batchRemove")
    public Result<?> batchRemove(@RequestBody List<Long> ids) {
        attrGroupService.removeByIds(ids);
        return Result.ok(null);
    }

    @ApiOperation("获取所有平台属性分组")
    @GetMapping("/findAllList")
    public Result<?> findAllList() {
        List<AttrGroup> attrGroupList = attrGroupService.findAllList();
        return Result.ok(attrGroupList);
    }
}

package cn.yh.ysyx.product.controller;


import cn.yh.ysyx.common.result.Result;
import cn.yh.ysyx.model.product.Attr;
import org.springframework.web.bind.annotation.*;
import cn.yh.ysyx.product.service.AttrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品属性 前端控制器
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
@Slf4j
@RestController
@RequestMapping("/admin/product/attr")
@Api(tags = "平台属性管理")
//@CrossOrigin
public class AttrController {

    @Resource
    private AttrService attrService;

    @ApiOperation("根据属性分组id获取列表")
    @GetMapping("/{groupId}")
    public Result<?> findByAttrGroupId(@PathVariable Long groupId) {
        List<Attr> attrList = attrService.findByAttrGroupId(groupId);
        return Result.ok(attrList);
    }

    @ApiOperation(value = "根据id获取平台属性")
    @GetMapping("/get/{id}")
    public Result<?> getById(@PathVariable Long id) {
        Attr attr = attrService.getById(id);
        return Result.ok(attr);
    }

    @ApiOperation(value = "新增平台属性")
    @PostMapping("/save")
    public Result<?> save(@RequestBody Attr attr) {
        attrService.save(attr);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改平台属性")
    @PutMapping("/update")
    public Result<?> update(@RequestBody Attr attr) {
        attrService.updateById(attr);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除平台属性")
    @DeleteMapping("/remove/{id}")
    public Result<?> remove(@PathVariable Long id) {
        attrService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "批量删除平台属性")
    @DeleteMapping("/batchRemove")
    public Result<?> batchRemove(@RequestBody List<Long> ids) {
        attrService.removeByIds(ids);
        return Result.ok(null);
    }
}

package cn.yh.ysyx.product.controller;


import cn.yh.ysyx.common.result.Result;
import cn.yh.ysyx.model.product.Category;
import cn.yh.ysyx.vo.product.CategoryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import cn.yh.ysyx.product.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品三级分类 前端控制器
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
@Slf4j
@RestController
@RequestMapping("/admin/product/category")
@Api(tags = "商品分类管理")
@CrossOrigin
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @ApiOperation(value = "获取商品分类分页列表")
    @GetMapping("{page}/{limit}")
    public Result<?> page(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "categoryVo", value = "查询对象", required = false)
            CategoryVo categoryVo
    ) {
        Page<Category> pageParam = new Page<>(page, limit);
        IPage<Category> pageModel = categoryService.selectPage(pageParam, categoryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "根据id获取商品分类")
    @GetMapping("/get/{id}")
    public Result<?> getById(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        return Result.ok(category);
    }

    @ApiOperation(value = "新增商品分类")
    @PostMapping("/save")
    public Result<?> save(@RequestBody Category category) {
        categoryService.save(category);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改商品分类")
    @PutMapping("/update")
    public Result<?> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除商品分类")
    @DeleteMapping("/remove/{id}")
    public Result<?> remove(@PathVariable Long id) {
        categoryService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "批量删除商品分类")
    @DeleteMapping("/batchRemove")
    public Result<?> batchRemove(@RequestBody List<Long> ids) {
        categoryService.removeByIds(ids);
        return Result.ok(null);
    }

    @ApiOperation("获取所有商品分类")
    @GetMapping("/findAllList")
    public Result<?> findAllList() {
        List<Category> categoryList = categoryService.findAllList();
        return Result.ok(categoryList);
    }
}

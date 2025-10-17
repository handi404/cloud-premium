package cn.yh.ysyx.activity.controller;


import cn.yh.ysyx.common.result.Result;
import cn.yh.ysyx.model.acl.Admin;
import cn.yh.ysyx.model.activity.ActivityInfo;
import cn.yh.ysyx.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import cn.yh.ysyx.activity.service.ActivityInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 活动表 前端控制器
 * </p>
 *
 * @author hd
 * @since 2025-10-17
 */
@RestController
@RequestMapping("/admin/activity/activityInfo")
@Api(tags = "活动管理")
@CrossOrigin
public class ActivityInfoController {

    @Resource
    private ActivityInfoService activityInfoService;

    @ApiOperation(value = "获取活动分页列表")
    @GetMapping("{page}/{limit}")
    public Result<?> page(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit
    ) {
        Page<ActivityInfo> pageParam = new Page<>(page, limit);
        IPage<ActivityInfo> pageModel = activityInfoService.selectPage(pageParam);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "根据id获取活动")
    @GetMapping("/get/{id}")
    public Result<?> getById(@PathVariable Long id) {
        ActivityInfo activityInfo = activityInfoService.getById(id);
        return Result.ok(activityInfo);
    }

    @ApiOperation(value = "新增")
    @PostMapping("/save")
    public Result<?> save(@RequestBody ActivityInfo activityInfo) {
        activityInfoService.save(activityInfo);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("/update")
    public Result<?> update(@RequestBody ActivityInfo activityInfo) {
        activityInfoService.updateById(activityInfo);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/remove/{id}")
    public Result<?> remove(@PathVariable Long id) {
        activityInfoService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping("batchRemove")
    public Result<?> batchRemove(@RequestBody List<Long> ids) {
        activityInfoService.removeByIds(ids);
        return Result.ok(null);
    }
}

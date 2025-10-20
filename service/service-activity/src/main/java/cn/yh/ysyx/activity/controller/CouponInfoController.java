package cn.yh.ysyx.activity.controller;


import cn.yh.ysyx.common.result.Result;
import cn.yh.ysyx.model.activity.CouponInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import cn.yh.ysyx.activity.service.CouponInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 优惠券信息 前端控制器
 * </p>
 *
 * @author hd
 * @since 2025-10-17
 */
@RestController
@RequestMapping("/admin/activity/couponInfo")
@Api(tags = "优惠卷管理")
@CrossOrigin
public class CouponInfoController {

    @Resource
    private CouponInfoService couponInfoService;

    @ApiOperation(value = "获取优惠卷分页列表")
    @GetMapping("{page}/{limit}")
    public Result<?> page(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit
    ) {
        Page<CouponInfo> pageParam = new Page<>(page, limit);
        IPage<CouponInfo> pageModel = couponInfoService.selectPage(pageParam);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "根据id获取优惠卷")
    @GetMapping("/get/{id}")
    public Result<?> getById(@PathVariable Long id) {
        CouponInfo couponInfo = couponInfoService.getById(id);
        couponInfo.setCouponTypeString(couponInfo.getCouponType().getComment());
        couponInfo.setRangeTypeString(couponInfo.getRangeType().getComment());
        return Result.ok(couponInfo);
    }

    @ApiOperation(value = "新增")
    @PostMapping("/save")
    public Result<?> save(@RequestBody CouponInfo couponInfo) {
        couponInfoService.save(couponInfo);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("/update")
    public Result<?> update(@RequestBody CouponInfo couponInfo) {
        couponInfoService.updateById(couponInfo);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/remove/{id}")
    public Result<?> remove(@PathVariable Long id) {
        couponInfoService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping("batchRemove")
    public Result<?> batchRemove(@RequestBody List<Long> ids) {
        couponInfoService.removeByIds(ids);
        return Result.ok(null);
    }
}

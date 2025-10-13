package cn.yh.ysyx.product.controller;


import org.springframework.web.bind.annotation.RestController;
import cn.yh.ysyx.product.service.SkuPosterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 商品海报表 前端控制器
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
@Slf4j
@RestController
@Api(value = "", tags = "", description="")
public class SkuPosterController {

    @Autowired
    private SkuPosterService skuPosterService;
}

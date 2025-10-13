package cn.yh.ysyx.product.controller;


import org.springframework.web.bind.annotation.RestController;
import cn.yh.ysyx.product.service.SkuAttrValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * spu属性值 前端控制器
 * </p>
 *
 * @author hd
 * @since 2025-10-13
 */
@Slf4j
@RestController
@Api(value = "", tags = "", description="")
public class SkuAttrValueController {

    @Autowired
    private SkuAttrValueService skuAttrValueService;
}

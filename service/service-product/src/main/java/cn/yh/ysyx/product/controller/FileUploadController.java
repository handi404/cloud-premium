package cn.yh.ysyx.product.controller;

import cn.yh.ysyx.common.result.Result;
import cn.yh.ysyx.product.service.FileUploadService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/admin/product")
@Api(tags = "文件上传")
@CrossOrigin
public class FileUploadController {

    @Resource
    private FileUploadService fileUploadService;

    @PostMapping("/fileUpload")
    public Result<?> fileUpload(MultipartFile file) {
        String url = fileUploadService.fileUpload(file);
        return Result.ok(url);
    }
}

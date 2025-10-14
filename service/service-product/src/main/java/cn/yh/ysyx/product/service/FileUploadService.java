package cn.yh.ysyx.product.service;


import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    
    /**
     * 文件上传
     * @param file
     * @return String 访问文件URL
     * @throws 
     */
    String fileUpload(MultipartFile file);
}

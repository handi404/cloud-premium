package cn.yh.ysyx.product.service.impl;

import cn.yh.ysyx.product.service.FileUploadService;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@Transactional
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${aliyun.endpoint}")
    private String endPoint;
    @Value("${aliyun.keyid}")
    private String accessKeyId;
    @Value("${aliyun.keysecret}")
    private String secretAccessKey;
    @Value("${aliyun.bucketname}")
    private String bucketName;

    /**
     * 文件上传
     * @param file
     * @return String 访问文件URL
     * @throws
     */
    @Override
    public String fileUpload(MultipartFile file) {
        try {
            // 创建OSSClient实例
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, secretAccessKey);
            // 上传文件流
            InputStream inputStream = file.getInputStream();
            // 原文件名
            String originalFilename = file.getOriginalFilename();
            // 文件扩展名：.png
            String fileExtName = originalFilename.substring(originalFilename.lastIndexOf("."));

            // 唯一文件名
            String uuidName = UUID.randomUUID().toString().replaceAll("-", "");
            // 上传保存路径：按照当前日期，创建文件夹，上传到创建文件夹里面
            String dateUrl = new DateTime().toString("yyyy/MM/dd");
            // 文件上传名：路径 + "/" + uuidName(文件名) + fileExtName(文件扩展名)
            String fileName = dateUrl + "/" + uuidName + fileExtName;

            // 调用方法实现上传
            ossClient.putObject(bucketName, fileName, inputStream);
            ossClient.shutdown(); // 关闭OSSClient

            // 上传之后文件访问路径：https://shequ-ysyx.oss-cn-beijing.aliyuncs.com/2025/10/14/6f66a253945545558f9fba733be6d113.png
            String url = "https://" + bucketName + "." + endPoint + "/" + fileName;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

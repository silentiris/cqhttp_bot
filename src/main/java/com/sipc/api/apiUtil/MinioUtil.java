package com.sipc.api.apiUtil;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
@Slf4j
@Component
public class MinioUtil {
    @Value("${minio.endpoint}")
    private  String endpoint;
    @Value("${minio.accesskey}")
    private  String accessKey;
    @Value("${minio.secretkey}")
    private   String secretKey;
    @Value("${minio.bucketname}")
    private  String bucket;

    public  InputStream returnBitMap(String path) {

        URL url = null;
        InputStream is = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            // 利用HttpURLConnection对象,我们可以从网络中获取网页数据.
            assert url != null;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            // 得到网络返回的输入流
            is = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }


    public   String uploadFileWithNetFile(String url) {
        // 创建客户端对象
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint(endpoint)
                        .credentials(accessKey, secretKey)
                        .build();
        try {
            InputStream inputStream = returnBitMap(url);
            // 判断上传文件是否为空
            if (null == inputStream) {
                return null;
            }
            // uuid 生成文件名
            String uuid = java.lang.String.valueOf(UUID.randomUUID());
            // 新的文件名
            String fileName = uuid + ".jpg";
            // 开始上传
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucket).object(fileName).stream(
                                    inputStream, -1, 10485760)
                            .contentType("image/jpeg")
                            .build());
            System.out.println("upload:"+fileName);
            // 返回图片的访问路径
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String downloadFile(String objectName) {
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint(endpoint)
                        .credentials(accessKey, secretKey)
                        .build();
        String url =
                null;
        try {
            url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucket)
                            .object(objectName)
                            .expiry(5, TimeUnit.MINUTES)
                            .build());
        } catch (Exception e) {
            log.error("minio error!!!");
        }
        return url;
    }
    public int removeObject(String objectName){
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint(endpoint)
                        .credentials(accessKey, secretKey)
                        .build();
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket(bucket).object(objectName).build());
        } catch (Exception ignored) {}
        return 1;
    }

}

package quick.pager.pcloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import quick.pager.pcloud.exception.PCloudException;
import quick.pager.pcloud.oss.request.OSSUploadRequest;
import quick.pager.pcloud.utils.DateUtils;
import quick.pager.pcloud.utils.FileUtil;
import quick.pager.pcloud.oss.enums.OSSTypeEnum;
import quick.pager.pcloud.properties.QiniuProperties;
import quick.pager.pcloud.service.OSSService;

/**
 * 七牛云OSS
 *
 * @author siguiyang
 */
@Service("QINIU")
@Slf4j
@EnableConfigurationProperties(QiniuProperties.class)
public class QiniuServiceImpl implements OSSService {

    private final UploadManager uploadManager;
    private final Auth auth;
    private final QiniuProperties qiniuProperties;

    @Autowired(required = false)
    public QiniuServiceImpl(UploadManager uploadManager, Auth auth, QiniuProperties qiniuProperties) {
        this.uploadManager = uploadManager;
        this.auth = auth;
        this.qiniuProperties = qiniuProperties;
    }

    @Override
    public boolean support(final OSSTypeEnum ossType) {
        return OSSTypeEnum.QINIU.equals(ossType);
    }

    @Override
    public String uploadStream(InputStream is, String fileName) {
        try {

            String key = qiniuProperties.getBucket() + '/' + DateUtils.format(LocalDateTime.now(), DateUtils.NORM_DATE_PATTERN) + "/" + fileName;
            Response response = uploadManager.put(is, key, this.getToken(), null, null);
            if (response.isOK()) {
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
                return putRet.key;
            }

        } catch (QiniuException e) {
            throw new RuntimeException("上传文件失败");
        }
        return null;
    }

    @Override
    public String uploadContent(final OSSUploadRequest request) {

        try {
            String key = qiniuProperties.getBucket() + '/' + DateUtils.format(LocalDateTime.now(), DateUtils.NORM_DATE_PATTERN) + "/" + request.getFilename();

            Response response = uploadManager.put(request.getContent().getBytes(StandardCharsets.UTF_8), key, this.getToken());
            if (response.isOK()) {
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
                return putRet.key;
            }
        } catch (QiniuException e) {
            throw new PCloudException("上传文件失败");
        }
        return null;
    }

    @Override
    public String uploadFile(File file, String fileName) {

        if (!file.exists()) {
            throw new RuntimeException("上传的文件不存在");
        }
        try {
            return this.uploadStream(new FileInputStream(file), fileName);
        } catch (FileNotFoundException e) {
            log.error("文件不存在");
        }
        return null;
    }


    @Override
    public String uploadToByte(byte[] data, String fileName) {
        try {
            Response response = uploadManager.put(data, fileName, this.getToken());
            if (response.isOK()) {
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
                return putRet.key;
            }
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public InputStream download(String ossKey) {

        try {
            String encodedFileName = URLEncoder.encode(ossKey, "utf-8").replace("+", "%20");
            String publicUrl = String.format("%s/%s", qiniuProperties.getEndpoint(), encodedFileName);
            String url = auth.privateDownloadUrl(publicUrl, 3600); //1小时，可以自定义链接过期时间

            return FileUtil.getRemoteFile(url);
        } catch (Exception e) {
            log.error("获取文件流失败");
        }
        return null;
    }

    /**
     * 获取上传token
     *
     * @return 上传token
     */
    private String getToken() {
        return auth.uploadToken(qiniuProperties.getBucket());
    }

}

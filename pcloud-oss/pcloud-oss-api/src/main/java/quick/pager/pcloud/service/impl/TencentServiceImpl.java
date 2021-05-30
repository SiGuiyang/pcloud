package quick.pager.pcloud.service.impl;

import com.qcloud.cos.COS;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.ObjectMetadata;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quick.pager.pcloud.oss.enums.OSSTypeEnum;
import quick.pager.pcloud.oss.request.OSSUploadRequest;
import quick.pager.pcloud.properties.COSCloudProperties;
import quick.pager.pcloud.service.OSSService;
import quick.pager.pcloud.utils.Assert;
import quick.pager.pcloud.utils.DateUtils;

@Service
public class TencentServiceImpl implements OSSService {

    private final COS cos;
    private final COSCloudProperties cosCloudProperties;

    @Autowired(required = false)
    public TencentServiceImpl(COS cos, COSCloudProperties cosCloudProperties) {
        this.cos = cos;
        this.cosCloudProperties = cosCloudProperties;
    }

    @Override
    public boolean support(OSSTypeEnum ossType) {
        return OSSTypeEnum.TENCENT.equals(ossType);
    }

    @Override
    public String uploadStream(InputStream is, String fileName) {
        String uploadFileName = "static/".concat(fileName);
        cos.putObject(cosCloudProperties.getBucket(), uploadFileName, is, new ObjectMetadata());
        return "https://".concat(cosCloudProperties.getBucket()).concat(".cos.").concat(cosCloudProperties.getRegionName()).concat(".myqcloud.com/").concat(uploadFileName);
    }

    @Override
    public String uploadContent(final OSSUploadRequest request) {
        String uploadFileName = "static/".concat(LocalDate.now().format(DateTimeFormatter.ofPattern(DateUtils.PURE_DATE_PATTERN))).concat("/").concat(request.getFilename());
        cos.putObject(cosCloudProperties.getBucket(), uploadFileName, request.getContent());
        return "https://".concat(cosCloudProperties.getBucket()).concat(".cos.").concat(cosCloudProperties.getRegionName()).concat(".myqcloud.com/").concat(uploadFileName);
    }

    @Override
    public String uploadFile(File file, String fileName) {

        String uploadFileName = "static/".concat(fileName);
        cos.putObject(cosCloudProperties.getBucket(), "static/".concat(fileName), file);

        return "https://".concat(cosCloudProperties.getBucket()).concat(".cos.").concat(cosCloudProperties.getRegionName()).concat(".myqcloud.com/").concat(uploadFileName);
    }

    @Override
    public String uploadToByte(byte[] data, String fileName) {
        Assert.isTrue(true, () -> "不支持此文件类型上传");
        return null;
    }

    @Override
    public InputStream download(String ossKey) {
        String uploadFileName = "static/".concat(ossKey);
        COSObject cosObject = cos.getObject(cosCloudProperties.getBucket(), uploadFileName);
        return cosObject.getObjectContent();
    }
}

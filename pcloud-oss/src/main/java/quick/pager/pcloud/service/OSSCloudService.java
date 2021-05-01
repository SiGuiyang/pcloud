package quick.pager.pcloud.service;

import java.util.List;
import quick.pager.pcloud.dto.BucketDTO;
import quick.pager.pcloud.dto.ObjectSummaryDTO;
import quick.pager.pcloud.enmus.OSSTypeEnum;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.request.UploadRequest;

/**
 * OSS云服务
 *
 * @author siguiyang
 */
public interface OSSCloudService {

    /**
     * 是否支持云服务
     *
     * @param ossType 云服务类型
     */
    boolean support(final OSSTypeEnum ossType);

    /**
     * 获取云服务桶
     */
    ResponseResult<List<BucketDTO>> bucket();

    /**
     * 文件上传对象
     *
     * @param request 请求参数
     */
    ResponseResult<List<ObjectSummaryDTO>> objects(final UploadRequest request);
}

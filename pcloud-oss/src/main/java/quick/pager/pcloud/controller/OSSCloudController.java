package quick.pager.pcloud.controller;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.configuration.OSSContext;
import quick.pager.pcloud.constants.ResponseStatus;
import quick.pager.pcloud.dto.BucketDTO;
import quick.pager.pcloud.dto.ObjectSummaryDTO;
import quick.pager.pcloud.enmus.OSSTypeEnum;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.model.request.UploadRequest;
import quick.pager.pcloud.service.OSSCloudService;

/**
 * oss云服务
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/oss/cloud")
@Slf4j
public class OSSCloudController {

    /**
     * 云服务桶列表
     *
     * @param ossType oss云服务类型
     * @return
     */
    @GetMapping("/bucket/{ossType}")
    public ResponseResult<List<BucketDTO>> bucket(@PathVariable("ossType") String ossType) {

        OSSTypeEnum ossTypeEnum = EnumUtils.getEnum(OSSTypeEnum.class, ossType);
        Optional<OSSCloudService> optional = OSSContext.getBeans(OSSCloudService.class).stream().filter(item -> item.support(ossTypeEnum)).findFirst();

        if (optional.isPresent()) {
            return optional.get().bucket();
        } else {
            return ResponseResult.toError(ResponseStatus.Code.FAIL_CODE, "未找到可用的云服务器");
        }
    }


    /**
     * 文件上传对象
     *
     * @param request 请求参数
     */
    @PostMapping("/objects")
    public ResponseResult<List<ObjectSummaryDTO>> objects(@RequestBody UploadRequest request) {
        OSSTypeEnum ossTypeEnum = EnumUtils.getEnum(OSSTypeEnum.class, request.getOssType());
        Optional<OSSCloudService> optional = OSSContext.getBeans(OSSCloudService.class).stream().filter(item -> item.support(ossTypeEnum)).findFirst();

        if (optional.isPresent()) {
            return optional.get().objects(request);
        } else {
            return ResponseResult.toError(ResponseStatus.Code.FAIL_CODE, "未找到可用的云服务器");
        }
    }
}

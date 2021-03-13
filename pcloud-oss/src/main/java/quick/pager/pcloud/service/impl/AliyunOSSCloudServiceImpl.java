package quick.pager.pcloud.service.impl;

import com.alibaba.cloud.spring.boot.oss.env.OssProperties;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.Owner;
import com.google.common.collect.Lists;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import quick.pager.pcloud.dto.BucketDTO;
import quick.pager.pcloud.dto.ObjectSummaryDTO;
import quick.pager.pcloud.enmus.OSSTypeEnum;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.model.request.UploadRequest;
import quick.pager.pcloud.service.OSSCloudService;

@Service
public class AliyunOSSCloudServiceImpl implements OSSCloudService {

    private final OSS oss;

    @Value("${alibaba.cloud.bucket}")
    private String bucket;

    private final OssProperties ossProperties;

    @Autowired(required = false)
    public AliyunOSSCloudServiceImpl(OSS oss, OssProperties ossProperties) {
        this.oss = oss;
        this.ossProperties = ossProperties;
    }

    @Override
    public boolean support(final OSSTypeEnum ossType) {
        return OSSTypeEnum.ALIYUN.equals(ossType);
    }

    @Override
    public ResponseResult<List<BucketDTO>> bucket() {

        List<Bucket> buckets = oss.listBuckets();

        List<BucketDTO> dtos = Optional.ofNullable(buckets).orElseGet(Lists::newArrayList).stream()
                .map(item -> BucketDTO.builder()
                        .name(item.getName())
                        .creationDate(item.getCreationDate())
                        .location(item.getLocation())
                        .ownerName(Optional.ofNullable(item.getOwner()).map(Owner::getDisplayName).get())
                        .build())
                .collect(Collectors.toList());

        return ResponseResult.toSuccess(dtos);
    }

    @Override
    public ResponseResult<List<ObjectSummaryDTO>> objects(final UploadRequest request) {

        ListObjectsRequest listObjectsReq = new ListObjectsRequest();
        listObjectsReq.setBucketName(request.getBucketName());
        listObjectsReq.setDelimiter("/");
        listObjectsReq.setPrefix(request.getPrefix());
        listObjectsReq.setMarker(request.getMarker());
        listObjectsReq.setMaxKeys(request.getMaxKey());

        ObjectListing objectListing = oss.listObjects(listObjectsReq);

        Optional<ObjectListing> optional = Optional.ofNullable(objectListing);

        List<ObjectSummaryDTO> summaryDTOS = Lists.newArrayList();
        int total = 0;

        if (optional.isPresent()) {
            List<OSSObjectSummary> summaries = optional.get().getObjectSummaries();
            List<String> folders = optional.get().getCommonPrefixes();
            total = optional.get().getMaxKeys();

            summaryDTOS = folders.stream().map(item ->
                    ObjectSummaryDTO.builder()
                            .bucketName(item)
                            .file(Boolean.FALSE)
                            .key(item)
                            .lastModified(new Date())
                            .build()
            ).collect(Collectors.toList());

            List<ObjectSummaryDTO> dtos = summaries.stream().map(item ->
                    ObjectSummaryDTO.builder()
                            .bucketName(item.getBucketName())
                            .lastModified(item.getLastModified())
                            .eTag(item.getETag())
                            .key(item.getKey())
                            .size(item.getSize())
                            .file(Boolean.TRUE)
                            .storageClass(item.getStorageClass())
                            .ownerName(Optional.ofNullable(item.getOwner()).map(Owner::getDisplayName).get())
                            .url("https://".concat(bucket).concat(".").concat(ossProperties.getEndpoint()).concat("/").concat(item.getKey()))
                            .build()
            ).collect(Collectors.toList());

            summaryDTOS.addAll(dtos);

        }

        return ResponseResult.toSuccess(summaryDTOS, total);
    }
}

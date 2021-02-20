package quick.pager.pcloud.service.impl;

import com.google.common.collect.Lists;
import com.qcloud.cos.COS;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.COSObjectSummary;
import com.qcloud.cos.model.ListObjectsRequest;
import com.qcloud.cos.model.ObjectListing;
import com.qcloud.cos.model.Owner;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quick.pager.pcloud.dto.BucketDTO;
import quick.pager.pcloud.dto.ObjectSummaryDTO;
import quick.pager.pcloud.enmus.OSSTypeEnum;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.properties.COSCloudProperties;
import quick.pager.pcloud.request.UploadRequest;
import quick.pager.pcloud.service.OSSCloudService;

@Service
public class TencentOSSCloudServiceImpl implements OSSCloudService {

    private final COS cos;

    private COSCloudProperties cosCloudProperties;

    @Autowired(required = false)
    private TencentOSSCloudServiceImpl(COS cos, COSCloudProperties cosCloudProperties) {
        this.cos = cos;
        this.cosCloudProperties = cosCloudProperties;
    }

    @Override
    public boolean support(final OSSTypeEnum ossType) {
        return OSSTypeEnum.TENCENT.equals(ossType);
    }

    @Override
    public ResponseResult<List<BucketDTO>> bucket() {

        List<Bucket> buckets = cos.listBuckets();

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

        ObjectListing objectListing = cos.listObjects(listObjectsReq);

        Optional<ObjectListing> optional = Optional.ofNullable(objectListing);

        List<ObjectSummaryDTO> summaryDTOS = Lists.newArrayList();

        int total = 0;

        if (optional.isPresent()) {
            List<COSObjectSummary> summaries = optional.get().getObjectSummaries();
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
                            .url("https://".concat(cosCloudProperties.getBucket()).concat(".cos.").concat(cosCloudProperties.getRegionName()).concat(".myqcloud.com/").concat(item.getKey()))
                            .build()
            ).collect(Collectors.toList());

            summaryDTOS.addAll(dtos);

        }

        return ResponseResult.toSuccess(summaryDTOS, total);

    }
}

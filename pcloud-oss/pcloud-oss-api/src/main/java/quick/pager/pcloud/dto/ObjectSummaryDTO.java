package quick.pager.pcloud.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObjectSummaryDTO implements Serializable {

    /**
     * The name of the bucket in which this object is stored
     */
    private String bucketName;

    /**
     * The key under which this object is stored
     */
    private String key;

    /**
     * Hex encoded MD5 hash of this object's contents, as computed by Qcloud COS
     */
    private String eTag;

    /**
     * The size of this object, in bytes
     */
    private long size;

    /**
     * The date, according to Qcloud COS, when this object was last modified
     */
    private Date lastModified;

    /**
     * The class of storage used by Qcloud COS to store this object
     */
    private String storageClass;

    /**
     * The owner of this object - can be null if the requester doesn't have
     * permission to view object ownership information
     */
    private String ownerName;
    /**
     * 全路径
     */
    private String url;
    /**
     * true 是文件，false 是文件夹
     */
    private Boolean file;


}

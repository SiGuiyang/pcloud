package quick.pager.pcloud.model.request;

import java.io.Serializable;
import lombok.Data;

@Data
public class UploadRequest implements Serializable {
    /**
     * 桶
     */
    private String bucketName;
    /**
     * 前缀
     */
    private String prefix;
    /**
     * 指示列表结果应该从何处开始的关键标记
     */
    private String marker;
    /**
     * 类型
     */
    private String ossType;
    /**
     * 最大值
     */
    private Integer maxKey;
}

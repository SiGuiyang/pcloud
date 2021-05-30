package quick.pager.pcloud.oss.request;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * oss 上传内容请求类
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OSSUploadRequest implements Serializable {
    private static final long serialVersionUID = -5260475135880666597L;

    /**
     * 文件名
     */
    private String filename;
    /**
     * 上传类型
     */
    private String ossType;
    /**
     * 上传内容
     */
    private String content;
}

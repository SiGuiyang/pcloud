package quick.pager.pcloud.request;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户导出
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserDownloadRequest extends Request {
    private static final long serialVersionUID = 5856971347160873763L;
    /**
     * 用户主键集
     */
    private List<Long> ids;
    /**
     * 导出文件名称
     */
    private String fileName;
}

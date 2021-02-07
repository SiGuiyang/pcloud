package quick.pager.pcloud.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通用异常返回
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PCloudException extends RuntimeException {

    private static final long serialVersionUID = 7562952216658710834L;

    public PCloudException(String message) {
        super(message);
    }
}

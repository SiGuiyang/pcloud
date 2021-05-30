package quick.pager.pcloud.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import quick.pager.pcloud.enums.ErrorCodeEnums;

/**
 * 通用异常返回
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PCloudException extends RuntimeException {

    private static final long serialVersionUID = 7562952216658710834L;

    private Integer code;

    public PCloudException(String message) {
        super(message);
        this.code = 1000;
    }

    public PCloudException(ErrorCodeEnums errorCodeEnums) {
        super(errorCodeEnums.getDesc());
        this.code = errorCodeEnums.getCode();
    }
}

package quick.pager.pcloud.open.request;

import java.io.Serializable;
import lombok.Data;

/**
 * 开放平台登录
 *
 * @author siguiyang
 */
@Data
public class OpenLoginRequest implements Serializable {
    private static final long serialVersionUID = -828620882211161195L;

    /**
     * secureId
     */
    private String secureId;

    /**
     * secureKey
     */
    private String secureKey;


}

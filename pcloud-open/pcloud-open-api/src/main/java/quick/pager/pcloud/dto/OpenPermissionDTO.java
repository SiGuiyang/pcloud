package quick.pager.pcloud.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 权限
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenPermissionDTO implements Serializable {

    private static final long serialVersionUID = -2487029228536720244L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 是否拥有权限
     */
    private Boolean permission;
    /**
     * 名称
     */
    private String name;
    /**
     * 访问路径
     */
    private String resourceUrl;
}

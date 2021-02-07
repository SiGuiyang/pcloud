package quick.pager.pcloud.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色
 *
 * @author siguiyang
 * @version 3.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = -4660253489132506580L;

    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 更新操作人
     */
    private String gmtModifiedName;
    /**
     * 数据库记录发生更新的时间
     */
    private LocalDateTime gmtModifiedDate;
}

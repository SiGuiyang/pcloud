package quick.pager.pcloud.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * t_open_account
 *
 * @author Siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenAccountDTO implements Serializable {
    private static final long serialVersionUID = -8504290375287188010L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 状态 0 开启 1 禁用 2 冻结
     */
    private Integer status;

    /**
     * secureId
     */
    private String secureId;

    /**
     * secureKey
     */
    private String secureKey;

    /**
     * 修改人
     */
    private String gmtModifiedName;

    /**
     * 修改时间
     */
    private Date gmtModifiedDate;

}

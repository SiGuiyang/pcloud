package quick.pager.pcloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
@TableName("t_open_account")
public class OpenAccountDO implements Serializable {
    private static final long serialVersionUID = -8504290375287188010L;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 状态 0 开启 1 禁用 2 冻结
     *
     * @see quick.pager.pcloud.enums.OpenAccountStatusEnums
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
     * 创建人
     */
    private String gmtCreatedName;

    /**
     * 修改人
     */
    private String gmtModifiedName;

    /**
     * 创建时间
     */
    private Date gmtCreatedDate;

    /**
     * 修改时间
     */
    private Date gmtModifiedDate;

    /**
     * 逻辑删除字段
     */
    @TableLogic(value = "false", delval = "true")
    private Boolean deleteStatus;

}

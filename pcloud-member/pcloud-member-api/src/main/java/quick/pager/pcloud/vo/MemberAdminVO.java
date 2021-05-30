package quick.pager.pcloud.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会员管理
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberAdminVO implements Serializable {
    private static final long serialVersionUID = 4374726229231802690L;

    private Long id;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 姓
     */
    private String familyName;
    /**
     * 名
     */
    private String name;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 生日
     */
    private LocalDate birthday;
    /**
     * 登录标识
     */
    private Integer loginFlag;
    /**
     * 注册渠道
     */
    private Integer registerChannel;
    /**
     * 登录时间
     */
    private LocalDateTime loginTime;
    /**
     * 上次登录时间
     */
    private LocalDateTime lastLoginTime;
    /**
     * 数据库记录创建时间
     */
    private LocalDateTime gmtCreatedDate;
}

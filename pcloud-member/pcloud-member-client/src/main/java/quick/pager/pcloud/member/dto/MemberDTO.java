package quick.pager.pcloud.member.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO implements Serializable {
    private static final long serialVersionUID = 6508706832178926844L;

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
     * 生日
     */
    private LocalDate birthday;
    /**
     * 更新操作人
     */
    private String gmtModifiedName;
    /**
     * 数据库记录发生更新的时间
     */
    private LocalDateTime gmtModifiedDate;
}

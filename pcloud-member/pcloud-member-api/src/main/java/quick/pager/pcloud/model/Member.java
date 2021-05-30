package quick.pager.pcloud.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quick.pager.pcloud.annotation.IdGen;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("member")
public class Member implements Serializable {
    private static final long serialVersionUID = 61571980619484702L;

    @TableId
    @IdGen("member")
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
     * 创建人
     */
    private String gmtCreatedName;
    /**
     * 更新操作人
     */
    private String gmtModifiedName;
    /**
     * 数据库记录创建时间
     */
    private LocalDateTime gmtCreatedDate;
    /**
     * 数据库记录发生更新的时间
     */
    private LocalDateTime gmtModifiedDate;
    /**
     * 数据库删除标志<br />
     * 1: 删除
     * 0: 未删除
     */
    @TableLogic(value = "false", delval = "true")
    private Boolean deleteStatus;
}

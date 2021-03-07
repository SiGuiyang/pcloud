package quick.pager.pcloud.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 短信数据
 *
 * @author siguiyang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailDTO implements Serializable {

    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 收信人名称
     */
    private String username;
    /**
     * 收信人手机号码
     */
    private String phone;
    /**
     * 收信人头像
     */
    private String avatar;
    /**
     * 发信人名称
     */
    private String sendUsername;
    /**
     * 发信人手机号码
     */
    private String sendPhone;
    /**
     * 收信人头像
     */
    private String sendAvatar;
    /**
     * 站内信状态
     * false : 未读
     * true : 已读
     */
    private Boolean state;
    /**
     * 短信内容
     */
    private String description;
    /**
     * 创建人
     */
    private String gmtCreatedName;
    /**
     * 更新操作人
     */
    private String gmtModifiedName;
    /**
     * 数据库记录发生更新的时间
     */
    private LocalDateTime gmtModifiedDate;
}

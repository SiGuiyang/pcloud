package quick.pager.pcloud.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quick.pager.pcloud.annotation.IdGen;

/**
 * 系统登陆用户
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_user")
public class SysUserDO implements Serializable {

    private static final long serialVersionUID = 6227594831283103430L;

    /**
     * 数据库主键
     */
    @TableId
    @IdGen(value = "biz_sys_user")
    private Long id;
    /**
     * 登陆用户名
     */
    private String name;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 密码
     */
    private String password;

    /**
     * 0 ：正常 1：禁用
     */
    private Boolean state;
    /**
     * 头像
     */
    private String avatar;
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

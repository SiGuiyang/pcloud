package quick.pager.pcloud.dto;

import com.google.common.collect.Lists;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统用户
 *
 * @author siguiyang
 * @version 3.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysUserDTO implements Serializable {

    private static final long serialVersionUID = 6313168677365244170L;
    /**
     * 主键
     */
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
     * 0: 在线 1: 下线
     */
    private Boolean online;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 更新操作人
     */
    private String gmtModifiedName;
    /**
     * 数据库记录发生更新的时间
     */
    private LocalDateTime gmtModifiedDate;

    private List<RoleDTO> roles = new ArrayList<>();
    /**
     * 角色编码
     */
    private List<String> authorities;

    private List<MenuDTO> routers = Lists.newArrayList();

    private List<String> permissions = Lists.newArrayList();
}

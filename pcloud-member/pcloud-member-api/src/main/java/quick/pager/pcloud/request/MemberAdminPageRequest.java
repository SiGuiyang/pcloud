package quick.pager.pcloud.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 会员中心
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class MemberAdminPageRequest extends PageRequest {
    private static final long serialVersionUID = 7352561258193854871L;

    /**
     * 手机号码
     */
    private String phone;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;


}

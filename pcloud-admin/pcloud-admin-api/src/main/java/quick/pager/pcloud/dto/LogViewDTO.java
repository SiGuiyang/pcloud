package quick.pager.pcloud.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 日志量
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogViewDTO implements Serializable {
    private static final long serialVersionUID = -5821236297076801674L;

    /**
     * 人访问量
     */
    private Long dailyVisits;
    /**
     * 接口访问量
     */
    private Long dailyInterfaceVisits;
}

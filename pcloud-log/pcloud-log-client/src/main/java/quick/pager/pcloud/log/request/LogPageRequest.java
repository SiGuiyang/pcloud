package quick.pager.pcloud.log.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import quick.pager.pcloud.model.request.PageRequest;

/**
 * 日志保存
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogPageRequest extends PageRequest {


    private static final long serialVersionUID = 6295446004406041118L;
    /**
     * 访问人
     */
    private String name;
    /**
     * 访问时间
     */
    private LocalDate visitDate;
}

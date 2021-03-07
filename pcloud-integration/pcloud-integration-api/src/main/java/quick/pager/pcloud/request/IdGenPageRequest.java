package quick.pager.pcloud.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class IdGenPageRequest extends PageRequest {

    /**
     * 业务号段说明
     */
    private String description;
}

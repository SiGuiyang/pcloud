package quick.pager.pcloud.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import quick.pager.pcloud.model.request.PageRequest;

@EqualsAndHashCode(callSuper = true)
@Data
public class OpenResourcePageRequest extends PageRequest {

    private static final long serialVersionUID = -3589944990830270449L;
    /**
     * 名称
     */
    private String name;
}

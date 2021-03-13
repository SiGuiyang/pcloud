package quick.pager.pcloud.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单
 *
 * @author siguiyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuOtherRequest extends PageRequest {
    private static final long serialVersionUID = -7242388842224416719L;

    private String name;
}

package quick.pager.pcloud.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单管理
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderAdminVO implements Serializable {
    private static final long serialVersionUID = -2339234956005935303L;

    private Long id;
}

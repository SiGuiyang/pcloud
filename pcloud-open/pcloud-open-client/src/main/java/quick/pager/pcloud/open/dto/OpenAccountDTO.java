package quick.pager.pcloud.open.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenAccountDTO implements Serializable {

    private static final long serialVersionUID = 6326803018780948407L;
    private Long id;

    /**
     * secureId
     */
    private String secureId;

    /**
     * secureKey
     */
    private String secureKey;
    /**
     * 角色编码
     */
    private List<String> authorities;
}

package quick.pager.pcloud.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类列表
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassificationVO implements Serializable {
    private static final long serialVersionUID = 6268522494751242461L;

    private Long id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 序号
     */
    private Integer sequence;
}

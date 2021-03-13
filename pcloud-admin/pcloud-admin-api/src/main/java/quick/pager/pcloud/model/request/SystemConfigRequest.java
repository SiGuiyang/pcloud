package quick.pager.pcloud.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SystemConfigRequest extends PageRequest {
    private static final long serialVersionUID = -5469712897215941464L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 配置项名称
     */
    private String configName;
    /**
     * 配置项类型
     */
    private String configType;
    /**
     * 配置项值
     */
    private String configValue;
    /**
     * 配置项模块
     */
    private String module;
    /**
     * 配置项模块描述
     */
    private String description;

}

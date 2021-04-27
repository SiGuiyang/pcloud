package quick.pager.pcloud.model;

import java.io.Serializable;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * redis 延迟队列数据传输对象
 *
 * @author siguiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RedisDelayDTO implements Serializable {

    /**
     * spring beanName
     */
    private String beanName;

    /**
     * 执行参数
     */
    private Map<String, Object> params;

}

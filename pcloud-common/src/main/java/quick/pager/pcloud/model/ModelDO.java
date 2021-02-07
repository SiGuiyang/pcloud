package quick.pager.pcloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * model 基类
 *
 * @author siguiyang
 */
@Data
@SuperBuilder
public class ModelDO implements Serializable {
    private static final long serialVersionUID = 4400997987429664604L;


}

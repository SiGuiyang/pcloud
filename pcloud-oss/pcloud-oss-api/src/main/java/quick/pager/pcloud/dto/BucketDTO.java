package quick.pager.pcloud.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * bucket桶
 *
 * @author siguiyang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BucketDTO implements Serializable {

    /**
     * Bucket name
     */
    private String name;

    /**
     * Bucket owner
     */
    private String ownerName;

    /**
     * Bucket location
     */
    private String location;

    /**
     * Created date.
     */
    private Date creationDate;
}

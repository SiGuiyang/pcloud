package quick.pager.pcloud.dto;

import com.google.common.collect.Lists;
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
public class PermissionDTO implements Serializable {


    private static final long serialVersionUID = 6106613383509895940L;
    /**
     * 路由拥有的权限
     */
    private List<Long> hasRouters = Lists.newArrayList();
    /**
     * 路由
     */
    private List<MenuDTO> routers = Lists.newArrayList();

    /**
     * 拥有的资源权限
     */
    private List<Long> hasResources = Lists.newArrayList();
    /**
     * 资源权限
     */
    private List<ResourceTreeDTO> resources = Lists.newArrayList();
}

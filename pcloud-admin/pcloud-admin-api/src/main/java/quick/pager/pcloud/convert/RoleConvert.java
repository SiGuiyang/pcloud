package quick.pager.pcloud.convert;

import quick.pager.pcloud.dto.RoleDTO;
import quick.pager.pcloud.model.RoleDO;

/**
 * 角色转换器
 *
 * @author siguiyang
 */
public class RoleConvert {


    /**
     * DO -> DTO
     *
     * @param roleDO RoleDO
     * @return MenuDTO
     */
    public static RoleDTO convert(final RoleDO roleDO) {
        return RoleDTO.builder()
                .id(roleDO.getId())
                .roleCode(roleDO.getRoleCode())
                .name(roleDO.getName())
                .gmtModifiedDate(roleDO.getGmtModifiedDate())
                .gmtModifiedName(roleDO.getGmtModifiedName())
                .build();
    }
}

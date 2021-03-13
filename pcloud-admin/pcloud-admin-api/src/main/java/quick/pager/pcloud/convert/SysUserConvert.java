package quick.pager.pcloud.convert;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import quick.pager.pcloud.dto.SysUserDTO;
import quick.pager.pcloud.mapper.RoleMapper;
import quick.pager.pcloud.mapper.SysRoleMapper;
import quick.pager.pcloud.model.RoleDO;
import quick.pager.pcloud.model.SysRoleDO;
import quick.pager.pcloud.model.SysUserDO;

/**
 * 系统用户转换
 *
 * @author siguiyang
 */
public class SysUserConvert {


    private RedisTemplate<String, Object> redisTemplate;

    private SysRoleMapper sysRoleMapper;

    private RoleMapper roleMapper;


    public SysUserConvert(RedisTemplate<String, Object> redisTemplate,
                          SysRoleMapper sysRoleMapper,
                          RoleMapper roleMapper) {
        this.redisTemplate = redisTemplate;
        this.sysRoleMapper = sysRoleMapper;
        this.roleMapper = roleMapper;
    }

    /**
     * DO -> DTO
     *
     * @param sysUserDO SysUserDO
     * @return SysUserDTO
     */
    public SysUserDTO convert(final SysUserDO sysUserDO) {
        SysUserDTO sysUserDTO = SysUserDTO.builder()
                .id(sysUserDO.getId())
                .name(sysUserDO.getName())
                .avatar(sysUserDO.getAvatar())
                .password(sysUserDO.getPassword())
                .phone(sysUserDO.getPhone())
                .state(sysUserDO.getState())
                .online(StringUtils.isEmpty((String) redisTemplate.opsForValue().get("pcloud:token:".concat(sysUserDO.getPhone()))))
                .gmtModifiedDate(sysUserDO.getGmtModifiedDate())
                .gmtModifiedName(sysUserDO.getGmtModifiedName())
                .build();
        List<SysRoleDO> sysRoleDOS = this.sysRoleMapper.selectList(new LambdaQueryWrapper<SysRoleDO>().eq(SysRoleDO::getSysUserId, sysUserDO.getId()));

        if (CollectionUtils.isNotEmpty(sysRoleDOS)) {
            List<RoleDO> roleDOS = this.roleMapper.selectBatchIds(sysRoleDOS.stream().map(SysRoleDO::getRoleId).collect(Collectors.toList()));
            sysUserDTO.setRoles(roleDOS.stream().map(RoleConvert::convert).collect(Collectors.toList()));
        }
        return sysUserDTO;
    }

}

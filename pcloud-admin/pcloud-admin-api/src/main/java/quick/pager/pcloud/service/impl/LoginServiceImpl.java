package quick.pager.pcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import quick.pager.pcloud.convert.SysUserConvert;
import quick.pager.pcloud.dto.SysUserDTO;
import quick.pager.pcloud.helper.MenuHelper;
import quick.pager.pcloud.mapper.RoleMapper;
import quick.pager.pcloud.mapper.SysRoleMapper;
import quick.pager.pcloud.mapper.SysUserMapper;
import quick.pager.pcloud.model.SysUserDO;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.service.LoginService;
import quick.pager.pcloud.utils.Assert;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private MenuHelper menuHelper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult<SysUserDTO> login(String phone, String password) {

        LambdaQueryWrapper<SysUserDO> wrapper = new LambdaQueryWrapper<SysUserDO>()
                .eq(SysUserDO::getPhone, phone);
        SysUserDO sysUserDO = this.sysUserMapper.selectOne(wrapper);
        Assert.isTrue(Objects.nonNull(sysUserDO), () -> "用户不存在");

        Assert.isTrue(passwordEncoder.matches(password, sysUserDO.getPassword()), () -> "密码不正确");

        SysUserConvert sysUserConvert = new SysUserConvert(redisTemplate, sysRoleMapper, roleMapper);

        List<String> permissions = this.menuHelper.queryRoleBySysUserId(sysUserDO.getId());
        Assert.isTrue(CollectionUtils.isNotEmpty(permissions), () -> "没有权限访问");

        SysUserDTO userDTO = sysUserConvert.convert(sysUserDO);
        userDTO.setAuthorities(permissions);
        return ResponseResult.toSuccess(userDTO);
    }
}

package quick.pager.pcloud.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import quick.pager.pcloud.admin.client.AdminAuthClient;
import quick.pager.pcloud.admin.dto.UserDTO;
import quick.pager.pcloud.dto.OAuthUserDTO;
import quick.pager.pcloud.model.response.ResponseResult;

/**
 * 查询用户权限
 * 废弃类，已不使用
 * 新版获取token方法
 * {@link quick.pager.pcloud.controller.AuthController#login}
 *
 * @author siguiyang
 * @version 3.0
 */
@Service
@Deprecated
public class OAuthUserService implements UserDetailsService {

    @Resource
    private AdminAuthClient adminAuthClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ResponseResult<UserDTO> sysUserResponse = adminAuthClient.login(username, null);
        if (!sysUserResponse.check() || Objects.isNull(sysUserResponse.getData())) {
            throw new UsernameNotFoundException("用户不存在");
        }

        UserDTO sysUser = sysUserResponse.getData();

        Collection<? extends GrantedAuthority> grantedAuthorities = this.getGrantedAuthority(sysUser.getId());
        OAuthUserDTO authUserDTO = new OAuthUserDTO(username, sysUser.getPassword(), grantedAuthorities);
        authUserDTO.setId(sysUser.getId());
        authUserDTO.setName(sysUser.getName());
        authUserDTO.setAvatar(sysUser.getAvatar());

        return authUserDTO;
    }

    /**
     * 获取权限
     *
     * @param sysUserId 系统用户主键
     * @return 权限列表
     */
    private Collection<? extends GrantedAuthority> getGrantedAuthority(final Long sysUserId) {
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        ResponseResult<List<String>> result = adminAuthClient.getRolesBySysUserId(sysUserId);

        if (result.check()) {
            grantedAuthorities = Optional.ofNullable(result.getData()).orElse(Collections.emptyList()).stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        return grantedAuthorities;
    }

}

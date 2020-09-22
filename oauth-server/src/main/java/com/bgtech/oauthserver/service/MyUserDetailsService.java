package com.bgtech.oauthserver.service;

import com.bgtech.oauthserver.domain.dto.MallUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HuangJiefeng
 * @date 2020/9/16 0016 16:31
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * 授权的时候是对角色授权，而认证的时候应该基于资源，而不是角色，因为资源是不变的，而用户的角色是会变的
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MallUserDto user = userService.getUserByName(username);
        if (null == user) {
            throw new UsernameNotFoundException(username);
        }
//        return new MallUserDto()
        return null;

//        SysUser sysUser = userService.getUserByName(username);
//        if (null == sysUser) {
//            throw new UsernameNotFoundException(username);
//        }
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        for (SysRole role : sysUser.getRoleList()) {
//            for (SysPermission permission : role.getPermissionList()) {
//                authorities.add(new SimpleGrantedAuthority(permission.getCode()));
//            }
//        }

/*        UserDTO user = userService.getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 添加用户拥有的多个角色
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
        }*/

//        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}

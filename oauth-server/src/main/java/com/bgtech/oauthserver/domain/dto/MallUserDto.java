package com.bgtech.oauthserver.domain.dto;

import com.bgtech.oauthserver.domain.module.Role;
import com.bgtech.oauthserver.domain.module.MallUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * @author HuangJiefeng
 * @date 2020/9/18 0018 9:29
 */
@Data
public class MallUserDto extends MallUser implements UserDetails {

    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

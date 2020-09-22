package com.bgtech.oauthserver.domain.dto;

import com.bgtech.oauthserver.domain.module.Role;
import com.bgtech.oauthserver.domain.module.MallUser;
import lombok.Data;

import java.util.Set;

/**
 * @author HuangJiefeng
 * @date 2020/9/18 0018 9:29
 */
@Data
public class RoleDto extends Role {
    private Set<MallUser> mallUsers;
}

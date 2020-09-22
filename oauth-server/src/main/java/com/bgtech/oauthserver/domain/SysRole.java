package com.bgtech.oauthserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SysRole implements Serializable {

    private Long id;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色拥有的权限
     */
    private List<SysPermission> permissionList;

    public SysRole(String roleCode, String roleName) {
        this.roleCode = roleCode;
        this.roleName = roleName;
    }
}

package com.bgtech.oauthserver.service;

import com.bgtech.oauthserver.domain.SysUser;
import org.springframework.stereotype.Service;

/**
 * @author HuangJiefeng
 * @date 2020/9/16 0016 16:30
 */
@Service
public interface UserService {

    /**
     * 根据用户名获取系统用户
     */
    SysUser getUserByName(String username);
}

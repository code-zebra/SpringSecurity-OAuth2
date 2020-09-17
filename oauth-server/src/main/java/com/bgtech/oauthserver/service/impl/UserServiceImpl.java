package com.bgtech.oauthserver.service.impl;

import com.bgtech.oauthserver.dao.UserDao;
import com.bgtech.oauthserver.domain.SysUser;
import com.bgtech.oauthserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author HuangJiefeng
 * @date 2020/9/16 0016 16:42
 *
 * 权限分配
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public SysUser getUserByName(String username) {
        return userDao.selectByName(username);
    }
}

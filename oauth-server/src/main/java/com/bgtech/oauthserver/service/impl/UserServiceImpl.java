package com.bgtech.oauthserver.service.impl;

import com.bgtech.oauthserver.dao.UserMapper;
import com.bgtech.oauthserver.domain.dto.MallUserDto;
import com.bgtech.oauthserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author HuangJiefeng
 * @date 2020/9/16 0016 16:42
 *
 * 权限分配
 */
//@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public MallUserDto getUserByName(String username) {
        return userMapper.selectUserByUserName(username);
    }
}

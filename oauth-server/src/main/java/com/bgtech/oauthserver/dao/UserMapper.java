package com.bgtech.oauthserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bgtech.oauthserver.domain.dto.MallUserDto;
import com.bgtech.oauthserver.domain.module.MallUser;
import org.springframework.stereotype.Repository;

/**
 * @author HuangJiefeng
 * @date 2020/9/18 0018 9:34
 */
@Repository
public interface UserMapper extends BaseMapper<MallUser> {

    /**
     * 根据用户名获取用户信息，包括用户角色列表
     * @param username
     * @return
     */
    MallUserDto selectUserByUserName(String username);
}

package com.bgtech.oauthserver;

import com.bgtech.oauthserver.dao.UserMapper;
import com.bgtech.oauthserver.domain.dto.MallUserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author HuangJiefeng
 * @date 2020/9/18 0018 10:07
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MallUserTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void selectUserByUserNameTest() {
        MallUserDto zhangsan = userMapper.selectUserByUserName("zhangsan");
        System.out.println(zhangsan);
    }
}

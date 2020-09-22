package com.bgtech.oauthserver.controller;

import com.bgtech.oauthserver.domain.UserInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author HuangJiefeng
 * @date 2020/9/17 0017 15:44
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String sayHello(String name) {
        return "Hello, " + name;
    }

//    @PreAuthorize("hasAuthority('hi')")
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/sayHi")
    public String sayHi() {
        return "Hi，admin";
    }

    @RequestMapping("/userInfo")
    public UserInfo userInfo(Principal principal) {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(principal.getName());
        return userInfo;
    }
}

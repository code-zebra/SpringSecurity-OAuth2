package com.bgtech.oauthserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {

    /**
     * 会员列表页面
     */
    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("member/list");
        return modelAndView;
    }

    /**
     * 导出
     */
//    @PreAuthorize("hasAuthority('memberExport')")
    @ResponseBody
    @RequestMapping("/export")
    public String export() {
        return "export";
    }

}

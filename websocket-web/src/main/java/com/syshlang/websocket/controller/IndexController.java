/*
 * Copyright (c) 2019.
 * @File: IndexController.java
 * @Description:
 * @Author: sunys
 * @Date: 2019/9/2 下午4:41
 * @since:
 */

package com.syshlang.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sunys
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @RequestMapping("/toMonitorPage")
    public ModelAndView  toMonitorPage(HttpServletRequest request){
        ModelAndView monitor = new ModelAndView("monitor");
        monitor.addObject("contextPath",request.getContextPath());
        return monitor;
    }
}

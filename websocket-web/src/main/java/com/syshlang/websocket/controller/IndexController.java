/*
 * Copyright (c) 2019.
 * @File: IndexController.java
 * @Description:
 * @Author: sunys
 * @Date: 2019/9/2 下午4:41
 * @since:
 */

package com.syshlang.websocket.controller;

import com.google.gson.Gson;
import com.syshlang.websocket.common.constants.Status;
import com.syshlang.websocket.common.vo.MessageVo;
import com.syshlang.websocket.server.MonitorTextWebSocket;
import com.syshlang.websocket.server.MonitorWebSocket;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author sunys
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @RequestMapping(value = "toMonitorPage",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public ModelAndView  toMonitorPage(HttpServletRequest request){
        ModelAndView monitor = new ModelAndView("monitor");
        monitor.addObject("contextPath",request.getContextPath());
        return monitor;
    }

    @RequestMapping(value = "sendMessage1",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String sendMessage1(HttpServletRequest request) {
        MessageVo messageVo = new MessageVo();
        messageVo.setStatus(Status.SUCCESS);
        try {
            String message = request.getParameter("mesage");
            messageVo.setReceive(message);
            messageVo.setServer("服务端1");
            ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
            scheduledThreadPool.schedule(new Runnable() {
                @Override
                public void run() {
                    MonitorTextWebSocket.sendMessage();
                }
            }, 3, TimeUnit.SECONDS);
        } catch (Exception e) {
            messageVo.setStatus(Status.FAIL);
            e.printStackTrace();
        }
        Gson gson = new Gson();
        String json = gson.toJson(messageVo);
        return json;
    }

    @RequestMapping(value = "sendMessage2",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String sendMessage2(HttpServletRequest request) {
        MessageVo messageVo = new MessageVo();
        messageVo.setStatus(Status.SUCCESS);
        try {
            String message = request.getParameter("mesage");
            messageVo.setReceive(message);
            messageVo.setServer("服务端2");
            ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
            scheduledThreadPool.schedule(new Runnable() {
                @Override
                public void run() {
                    MonitorWebSocket.sendMessage();
                }
            }, 3, TimeUnit.SECONDS);
        } catch (Exception e) {
            messageVo.setStatus(Status.FAIL);
            e.printStackTrace();
        }
        Gson gson = new Gson();
        String json = gson.toJson(messageVo);
        return json;
    }
}

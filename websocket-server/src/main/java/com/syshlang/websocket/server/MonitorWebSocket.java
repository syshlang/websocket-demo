/*
 * Copyright (c) 2020.
 * @File: MonitorWebSocket.java
 * @Description:
 * @Author: sunys
 * @Date: 2020/11/6 下午1:11
 * @since:
 */

package com.syshlang.websocket.server;

import com.google.gson.Gson;
import com.syshlang.websocket.common.constants.Status;
import com.syshlang.websocket.common.vo.MessageVo;
import org.apache.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Monitor web socket.
 * 使用注解方式
 * @author sunys
 */
@ServerEndpoint(value = "/monitor2")
    public class MonitorWebSocket {
    private static Logger logger = Logger.getLogger(MonitorWebSocket.class);

    private  static Set<Session> monitorWebSocketSession = Collections.synchronizedSet(new HashSet<Session>());

    /**
     * 打开连接触发事件
     *
     * @param session the session
     */
    @OnOpen
    public void onOpen(Session session)  {
        monitorWebSocketSession.add(session);
        logger.debug("Opened new session in instance " +  session.getId());
    }

    /**
     * 收到消息触发事件
     *
     * @param session the session
     * @param message the message
     */
    @OnMessage
    public void onMessage(Session session, String message)  {
        logger.debug("Receive Mesage:"+message);
    }

    /**
     * 关闭连接触发事件
     *
     * @param session the session
     */
    @OnClose
    public void onClose(Session session) {
        monitorWebSocketSession.remove(session);
        logger.debug("WebSocket connection closed.");
    }

    /**
     * 传输消息错误触发事件
     *
     * @param throwable the throwable
     */
    @OnError
    public void onError(Throwable throwable) {
        logger.debug("WebSocket connection Error.",throwable);
    }

    /**
     * Send message.
     */
    public static void sendMessage(){
        for (Session session : monitorWebSocketSession) {
            if(session != null && session.isOpen()){
                synchronized (session) {
                    try {
                        MessageVo messageVo = new MessageVo();
                        messageVo.setStatus(Status.SUCCESS);
                        messageVo.setServer("服务端2");
                        messageVo.setSend("消息已收到！");
                        Gson gson = new Gson();
                        String json = gson.toJson(messageVo);
                        session.getBasicRemote().sendText(json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

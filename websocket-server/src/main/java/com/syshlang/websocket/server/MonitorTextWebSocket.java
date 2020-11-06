/*
 * Copyright (c) 2020.
 * @File: MonitorTextWebSocket.java
 * @Description:
 * @Author: sunys
 * @Date: 2020/11/5 下午4:56
 * @since:
 */

package com.syshlang.websocket.server;


import com.google.gson.Gson;
import com.syshlang.websocket.common.constants.Status;
import com.syshlang.websocket.common.vo.MessageVo;
import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

/**
 * The type Monitor text web socket.
 * 采用使继承类，xml配置方式方式
 * @author sunys
 */
public  class MonitorTextWebSocket extends TextWebSocketHandler {

    private static Logger logger = Logger.getLogger(MonitorTextWebSocket.class);

    private  static Set<WebSocketSession> monitorWebSocketSession = Collections.synchronizedSet(new HashSet<WebSocketSession>());

    /**
     * 建立连接后触发的回调
     * @param session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session)  {
        monitorWebSocketSession.add(session);
        Principal principal = session.getPrincipal();
        if (principal != null){
            String name = principal.getName();
            logger.debug("Opened new session in instance " +  name);
        }
    }

    /**
     *  收到消息时触发的回调
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session,message);
    }

    /**
     * 断开连接后触发的回调
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        monitorWebSocketSession.remove(session);
        logger.debug("WebSocket connection closed.");
    }

    /**
     * 传输消息出错时触发的回调
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close(CloseStatus.SERVER_ERROR);
        }
        monitorWebSocketSession.remove(session);
        logger.debug("WebSocket connection closed.",exception);
    }

    /**
     * 是否处理分片消息
     * @return
     */
    @Override
    public boolean supportsPartialMessages() {
        return super.supportsPartialMessages();
    }


    /**
     * Send message.
     */
    public static void sendMessage(){
        for (WebSocketSession webSocketSession : monitorWebSocketSession) {
            if(webSocketSession != null && webSocketSession.isOpen()){
                synchronized (webSocketSession) {
                    try {
                        MessageVo messageVo = new MessageVo();
                        messageVo.setStatus(Status.SUCCESS);
                        messageVo.setServer("服务端1");
                        messageVo.setSend("消息已收到！");
                        Gson gson = new Gson();
                        String json = gson.toJson(messageVo);
                        webSocketSession.sendMessage(new TextMessage(json));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}

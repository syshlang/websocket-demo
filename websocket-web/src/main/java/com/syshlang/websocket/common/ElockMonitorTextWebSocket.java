/*
 * Copyright (c) 2019.
 * @File: ElockMonitorTextWebSocket.java
 * @Description:
 * @Author: sunys
 * @Date: 2019/9/4 上午8:41
 * @since:
 */

package com.syshlang.websocket.common;


import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author sunys
 */
public  class ElockMonitorTextWebSocket extends TextWebSocketHandler {

    private static Logger logger = Logger.getLogger(ElockMonitorTextWebSocket.class);

    private  static List<WebSocketSession> elockMonitorWebSocketSession = Collections.synchronizedList(new ArrayList<WebSocketSession>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        elockMonitorWebSocketSession.add(session);
        logger.debug("Opened new session in instance " + this);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        session.sendMessage(message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        elockMonitorWebSocketSession.remove(session);
        logger.debug("WebSocket connection closed.");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close(CloseStatus.SERVER_ERROR);
        }
        elockMonitorWebSocketSession.remove(session);
        logger.debug("WebSocket connection closed.",exception);
    }

    public static void sendMessage(String message){
        for (WebSocketSession webSocketSession : elockMonitorWebSocketSession) {
            Principal principal = webSocketSession.getPrincipal();

        }
    }
}

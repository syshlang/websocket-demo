/*
 * Copyright (c) 2019.
 * @File: ElockMonitorTextWebSocket.java
 * @Description:
 * @Author: sunys
 * @Date: 2019/9/4 上午8:41
 * @since:
 */

package com.syshlang.websocket.common;


import freemarker.template.utility.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
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
        initQueryParameter(session);
        Object menuIdobj = session.getAttributes().get("menuId");
        elockMonitorWebSocketSession.add(session);
        Principal principal = session.getPrincipal();
        if (principal != null){
            String name = principal.getName();
            logger.debug("Opened new session in instance " +  name);
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session,message);
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

    private void initQueryParameter(WebSocketSession session){
        if (session == null){
            return;
        }
        URI sessionUri = session.getUri();
        if (sessionUri == null){
            return;
        }
        String queryParams  = sessionUri.getQuery();
        if (StringUtils.isEmpty(queryParams)) {
            return;
        }
        if("&".indexOf(queryParams) < 0){
            String[] strings = queryParams.split("=");
            session.getAttributes().put(strings[0],strings[1]);
        }else{
            String[] split = queryParams.split("&");
            for (int i = 0; i < split.length; i++) {
                String paramstr = split[i];
                if (!StringUtils.isEmpty(paramstr)){
                    String[] strings = paramstr.split("=");
                    session.getAttributes().put(strings[0],strings[1]);
                }
            }
        }
    }


    public static void sendMessage(String message){
        for (WebSocketSession webSocketSession : elockMonitorWebSocketSession) {
            Principal principal = webSocketSession.getPrincipal();

        }
    }
}

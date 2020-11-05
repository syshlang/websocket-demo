/*
 * Copyright (c) 2020.
 * @File: MonitorTextWebSocketTest.java
 * @Description:
 * @Author: sunys
 * @Date: 2020/11/5 下午4:55
 * @since:
 */

package com.syshlang.websocket.simple;


import org.junit.jupiter.api.Test;

import java.util.Date;

class MonitorTextWebSocketTest {

    @Test
    void sendMessage() {
        MonitorTextWebSocket.sendMessage("{\"data\":\""+new Date().toString()+"\"}");
    }
}
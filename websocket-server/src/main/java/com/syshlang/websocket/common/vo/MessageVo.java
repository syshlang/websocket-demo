/*
 * Copyright (c) 2020.
 * @File: MessageVo.java
 * @Description:
 * @Author: sunys
 * @Date: 2020/11/6 上午8:45
 * @since:
 */

package com.syshlang.websocket.common.vo;

import com.syshlang.websocket.common.constants.Status;

/**
 * @author sunys
 */
public class MessageVo {
    private Status status;
    private String server;
    private String receive;
    private String send;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }
}

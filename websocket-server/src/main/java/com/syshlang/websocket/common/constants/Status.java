/*
 * Copyright (c) 2020.
 * @File: Status.java
 * @Description:
 * @Author: sunys
 * @Date: 2020/11/6 上午9:27
 * @since:
 */

package com.syshlang.websocket.common.constants;

/**
 * @author sunys
 */

public enum Status {
    SUCCESS(0,"成功"),
    FAIL(1,"失败");
    private int status;
    private String describe;

    Status(int status, String describe) {
        this.status = status;
        this.describe = describe;
    }

    public int getStatus() {
        return status;
    }

    public String getDescribe() {
        return describe;
    }
}

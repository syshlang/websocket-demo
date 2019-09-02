/*
 * Copyright (c) 2019. GRGBanking
 * @File: websocket.js
 * @Description:
 * @Author: sunys
 * @Date: 2019/8/6 下午3:00
 * @since:
 */

var socketRequest = null;
function webSocketInit(websocketUrl,onmessageMethod){
    //检查浏览器是否支持WebSocket
    if (!window.WebSocket) {
        alert("您的浏览器不支持WebSocket，建议使用最新的谷歌浏览器！");
        return;
    }
    if (socketRequest && !socketRequest.closed) {
        return;
    }
    socketRequest = new WebSocket(websocketUrl);
    socketRequest.onopen = function (event) {
        if (socketRequest) {
            socketRequest.open && socketRequest.open(event, this);
            socketRequest.closed = false;
            websocket.send("客户端链接成功");
        }
    };

    socketRequest.onmessage = function (event) {
        if (typeof onmessageMethod == "function"){
            onmessageMethod(event);
        }
    };

    socketRequest.onclose = function (event) {
        console.log(event);
        socketRequest.closed = true;
    };

    socketRequest.onerror = function (event) {
        console.log(event);
        //错误重连
        socketRequest.error && socketRequest.error(event, this);
        if(socketRequest.connect){
            socketRequest.connect();
        }
    };
    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function (event) {
        socketRequest.onclose();
    };
}
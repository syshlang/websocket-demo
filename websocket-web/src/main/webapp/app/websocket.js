/*
 * Copyright (c) 2020.
 * @File: websocket.js
 * @Description:
 * @Author: sunys
 * @Date: 2020/11/6 下午3:41
 * @since:
 */

var SYSHLANG = jQuery;
(function() {
    $.extend({
        newWebSocket:function (config) {
            var defaults = {
                websocketUrl:null,
                onopenMethod:null,
                onmessageMethod:null,
                oncloseMethod:null,
            };
            if (jQuery.isPlainObject(config)) {
                defaults = $.extend(true, defaults, config);
            }
            //检查浏览器是否支持WebSocket
            if (!window.WebSocket) {
                alert("您的浏览器不支持WebSocket，建议使用最新的谷歌浏览器！");
                return;
            }
            if (defaults.socketRequest && !defaults.socketRequest.closed) {
                return;
            }
            var websocketUrl = "";
            if (window.location.protocol === 'http:') {
                websocketUrl = 'ws://' + window.location.host + ctx + defaults.websocketUrl;
            } else {
                websocketUrl = 'wss://' + window.location.host + ctx + defaults.websocketUrl;
            }
            defaults.socketRequest = new WebSocket(websocketUrl);
            defaults.socketRequest.onopen = function (event) {
                if (defaults.socketRequest) {
                    defaults.socketRequest.open && defaults.socketRequest.open(event, this);
                    defaults.socketRequest.closed = false;
                    if (jQuery.isFunction(defaults.onopenMethod)){
                        defaults.onopenMethod(event);
                    }
                }
            };

            defaults.socketRequest.onmessage = function (event) {
                if (jQuery.isFunction(defaults.onmessageMethod)){
                    defaults.onmessageMethod(event);
                }
            };

            defaults.socketRequest.onclose = function (event) {
                defaults.socketRequest.closed = true;
                if (jQuery.isFunction(defaults.oncloseMethod)){
                    defaults.oncloseMethod(event);
                }
            };

            defaults.socketRequest.onerror = function (event) {
                console.log(event);
                //错误重连
                defaults.socketRequest.error && defaults.socketRequest.error(event, this);
                if(defaults.socketRequest.connect){
                    defaults.socketRequest.connect();
                }
            };
            //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
            window.onbeforeunload = function (event) {
                defaults.socketRequest.onclose();
            };


        }
    });
})(SYSHLANG);
/**
 *
 * @param websocketUrl websocket URL地址
 * @param onopenMethod 当websocket打开通道建立链接时执行
 * @param onmessageMethod 当接收到消息时执行
 * @param oncloseMethod 当关闭通道时时执行
 */
SYSHLANG.webSocketInit = function(websocketUrl,onopenMethod,onmessageMethod,oncloseMethod){
    var socketRequest = null;
    //检查浏览器是否支持WebSocket
    if (!window.WebSocket) {
        alert("您的浏览器不支持WebSocket，建议使用最新的谷歌浏览器！");
        return;
    }
    if (socketRequest && !socketRequest.closed) {
        return;
    }
    if (window.location.protocol === 'http:') {
        websocketUrl = 'ws://' + window.location.host + ctx + websocketUrl;
    } else {
        websocketUrl = 'wss://' + window.location.host + ctx + websocketUrl;
    }
    socketRequest = new WebSocket(websocketUrl);
    socketRequest.onopen = function (event) {
        if (socketRequest) {
            socketRequest.open && socketRequest.open(event, this);
            socketRequest.closed = false;
            if (typeof onopenMethod == "function"){
                onopenMethod(event);
            }
        }
    };

    socketRequest.onmessage = function (event) {
        if (typeof onmessageMethod == "function"){
            onmessageMethod(event);
        }
    };

    socketRequest.onclose = function (event) {
        socketRequest.closed = true;
        if (typeof oncloseMethod == "function"){
            oncloseMethod(event);
        }
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
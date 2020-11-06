/*
 * Copyright (c) 2020.
 * @File: monitor.js
 * @Description:
 * @Author: sunys
 * @Date: 2020/11/5 下午3:57
 * @since:
 */

$(function(){
    SYSHLANG.newWebSocket({
        socketRequest:null,
        websocketUrl:websocketUrl1,
        onopenMethod:function () {
            $(".top_page").find("span").eq(0).show();
            $(".top_page").find("span").eq(1).hide();
        },
        onmessageMethod:function (message) {
            if (!message ) {
                return;
            }
            var objJSON=JSON.parse(message.data);
            showMessage(objJSON);
        },
        oncloseMethod:function () {
            $(".top_page").find("span").eq(0).hide();
            $(".top_page").find("span").eq(1).show();
        },
    });
    SYSHLANG.newWebSocket({
        socketRequest:null,
        websocketUrl:websocketUrl2,
        onopenMethod:function () {
            $(".top_page").find("span").eq(0).show();
            $(".top_page").find("span").eq(1).hide();
        },
        onmessageMethod:function (message) {
            if (!message ) {
                return;
            }
            var objJSON=JSON.parse(message.data);
            showMessage(objJSON);
        },
        oncloseMethod:function () {
            $(".top_page").find("span").eq(0).hide();
            $(".top_page").find("span").eq(1).show();
        },
    });
});

function showMessage(data) {
    if (data.status == 'SUCCESS'){
        $("#monitor").append("["+data.server + "]回复消息:"+ data.send+ "</br>");
    }
}

function sendMessage(server){
    var message = $("#messsage").val();
    $.ajax({
        url:ctx+'/index/sendMessage'+server+'.do',
        type:'post',
        data:{"mesage":message},
        dataType : "json",
        success:function(data) {
            $("#monitor").append("向["+data.server + "]发送消息:"+ message+ "</br>");
            if (data.status == 'SUCCESS'){
                $("#monitor").append("["+data.server + "]接收消息:成功</br>")
            } else {
                $("#monitor").append("["+data.server + "]接收消息:失败</br>")
            }
        }
    });
}
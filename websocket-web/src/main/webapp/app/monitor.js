/*
 * Copyright (c) 2020.
 * @File: monitor.js
 * @Description:
 * @Author: sunys
 * @Date: 2020/11/5 下午3:57
 * @since:
 */

$(function(){
    webSocketInit(websocketUrl,function () {
        $(".top_page").find("span").eq(0).show();
        $(".top_page").find("span").eq(1).hide();
    },function (message) {
        if (!message ) {
            return;
        }
        var objJSON=JSON.parse(message.data);
        showMessage(objJSON);
    },function () {
        $(".top_page").find("span").eq(0).hide();
        $(".top_page").find("span").eq(1).show();
    });
});

function showMessage(message) {
    console.log(message);
}

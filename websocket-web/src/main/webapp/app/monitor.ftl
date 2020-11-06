<!DOCTYPE html>
<html>
<head>
    <meta content="text/html;charset=UTF-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>WEBSOCKET</title>
    <link rel="stylesheet" type="text/css" href="../jquery-easyui-1.8.5/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../jquery-easyui-1.8.5/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/app/js/tipso/tipso.min.css"/>
    <script type="text/javascript" src="../jquery-easyui-1.8.5/jquery.min.js"></script>
    <script type="text/javascript" src="../jquery-easyui-1.8.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${contextPath}/app/js/tipso/tipso.min.js"></script>
    <script type="text/javascript" src="${contextPath}/app/websocket.js"></script>
    <link rel="stylesheet" type="text/css" href="${contextPath}/app/css/monitor.css"/>
    <script type="text/javascript">
        var ctx="${contextPath}";
        var  websocketUrl1 = "/monitor1";
        var  websocketUrl2 = "/monitor2";
    </script>
</head>
<body>
<div class="top_page">
    <div class="box_text box_page">
        服务状态：
        <span style="color:blue;display: none">正常</span><span style="color:red;">异常</span>
    </div>
</div>
<div class="bottom_page">
    <input type="text" id="messsage">
    <input type="button" value="发送消息1" onclick="sendMessage(1)"/>
    <input type="button" value="发送消息2" onclick="sendMessage(2)"/>
    <div id="monitor" data-options="region:'center',border:false" style="bottom: 50px;overflow: auto;">
    </div>
</div>

</body>
<script type="text/javascript" src="${contextPath}/app/monitor.js"></script>
</html>
<!DOCTYPE html>
<html>
<head>
    <meta content="text/html;charset=UTF-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Accordion Tools - jQuery EasyUI Demo</title>
    <link rel="stylesheet" type="text/css" href="../jquery-easyui-1.8.5/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../jquery-easyui-1.8.5/themes/icon.css">
    <script type="text/javascript" src="../jquery-easyui-1.8.5/jquery.min.js"></script>
    <script type="text/javascript" src="../jquery-easyui-1.8.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${contextPath}/app/websocket.js"></script>
    <script type="text/javascript" src="${contextPath}/app/monitor.js"></script>
    <link rel="stylesheet" type="text/css" href="${contextPath}/app/css/monitor.css"/>
</head>
<body>
<div id="monitor" data-options="region:'center',border:false" style="bottom: 50px;overflow: auto;">
</div>
<div class="bottom_page">
    <i></i>
    <div class="ebm_box_text ebm_box_page">
        <span><i class="text_round text_round_blue"></i>已联网(空闲)：13</span>
        <span><i class="text_round text_round_yellow"></i>已联网(忙碌)：8</span>
        <span><i class="text_round text_round_red"></i>未联网：3</span>
        <span>总计：<i class="text_round_text">24</i></span>
    </div>
</div>
</body>
</html>
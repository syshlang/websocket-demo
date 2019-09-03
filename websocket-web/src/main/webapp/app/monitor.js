var ONLINE_STATUS_FREE = 0;
var ONLINE_STATUS_BUSY = 1;
var ONLINE_STATUS_OFFLINE = 2;

$(function(){
    var  websocketUrl = "ws://${ipAddress}:${port}/ebm/ws/ElockMonitorServer";
    //webSocketInit(websocketUrl,resetMonitor);

    var elock=[
        {
            lockCode:'16802252',
            orgId:11,
            orgName:'广电银行广东总行',
            address:'广州市天河区高塘路228号',
            onLineStatus:0
        },
        {
            lockCode:'16802253',
            orgId:2221,
            orgName:'广电银行广东总行',
            address:'广州市天河区高塘路228号广州市天河区高塘路228号',
            onLineStatus:1
        },
        {
            lockCode:'16802256',
            orgId:331,
            orgName:'广电银行广东总行',
            address:'广州市天河区高塘路228号广州市天河区高塘路228号',
            onLineStatus:2
        },
        {
            lockCode:'16802259',
            orgId:333,
            orgName:'广电银行广东总行',
            address:'广州市天河区高塘路228号广州市天河区高塘路228号',
            onLineStatus:0
        },
        {
            lockCode:'16802269',
            orgId:339,
            orgName:'广电银行广东总行',
            address:'广州市天河区高塘路228号广州市天河区高塘路228号',
            onLineStatus:0
        },
        {
            lockCode:'16802264',
            orgId:334,
            orgName:'广电银行广东总行',
            address:'广州市天河区高塘路228号广州市天河区高塘路228号',
            onLineStatus:1
        }
    ];
    initMonitor(elock);
});


function initMonitor(data){
    if (data != null && data != undefined){
        var datas=[];
        for (var i = 0; i < data.length; i++) {
            var dataT = data[i];
            datas.push(dataT);
            for (var j = 1; j < 150; j++) {
                var dataE = {
                    lockCode:dataT.lockCode - j,
                    orgId:dataT.orgId,
                    orgName:dataT.orgName,
                    address:dataT.address,
                    onLineStatus:j%3
                };
                datas.push(dataE);
            }
        }
        addElockMonitor(datas);
    }
}

function addElockMonitor(data) {
    if (!data) {
        return;
    }
    for (var i = 0; i < data.length; i++) {
        var datum = data[i];
        var $ebm_box_ = $("#ebm_box_"+datum.orgId);
        if ($ebm_box_.length == 0){
            var ebmBoxHtml = "<div class='ebm_box' id='ebm_box_"+datum.orgId+"'></div>";
            var ebmBoxTextHtml = "<div class='ebm_box_title'><span>"+datum.orgName+"</span>" +
                "<div class='ebm_box_text'>" +
                "<span><i class='text_round text_round_blue'></i>已联网(空闲)：13</span>" +
                "<span><i class='text_round text_round_yellow'></i>已联网(忙碌)：8</span>" +
                "<span><i class='text_round text_round_red'></i>未联网：3</span>" +
                "<span>总计：<i class='text_round_text'>24</i></span>" +
                "</div></div>";
            var ebmBoxMainHtml = "<div class='ebm_box_main' id='ebm_box_main"+datum.orgId+"'></div>";
            $ebm_box_ = $(ebmBoxHtml).append($(ebmBoxTextHtml)).append($(ebmBoxMainHtml));
            $("#monitor").append($ebm_box_);
        }
        var ebmMainHtml = "<span class='ebm_main' id='ebm_main_"+datum.lockCode+"'></span>";
        var ebmDzsHtml = "<div class='ebm_dzs'>" +
            "<img src='"+ctx+"/app/images/icon_ebm.png'>" +
            "<span>"+datum.lockCode+"</span></div>";
        var $ebmDzs = $(ebmDzsHtml);
        if (datum.onLineStatus === ONLINE_STATUS_FREE){
            $ebmDzs.addClass("ebm_color_blue");
        }else if(datum.onLineStatus === ONLINE_STATUS_BUSY){
            $ebmDzs.addClass("ebm_color_yellow");
        }else {
            $ebmDzs.addClass("ebm_color_red");
        }
        var ebmAddHtml = "<p>安装地址</p><span>"+datum.address+"</span>";
        var $ebmMain = $(ebmMainHtml).append($ebmDzs);
        $ebm_box_.find(".ebm_box_main").append($ebmMain);
        $ebmMain.tipso({
            useTitle:false,
            background: '#ffffff',
            color:'#000000',
            content:ebmAddHtml
        });
    }
}

//换某一把锁具资料
function editElock(innerHTML) {
    var elock = eval("("+innerHTML+")");
    //1：在线，2：断线，3：离线，4：维护。
    var connectStatus = 'box_list_wly';//默认是离线
    if("1"==elock.connectionStatus){//在线
        var connectStatus = 'box_list_kx';
    }else if("2"==elock.connectionStatus){//2：断线
        var connectStatus = 'box_list_rwz';
    }else if("4"==elock.connectionStatus){//4：维护。
        var connectStatus = 'box_list_yfp';
    }
    document.getElementById(elock.id).innerHTML = "<span class='"+connectStatus+"'   title=\""+elock.title+"\" >" +elock.lockCode+
        "</span>";

    $('span.box_list_wly,span.box_list_yfp,span.box_list_kx,span.box_list_rwz').tipso({
        useTitle:true,
        //width:380,
        background:'#5280A8'
    });
}


//按下回车时，直接查询
function onFormKeyPress(event){
    if (event.keyCode == 13){
        resetMonitor();
    }
}


//回调方法
function callbackmethod(data){
    if(data.status=='success'){
        doSearch();
    }else{
        $.messager.alert(getCurrentLangText('prompt'),data.msg,'error');
    }
}


function resetMonitor(){
    GRG.showLoading("正在加载...");//正在保存数据中...
    var lockCode = $("#lockCode").val();
    var orgId = $("#orgId").val();
    $.ajax({
        url:ctx + '/elock/getElockMonitor.do',
        type:'post',
        dataType:'json',
        data:{
            lockCode : lockCode,
            orgId : orgId
        },
        success:function(data){
            if(data.status=='success'){
                var elockList = data.elockList;
                var divStr = "";
                for(var i = 0; elockList.length > i; i++){
                    //1：在线，2：断线，3：离线，4：维护。
                    var connectStatus = 'box_list_wly';//默认是离线
                    if("1"==elockList[i].connectionStatus){//在线
                        var connectStatus = 'box_list_kx';
                    }else if("2"==elockList[i].connectionStatus){//2：断线
                        var connectStatus = 'box_list_rwz';
                    }else if("4"==elockList[i].connectionStatus){//4：维护。
                        var connectStatus = 'box_list_yfp';
                    }
                    var elockStr = "<div  id=\""+elockList[i].id+"\" class=\"box_list\">" +
                        "<span class=\""+connectStatus+"\" title=\""+elockList[i].title+"\" >" +elockList[i].lockCode+
                        "</span>"+
                        "</div>";
                    divStr+=elockStr;
                }
                document.getElementById("list_body").innerHTML = divStr;

                $('span.box_list_wly,span.box_list_yfp,span.box_list_kx,span.box_list_rwz').tipso({
                    useTitle:true,
                    //width:380,
                    background:'#5280A8'
                });
            }else{
                $.messager.alert(getCurrentLangText('prompt'),data.msg,'error');//"该设备状态不是启用状态，已为你刷新界面"
            }
        }
    });
    GRG.closeLoading();
}

/**清空查询条件并查找*/
function resetFormMonitor(){
    $("#lockCode").val("");
    $("#orgName").val("");
    $("#orgId").val("");
    $("#status").combobox("setValue","");
    resetMonitor();
}
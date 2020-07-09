var userName;
function getUserName(){
    $.ajax({
        url:"/AirSystem/user/getUserName",
        type:"post",
        async:false,
        //contentType:"application/json;charset=utf-8",
        dataType:"text",
        success:function (data) {
            userName = data;
            //检查是否已经登录了账号
            if (userName == null || userName == "") {
                $("#btnBuy").attr("disabled", true);
                $("#navshow").append("<li><a href='home.jsp'>首页</a></li>");
                $("#navshow").append("<li><a href='register.jsp'>注册</a></li>");
                $("#navshow").append("<li><a href='login.jsp'>登录</a></li>");
            } else {
                $("#btnBuy").attr("disabled", false);
                $("#navshow").append("<li><a href='home.jsp'>首页</a></li>");
                $("#navshow").append("<li id='UserList'><a href='personalInfo.jsp'>" + userName + "</a></li>");
                $("#UserList").append("<ul class='dropdown-menu' style='text-align:center' id='menu'>" +
                    "<li><a href='personalInfo.jsp'>个人信息</a></li>" +
                    "<li><a href='changePwd.jsp'>修改密码</a></li>" +
                    "<li><a href='bill.jsp'>我的订单</a></li>" +
                    "<li><a href='#' onclick='logout()'>退出登录</a></li>" +
                    "</ul>");
                $("#UserList").hover(function () {
                    $("#menu").slideDown(200);
                }, function () {
                    $("#menu").slideUp(100);
                });
            }
        },
        error:function (msg) {
            alert("失败！" + msg);
        }
    });
}

//检查用户是否登录
function checkIsLogin(){
    if(userName == null || userName == ""){
        alert("当前未登录任何用户，是否登录？");
        location = "/AirSystem/jsp/login.jsp";
    }
}

var startCity;      //出发地
var arriveCity;     //目的地
var theDate;        //出发日期

//获取出发地、目的地和出发日期Cookie设置页面出发地、目的地和出发日期
function getSAD(){
    $.ajax({
        url:"/AirSystem/init/getSAD",
        type:"post",
        //contentType:"application/json;charset=utf-8",
        dataType:"json",
        success:function(sad){
            if(sad.startCity != null && sad.startCity != ""){
                startCity = sad.startCity;
            }else {
                startCity = "广州";
            }
            if(sad.arriveCity != null && sad.arriveCity != ""){
                arriveCity = sad.arriveCity;
            }else {
                arriveCity = "北京";
            }
            if(sad.theDate != null && sad.theDate != ""){
                theDate = sad.theDate;
            }else {
                var date = new Date();
                theDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
            }

            $("#dateTime").val(theDate);
        },
        error:function () {
            alert("请求数据失败！");
        }
    });
}

//获取所有城市，并根据Cookie中的值显示出来
function getCities(){
    $.ajax({
        url:"/AirSystem/init/getCities",
        type:"post",
        //contentType:"application/json;charset=utf-8",
        dataType:"json",
        success:function(cities){
            if ($("#selStartCity option").length == 0) {
                for (var i = 0; i < cities.length; i++) {
                    if (cities[i].cnCityName.includes(startCity)) {
                        $("#selStartCity").append("<option selected='selected'>" + cities[i].cnCityName + "</option>");
                    } else {
                        $("#selStartCity").append("<option>" + cities[i].cnCityName + "</option>");
                    }
                }
            }
            if ($("#selLastCity option").length == 0) {
                for (var i = 0; i < cities.length; i++) {
                    if (cities[i].cnCityName.includes(arriveCity)) {
                        $("#selLastCity").append("<option selected='selected'>" + cities[i].cnCityName + "</option>");
                    } else {
                        $("#selLastCity").append("<option>" + cities[i].cnCityName + "</option>");
                    }
                }
            }
        },
        error:function () {
            alert("请求数据失败！");
        }
    });
}

//初始化时间控件
function initTimePicker(){
    $("#dateTime").datetimepicker({
        format: 'Y-m-d',        //设置时间显示格式
        autoclose: true,        //设置选择时间后自动消失为true(这个没用)
        minView: 2,             //设置显示方式为2（这个没用）
        language: 'zh-CN',      //设置字符串方式为中文解码
        minDate: new Date(),    //设置可选日期为当前日期之后的日期
    });
}

//退出登录
function logout() {
    if (confirm("确定退出登录吗？")) {
        $.ajax({
            url: "/AirSystem/user/logout",
            type: "post",
            contentType: "application/json; charset=utf-8",
            success: function () {
                alert("退出成功！");
            },
            error: function () {
                alert("未知错误！");
            }
        });
        location = "/AirSystem/jsp/login.jsp";
    }
}

var pageIndex;      //当前页面索引
var pageCount;      //总页面数
var pageSize;       //页面显示行数
var ticketList;     //获取到的航班List

//获取机票
function getTickets() {
    $.ajax({
        url:"/AirSystem/ticket/getTickets",
        type:"post",
        contentType:"application/json", // 指定这个协议很重要
        dateType:"json",
        success:function (list) {
            ticketList = list;
            pageIndex = 1;
            pageSize = 10;
            pageCount = Math.ceil(list.length / pageSize);      //向上取整
            //显示机票
            showTickets(ticketList);

            //给机票表格添加鼠标移动事件
            tableRowMove();
        },
        error:function () {
            alert("获取数据失败！");
        }
    });
}

//根据获取到的航班List显示
function showTickets(list){
    if(list.length > 0) {
        var pageSize = 10;
        var itemCol = 9;            //返回的数据的列数
        $("#tabTickets tr").remove();
        if ($("#tabTickets tr").length <= 0) {
            $("#tabTickets").append("<tr id='thead' class='active'></tr>");
            $("#thead").append("<td>航空公司</td>");
            $("#thead").append("<td>航班号</td>");
            $("#thead").append("<td>出发机场</td>");
            $("#thead").append("<td>到达机场</td>");
            $("#thead").append("<td>出发时间</td>");
            $("#thead").append("<td>到达时间</td>");
            $("#thead").append("<td>机型</td>");
            $("#thead").append("<td>经停</td>");
            $("#thead").append("<td>飞行周期（星期）</td>");
            var row = list.length % pageSize == 0 ? pageSize : list.length % pageSize;
            var line = list.length / pageSize;
            if (pageIndex <= line) {
                row = pageSize;
            }
            for (var i = 0; i < pageSize; i++) {
                $("#tabTickets").append("<tr id='tabRow" + i + "' onclick='chooseToBuy(" + i + ")'></tr>");
            }

            for (var i = 0; i < row; i++) {
                $("#tabRow" + i).append("<td>" + list[(pageIndex - 1) * pageSize + i].company + "</td>");
                $("#tabRow" + i).append("<td>" + list[(pageIndex - 1) * pageSize + i].airCode + "</td>");
                $("#tabRow" + i).append("<td>" + list[(pageIndex - 1) * pageSize + i].startDrome + "</td>");
                $("#tabRow" + i).append("<td>" + list[(pageIndex - 1) * pageSize + i].arriveDrome + "</td>");
                $("#tabRow" + i).append("<td>" + list[(pageIndex - 1) * pageSize + i].startTime + "</td>");
                $("#tabRow" + i).append("<td>" + list[(pageIndex - 1) * pageSize + i].arriveTime + "</td>");
                $("#tabRow" + i).append("<td>" + list[(pageIndex - 1) * pageSize + i].mode + "</td>");
                $("#tabRow" + i).append("<td>" + list[(pageIndex - 1) * pageSize + i].airStop + "</td>");
                $("#tabRow" + i).append("<td>" + list[(pageIndex - 1) * pageSize + i].week + "</td>");
            }
        }
        //添加分页按钮和事件
        $("#pageBar").html("");
        $("#pageBar").append("<li id='preBtn'><a href='#' aria-label='Previous' onclick='ChangeTicketPage(" + (pageIndex - 1) + ")'><span aria-hidden='true'>&laquo;</span>Previous</a></li>");
        if (pageCount > 1) {
            for (var i = 1; i <= pageCount; i++) {
                $("#pageBar").append("<li><a href='#' onclick='ChangeTicketPage(" + i + ")'>" + i + "</a></li>");
            }
        }
        $("#pageBar").append("<li id='nextBtn'><a href='javascript:ChangeTicketPage(" + (pageIndex + 1) + ")' aria-label='Next'>Next<span aria-hidden='true'>&raquo;</span></a></li>");
    }else {
        $("#tabTickets tr").remove();
        if ($("#tabTickets tr").length <= 0) {
            $("#tabTickets").append("<tr id='thead' class='active'></tr>");
            $("#thead").append("<td>航空公司</td>");
            $("#thead").append("<td>航班号</td>");
            $("#thead").append("<td>出发机场</td>");
            $("#thead").append("<td>到达机场</td>");
            $("#thead").append("<td>出发时间</td>");
            $("#thead").append("<td>到达时间</td>");
            $("#thead").append("<td>机型</td>");
            $("#thead").append("<td>经停</td>");
            $("#thead").append("<td>飞行周期（星期）</td>");
            $("#tabTickets").append("<tr id='tabRow'></tr>");
            $("#tabRow").append("<td>没有数据</td>");
            $("#tabRow").append("<td>没有数据</td>");
            $("#tabRow").append("<td>没有数据</td>");
            $("#tabRow").append("<td>没有数据</td>");
            $("#tabRow").append("<td>没有数据</td>");
            $("#tabRow").append("<td>没有数据</td>");
            $("#tabRow").append("<td>没有数据</td>");
            $("#tabRow").append("<td>没有数据</td>");
            $("#tabRow").append("<td>没有数据</td>");
        }
    }
}


function tableRowMove() {
    //表格行的hover效果
    $("tr").not("#thead").mousemove(function () {
        $(this).attr("class", "active");
    });
    $("tr").not("#thead").mouseout(function () {
        $(this).attr("class", "");
    });
}


//分页事件
function ChangeTicketPage(i) {
    pageIndex = i;
    if (i == 0) {
        pageIndex = 1;
    }
    if (i == pageCount + 1) {
        pageIndex = pageCount;
    }
    //重新显示
    showTickets(ticketList);
    tableRowMove();
}

//保存所选择的航班信息到Cookie中，方便读取
function chooseToBuy(index) {
    var colArr = new Array();
    $("#tabTickets tr").eq(index+1).find("td").each(function () {
        colArr.push($(this).text());
    });
    var airCode = colArr[1];
    var startTime = colArr[4];
    var arriveTime = colArr[5];
    $.ajax({
        url:"/AirSystem/bill/setBillToBuy",
        type:"post",
        contentType:"application/json;charset=utf-8",
        data:JSON.stringify({airCode:airCode,startTime:startTime, arriveTime:arriveTime}),
        success:function () {
            location = "/AirSystem/jsp/buy.jsp";
        },
        error:function () {
            alert("未知错误！");
        }
    })
}

//获取用户个人信息
function getPersonalInfo(){
    $.ajax({
        url:"/AirSystem/user/getPersonalInfo",
        type:"post",
        //contentType:"application/json", // 指定这个协议很重要
        dateType:"json",
        success:function (user) {
            //显示结果
            $("#userName").text(user.name);
            $("#email").text(user.email);
            $("#date").text(user.date);
        },
        error:function () {
            alert("获取数据失败！");
        }
    });
}

var billMap;           //接收获取到的订单List

function getBills(){

    $.ajax({
        url:"/AirSystem/bill/getBills",
        type:"post",
        dataType:"json",
        success:function(para){
            billMap = para;
            pageIndex = 1;
            pageSize = 9;
            pageCount = Math.ceil(para["ticket"].length / pageSize);
            //显示订单
            showBills(para);
            //给订单表格添加鼠标移动事件
            tableRowMove();
        },
        error:function(para){
            alert("获取订单信息失败!")
        }
    })
}

//显示订单信息
function showBills(billMap){
    $("#tabBill tr").remove();

    if(billMap["ticket"].length > 0) {
        var pageSize = 10;
        var itemCol = 9;            //返回的数据的列数
        if ($("#tabBill tr").length <= 0) {
            $("#tabBill").append("<tr id='thead' class='active'></tr>");
            $("#thead").append("<td>航空公司</td>");
            $("#thead").append("<td>航班号</td>");
            $("#thead").append("<td>出发机场</td>");
            $("#thead").append("<td>到达机场</td>");
            $("#thead").append("<td>出发时间</td>");
            $("#thead").append("<td>到达时间</td>");
            $("#thead").append("<td>机型</td>");
            $("#thead").append("<td>经停</td>");
            $("#thead").append("<td>飞行周期（星期）</td>");
            $("#thead").append("<td>出发日期</td>");
            $("#thead").append("<td>下单日期</td>");

            var row = billMap["ticket"].length % pageSize == 0 ? pageSize : billMap["ticket"].length % pageSize;
            var line = billMap["ticket"].length / pageSize;
            if (pageIndex <= line) {
                row = pageSize;
            }
            for (var i = 0; i < pageSize; i++) {
                $("#tabBill").append("<tr id='tabRow" + i + "'></tr>");
            }

            for (var i = 0; i < row; i++) {
                $("#tabRow" + i).append("<td>" + billMap["ticket"][(pageIndex - 1) * pageSize + i].company + "</td>");
                $("#tabRow" + i).append("<td>" + billMap["ticket"][(pageIndex - 1) * pageSize + i].airCode + "</td>");
                $("#tabRow" + i).append("<td>" + billMap["ticket"][(pageIndex - 1) * pageSize + i].startDrome + "</td>");
                $("#tabRow" + i).append("<td>" + billMap["ticket"][(pageIndex - 1) * pageSize + i].arriveDrome + "</td>");
                $("#tabRow" + i).append("<td>" + billMap["ticket"][(pageIndex - 1) * pageSize + i].startTime + "</td>");
                $("#tabRow" + i).append("<td>" + billMap["ticket"][(pageIndex - 1) * pageSize + i].arriveTime + "</td>");
                $("#tabRow" + i).append("<td>" + billMap["ticket"][(pageIndex - 1) * pageSize + i].mode + "</td>");
                $("#tabRow" + i).append("<td>" + billMap["ticket"][(pageIndex - 1) * pageSize + i].airStop + "</td>");
                $("#tabRow" + i).append("<td>" + billMap["ticket"][(pageIndex - 1) * pageSize + i].week + "</td>");
                $("#tabRow" + i).append("<td>" + billMap["ticket"][(pageIndex - 1) * pageSize + i].date + "</td>");
                $("#tabRow" + i).append("<td>" + billMap["bill"][(pageIndex - 1) * pageSize + i].date + "</td>");
            }
        }
        $("#pageBar").html("");
        $("#pageBar").append("<li id='preBtn'><a href='#' aria-label='Previous' onclick='changeBillPage(" + (pageIndex - 1) + ")'><span aria-hidden='true'>&laquo;</span>Previous</a></li>");
        if (pageCount > 1) {
            for (var i = 1; i <= pageCount; i++) {
                $("#pageBar").append("<li><a href='#' onclick='changeBillPage(" + i + ")'>" + i + "</a></li>");
            }
        }
        $("#pageBar").append("<li id='nextBtn'><a href='javascript:changeBillPage(" + (pageIndex + 1) + ")' aria-label='Next'>Next<span aria-hidden='true'>&raquo;</span></a></li>");
    }else {
        if ($("#tabBill tr").length <= 0) {
            $("#tabBill").append("<tr id='thead' class='active'></tr>");
            $("#thead").append("<td>航空公司</td>");
            $("#thead").append("<td>航班号</td>");
            $("#thead").append("<td>出发机场</td>");
            $("#thead").append("<td>到达机场</td>");
            $("#thead").append("<td>出发时间</td>");
            $("#thead").append("<td>到达时间</td>");
            $("#thead").append("<td>机型</td>");
            $("#thead").append("<td>经停</td>");
            $("#thead").append("<td>飞行周期（星期）</td>");
            $("#thead").append("<td>出发日期</td>");
            $("#thead").append("<td>下单日期</td>");
            $("#tabBill").append("<tr id='tabRow'></tr>");
            $("#tabRow").append("<td></td>");
            $("#tabRow").append("<td></td>");
            $("#tabRow").append("<td></td>");
            $("#tabRow").append("<td></td>");
            $("#tabRow").append("<td></td>");
            $("#tabRow").append("<td>qin，你还未下过单！</td>");
            $("#tabRow").append("<td></td>");
            $("#tabRow").append("<td></td>");
            $("#tabRow").append("<td></td>");
            $("#tabRow").append("<td></td>");
            $("#tabRow").append("<td></td>");
        }
    }
}


//订单页面修改事件
function changeBillPage(i){
    pageIndex = i;
    if (i == 0) {
        pageIndex = 1;
    }
    if (i == pageCount + 1) {
        pageIndex = pageCount;
    }
    showBills(billMap);
    tableRowMove();
}


//从Cookie中获取到用户所选择的航班信息
function getTicketToBuy(){
    $.ajax({
        url:"/AirSystem/bill/getBillToBuy",
        type:"post",
        dataType:"json",
        success:function(ticket){
            showTicketToBuy(ticket);
        },
        error:function(s){
            alert(s);
        }
    })
}

//显示从Cookie中获取到的用户所选择的航班信息
function showTicketToBuy(ticket){
    $("#tabTickets tr").remove();
    $("#tabTickets").append("<tr id='thead' class='active'></tr>");
    $("#thead").append("<td>航空公司</td>");
    $("#thead").append("<td>航班号</td>");
    $("#thead").append("<td>出发机场</td>");
    $("#thead").append("<td>到达机场</td>");
    $("#thead").append("<td>出发时间</td>");
    $("#thead").append("<td>到达时间</td>");
    $("#thead").append("<td>机型</td>");
    $("#thead").append("<td>经停</td>");
    $("#thead").append("<td>飞行周期（星期）</td>");
    $("#thead").append("<td>出发日期</td>");
    $("#tabTickets").append("<tr id='tabRow'></tr>");
    if(ticket.company != ""){
        $("#tabRow").append("<td>"+ticket.company+"</td>");
        $("#tabRow").append("<td>"+ticket.airCode+"</td>");
        $("#tabRow").append("<td>"+ticket.startDrome+"</td>");
        $("#tabRow").append("<td>"+ticket.arriveDrome+"</td>");
        $("#tabRow").append("<td>"+ticket.startTime+"</td>");
        $("#tabRow").append("<td>"+ticket.arriveTime+"</td>");
        $("#tabRow").append("<td>"+ticket.mode+"</td>");
        $("#tabRow").append("<td>"+ticket.airStop+"</td>");
        $("#tabRow").append("<td>"+ticket.week+"</td>");
        $("#tabRow").append("<td>"+ticket.date+"</td>");
    }else {
        $("#tabRow").append("<td></td>");
        $("#tabRow").append("<td></td>");
        $("#tabRow").append("<td></td>");
        $("#tabRow").append("<td></td>");
        $("#tabRow").append("<td>qin，请转到查询页面，根据所需选择起始地址，然后点击搜索！</td>");
        $("#tabRow").append("<td></td>");
        $("#tabRow").append("<td></td>");
        $("#tabRow").append("<td></td>");
        $("#tabRow").append("<td></td>");
        $("#tabRow").append("<td></td>");
    }
}


//购买事件，发送请求保存订单到数据库
function buy() {
    $.ajax({
        url: "/AirSystem/bill/buyTicket",
        type: "post",
        success: function () {
            if (userName == null || userName == "") {
                if (confirm("当前未登录任何用户！是否登录？")) {
                    location = "/AirSystem/jsp/login.jsp";
                }
            } else {
                alert("购买成功！即将跳转到我的订单页面！");
                location = "/AirSystem/jsp/bill.jsp";
            }
        },
        error: function () {
            alert("购买失败！");
        }
    })
}
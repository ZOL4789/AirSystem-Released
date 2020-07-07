
function getUserName(){
    $.ajax({
        url:"/AirSystem/user/getUserName",
        type:"post",
        //contentType:"application/json;charset=utf-8",
        dataType:"text",
        success:function (userName) {
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

function getCities(){
    $.ajax({
        url:"/AirSystem/init/getCities",
        type:"post",
        contentType:"application/json;charset=utf-8",
        dataType:"json",
        success:function(cities){
            if ($("#selStartCity option").length == 0) {
                for (var i = 0; i < cities.length; i++) {
                    if (cities[i].cnCityName.includes("广州")) {
                        $("#selStartCity").append("<option selected='selected'>" + cities[i].cnCityName + "</option>");
                    } else {
                        $("#selStartCity").append("<option>" + cities[i].cnCityName + "</option>");
                    }
                }
            }
            if ($("#selLastCity option").length == 0) {
                for (var i = 0; i < cities.length; i++) {
                    if (cities[i].cnCityName.includes("北京")) {
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

function initTimePicker(){
    $("#dateTime").datetimepicker({
        format: 'Y-m-d',        //设置时间显示格式
        autoclose: true,        //设置选择时间后自动消失为true(这个没用)
        minView: 2,             //设置显示方式为2（这个没用）
        language: 'zh-CN',      //设置字符串方式为中文解码
        minDate: new Date(),    //设置可选日期为当前日期之后的日期
    });
}

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

var pageIndex = 1;
var pageCount = 1;
var pageSize = 10;
var ticketList;

//获取机票
function getTickets() {
    // var startCity = ${sessionScope.startCity};
    // alert(startCity);
    $.ajax({
        url:"/AirSystem/ticket/getTickets",
        type:"post",
        contentType:"application/json", // 指定这个协议很重要
        dateType:"json",
        success:function (list) {
            ticketList = list;
            pageCount = Math.ceil(list.length / pageSize);
            showTickets(ticketList);
        },
        error:function () {
            alert("获取数据失败！");
        }
    });
}

function showTickets(list){
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
        if(pageIndex <= line){
            row = pageSize;
        }
        for (var i = 0; i < pageSize; i++) {
            $("#tabTickets").append("<tr id='tabRow" + i + "' onclick='Buy(" + i + ")'></tr>");
        }

        for (var i = 0; i < row; i++) {
            $("#tabRow" + i).append("<td>" + list[(pageIndex-1)*pageSize + i ].airCode + "</td>");
            $("#tabRow" + i).append("<td>" + list[(pageIndex-1)*pageSize + i].company + "</td>");
            $("#tabRow" + i).append("<td>" + list[(pageIndex-1)*pageSize + i].airStartDrome + "</td>");
            $("#tabRow" + i).append("<td>" + list[(pageIndex-1)*pageSize + i].arriveDrome + "</td>");
            $("#tabRow" + i).append("<td>" + list[(pageIndex-1)*pageSize + i].startTime + "</td>");
            $("#tabRow" + i).append("<td>" + list[(pageIndex-1)*pageSize + i].arriveTime + "</td>");
            $("#tabRow" + i).append("<td>" + list[(pageIndex-1)*pageSize + i].mode + "</td>");
            $("#tabRow" + i).append("<td>" + list[(pageIndex-1)*pageSize + i].airStop + "</td>");
            $("#tabRow" + i).append("<td>" + list[(pageIndex-1)*pageSize + i].week + "</td>");
        }
    }
    $("#pageBar").html("");
    $("#pageBar").append("<li id='preBtn'><a href='#' aria-label='Previous' onclick='ChangePage(" + (pageIndex - 1) + ")'><span aria-hidden='true'>&laquo;</span>Previous</a></li>");
    if (pageCount > 1) {
        for (var i = 1; i <= pageCount; i++) {
            $("#pageBar").append("<li><a href='#' onclick='ChangePage(" + i + ")'>" + i + "</a></li>");
        }
    }
    $("#pageBar").append("<li id='nextBtn'><a href='javascript:ChangePage(" + (pageIndex + 1) + ")' aria-label='Next'>Next<span aria-hidden='true'>&raquo;</span></a></li>");
}


function tableRowMove() {
    //表格行的hover效果
    $("tr").mousemove(function () {
        $(this).attr("class", "active");
    });
    $("tr").mouseout(function () {
        $(this).attr("class", "");
    });
}


function ChangePage(i) {
    pageIndex = i;
    if (i == 0) {
        pageIndex = 1;
    }
    if (i == pageCount + 1) {
        pageIndex = pageCount;
    }
    showTickets(ticketList);
    tableRowMove();
}

function Buy(index) {
    var colArr = new Array();
    $("#tabTickets tr").eq(index+1).find("td").each(function () {
        colArr.push($(this).text());
    });
    var airCode = colArr[1];
    $.ajax({
        url:"/AirSystem/buy/buyTicket",
        type:"post",
        contentType:"text/xml;charset=utf-8",
        date:{airCode:airCode},
        success:function (msg) {
        },
        error:function (msg) {
            alert("购买失败！数据为：" + msg);
        }
    })
}

function getPersonalInfo(){
    $.ajax({
        url:"/AirSystem/user/getPersonalInfo",
        type:"post",
        //contentType:"application/json", // 指定这个协议很重要
        dateType:"json",
        success:function (user) {
            $("#userName").text(user.name);
            $("#email").text(user.email);
            $("#date").text(user.date);
        },
        error:function () {
            alert("获取数据失败！");
        }
    });
}


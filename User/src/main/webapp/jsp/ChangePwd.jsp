<%--
  Created by IntelliJ IDEA.
  User: ZOL
  Date: 2020/7/1
  Time: 20:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AirSystem-修改密码</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container-fluid">
    <nav class="navbar navbar-inverse" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <button id="switchNav" class="navbar-toggle" data-toggle="collapse"
                        data-target="#example-navbar-collapse">
                    <span class="sr-only">切换导航</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">AirSystem</a>
            </div>
            <div class="collapse navbar-collapse" id="example-navbar-collapse">
                <ul class="nav navbar-nav navbar-right" id="navshow">
                </ul>
            </div>
        </div>
    </nav>
    <div class="container">
        <div class="col-lg-2">
            <div>
                <input type="button" id="btnInfo" value="账号信息" class="btn btn-primary btn-block"/>
                <br />
                <p>可查看本账号的个人信息。</p>
            </div>
            <div>
                <input type="button" id="btnChangePwd" value="修改密码" class="btn btn-primary btn-block" disabled="true"/>
                <br />
                <p>可修改本账号的密码。</p>
            </div>
            <div>
                <input id="btnBill" value="我的订单" class="btn btn-primary btn-block"/>
                <br />
                <p>可查看本账号以往的购买记录。</p>
            </div>
            <div>
                <input id="btnLogout" value="退出登录" class="btn btn-danger btn-block" />
                <br />
                <p>注销本账号的登录。</p>
            </div>
        </div>
        <div class="col-lg-10">
            <div class="panel panel-primary">
                <div class="panel-title" style="font-size: 24px">
                    <p style="text-align: center;">修改密码</p>
                </div>
                <div class="panel-body" style="height: 100%">
                    <div class="row" style="margin-top: 200px;">
                        <div class="input-group col-lg-6 col-lg-offset-3" style="text-align: center">
                            <span ID="lblOldPassword" class="input-group-addon" Style="width: 150px">旧密码：</span>
                            <input type="text" id="txtOldPassword" class="form-control" placeholder="Old password" aria-describedby="basic-addon1"/>
                        </div>
                    </div>
                    <br />
                    <div class="row">
                        <div class="input-group col-lg-6 col-lg-offset-3" style="text-align: center">
                            <span id="lblNewPassword" runat="server" Style="width: 150px" class="input-group-addon input-group-lg" >请输入密码：</span>
                            <input type="text" id="txtNewPassword" runat="server" class="form-control" placeholder="New password" aria-describedby="basic-addon1"></input>
                        </div>
                    </div>
                    <br />
                    <div class="row">
                        <div class="input-group col-lg-6 col-lg-offset-3" style="text-align: center">
                            <span id="lblNewPasswordAgain" runat="server" Style="width: 150px" class="input-group-addon input-group-lg">请再次输入密码：</span>
                            <input type="text" id="txtNewPasswordAgain" class="form-control" placeholder="New password Again" aria-describedby="basic-addon1" ></input>
                        </div>
                    </div>
                    <br />
                    <div class="row">
                        <div class="col-lg-6 col-lg-offset-3" style="margin-bottom: 250px">
                            <input type="button" id="btnSubmit" value="修改" class="btn btn-primary col-lg-12" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="footer" class="container">
        <nav class="navbar navbar-default navbar-fixed-bottom">
            <div class="navbar-inner navbar-content-center">
                <p class="text-muted credit" style="padding: 20px; text-align: center">
                    &#169;2020&nbsp;AirSystem&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    (无)-经营性-2015-0023
                </p>
            </div>
        </nav>
    </div>
</div>
</body>
</html>

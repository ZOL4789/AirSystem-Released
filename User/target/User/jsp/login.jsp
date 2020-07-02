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
    <title>AirSystem-登录</title>
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
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="Home.aspx">首页</a></li>
                    <li><a href="Register.aspx">注册</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <div class="container">
        <div class="col-lg-2">
            <div>
                <input type="button" id="Button1" value="登录帮助" class="btn btn-primary btn-block" disabled="disabled"/><br />
                <p>用户名不包含空格。</p>
                <p>密码由一到十六个字符组成。</p>
            </div>
            <div>
                <input type="button" id="Button2" value="忘记密码" class="btn btn-primary btn-block" disabled="disabled"/><br />
                <p>忘记密码功能需要用户记住所注册的用户名及其绑定的邮箱。</p>
                <p>如果用户名及其绑定的邮箱一致，则点击忘记密码则将重置密码为用户名。</p>
                <p>忘记密码功能半年才能使用一次，请用户谨记密码。</p>
            </div>
            <div>
                <input type="button" id="Button3" value="关于登录" class="btn btn-primary btn-block"  disabled="disabled"/><br />
                <p>AirSystem只有登录才能使用购买功能，为登录的用户不能使用购买功能。</p>
                <p>登录的用户可以使用AirSystem的全部功能。</p>
                <p>登录的用户的某些个人隐私将暴露于AirSystem的管理员。</p>
            </div>
        </div>
        <div class="col-lg-10">
            <div class="panel panel-primary">
                <div class="panel-title">
                    <p style="text-align: center; font-size: 24px">登录</p>
                </div>
                <div class="panel-body" style="height: 750px">
                    <div class="row" style="margin-top: 200px">
                        <div class="input-group col-lg-6 col-lg-offset-3" style="text-align: center">
                            <span id="lblUserName" runat="server" class="input-group-addon" Style="width: 100px" >用户名：</span>
                            <input type="text" id="txtUserName"  class="form-control" placeholder="Username" aria-describedby="basic-addon1"></input>
                        </div>
                    </div>
                    <br />
                    <div class="row">
                        <div class="input-group col-lg-6 col-lg-offset-3" style="text-align: center">
                            <span ID="lblPassword" Style="width: 100px" class="input-group-addon input-group-lg" >密码：</span>
                            <input type="text" id="txtPassword" class="form-control" placeholder="Password" aria-describedby="basic-addon1" inputmode="password"></input>
                        </div>
                    </div>
                    <br />
                    <div class="row" style="margin-bottom: 250px">
                        <div class="col-lg-6 col-lg-offset-3">
                            <input type="button" id="btnSubmit" value="登录" class="btn btn-primary col-lg-12"/>
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

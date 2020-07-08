<%--
  Created by IntelliJ IDEA.
  User: dzx
  Date: 2020/7/2
  Time: 19:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AirSystem-注册</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            myFunction();
        });
        function myFunction() {
            var x="${sessionScope.result2}";
            if (x!=null&&x!=""){
                alert(x);
            }
        }

        //判断两次密码输入是否一致
        function pwdIsSame() {
            var pwd1=$("#txtPassword").val();
            var pwd2=$("#txtPasswordAgain").val();
            if(pwd1==pwd2){
                return true;
            }
            else{
                alert("两次密码输入不一致！");
                return false;
            }
        }
    </script>
</head>
<body>
<form method="post" action="/AirSystem/user/register">
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
                        <li><a href="home.jsp">首页</a></li>
                        <li><a href="login.jsp">登录</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="col-lg-2">
                <div>
                    <input type="button" id="Button1" value="注册协议" class="btn btn-primary btn-block" disabled="true"/><br />
                    <p>此账号仅用于AirSystem用户使用。</p>
                    <p>点击注册即表示同意AirSystem访问隐私，并且AirSystem保证不泄露用户隐私。</p>
                    <p>此账号由所注册用户所拥有，拥有者应受法律监管，切勿用于违法犯罪。</p>
                </div>
                <div>
                    <input type="button" id="Button2" value="注册指导" class="btn btn-primary btn-block" disabled="true"/><br />
                    <p>用户名不包含空格。</p>
                    <p>密码由一到十六个字符组成。</p>
                    <p>邮箱可用于找回密码。</p>
                </div>
                <div>
                    <input type="button" id="Button3" value="关于注册" class="btn btn-primary btn-block" disabled="true"/><br />
                    <p>注册账号可以享受AirSystem更多更好的服务。</p>
                    <p>注册账号最终解释权归AirSystem所有。</p>
                </div>
            </div>
            <div class="col-lg-10">
                <div class="panel panel-primary">
                    <div class="panel-title" style="font-size:24px">
                        <p style="text-align: center;">注册</p>
                    </div>
                    <div class="panel-body" style="height: 100%">
                        <div class="row" style="margin-top: 200px;">
                            <div class="input-group col-lg-6 col-lg-offset-3" style="text-align: center">
                                <span id="lblUserName" class="input-group-addon" Style="width: 150px" >用户名：</span>
                                <input type="text" name="userName" id="txtUserName" class="form-control" placeholder="Username" aria-describedby="basic-addon1"></input>
                            </div>
                        </div>
                        <br />
                        <div class="row">
                            <div class="input-group col-lg-6 col-lg-offset-3" style="text-align: center">
                                <span id="lblPassword" Style="width: 150px" class="input-group-addon input-group-lg" >请输入密码：</span>
                                <input type="text" name="password" id="txtPassword"  class="form-control" placeholder="Password" aria-describedby="basic-addon1"></input>
                            </div>
                        </div>
                        <br />
                        <div class="row">
                            <div class="input-group col-lg-6 col-lg-offset-3" style="text-align: center">
                                <span id="lblPasswordAgain" Style="width: 150px" class="input-group-addon input-group-lg" >请再次输入密码：</span>
                                <input type="text" name="passwordAgain" id="txtPasswordAgain" class="form-control" placeholder="Password Again" aria-describedby="basic-addon1"></input>
                            </div>
                        </div>
                        <br />
                        <div class="row">
                            <div class="input-group col-lg-6 col-lg-offset-3" style="text-align: center">
                                <span id="lblPhone" Style="width: 150px" class="input-group-addon input-group-lg">手机号码</span>
                                <input type="text" name="phone" id="txtPhone"  class="form-control" placeholder="PhoneNumber" aria-describedby="basic-addonl"></input>
                            </div>
                        </div>
                        <br />
                        <div class="row">
                            <div class="input-group col-lg-6 col-lg-offset-3" style="text-align: center">
                                <span id="lblEmail" Style="width: 150px" class="input-group-addon input-group-lg">邮箱：</span>
                                <input type="text" name="email" id="txtEmail"  class="form-control" placeholder="Email" aria-describedby="basic-addon1"></input>
                            </div>
                        </div>
                        <br />
                        <div class="row">
                            <div class="col-lg-6 col-lg-offset-3" style="margin-bottom:250px">
                                <input type="submit" id="btnSubmit" value="注册" class="btn btn-primary col-lg-12" onclick="return pwdIsSame()" />
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
</form>
</body>
</html>

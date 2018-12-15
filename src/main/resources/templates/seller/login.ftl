<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
    <#-- 边栏slidebar -->
    <#include "../common/nav.ftl">
    <#-- 主要内容Content -->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-4 column">
                    <form class="form-horizontal" role="form" method="post" action="/sell/seller/info/login">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">邮箱</label>
                            <div class="col-sm-10">
                                <input name="email" type="email" class="form-control" value="${email!''}"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">密码</label>
                            <div class="col-sm-10">
                                <input name="password" type="password" class="form-control" value="${password!''}"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <div class="checkbox">
                                    <label><input type="checkbox" />记住账号和密码</label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" class="btn btn-default">登录</button>
                                <a href="/sell/seller/info/toRegister">注册</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>

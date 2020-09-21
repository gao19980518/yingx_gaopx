<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>应学app后台管理系统</title>
    <link rel="icon" href="${path}/bootstrap/img/arrow-up.png" type="image/x-icon">
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.css">

    <%--引入jqgrid中主题css--%>
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/css/css/hot-sneaks/jquery-ui-1.8.16.custom.css">
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/boot/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/bootstrap/js/bootstrap.js"></script>
    <script src="${path}/bootstrap/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${path}/bootstrap/jqgrid/boot/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${path}/bootstrap/js/ajaxfileupload.js"></script>
</head>
<body>
<!--顶部导航-->
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- 导航条标签 -->
        <div class="navbar-header">
            <a class="navbar-brand" href="#">应学App后台管理系统</a>
        </div>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="#">欢迎：<span style="color: #2aabd2">${sessionScope.admin.username}</span></a></li>
            <li><a href="${path}/admin/safeOut">退出<span class="glyphicon glyphicon-log-out"
                                                        style="color: #3e8f3e"></span></a></li>
        </ul>
    </div><!-- /.container-fluid -->
</nav>
<!--栅格系统-->
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2">
            <!--左边手风琴部分-->
            <div class="panel-group" id="accordion" role="tablist" align="center" aria-multiselectable="true">
                <div class="panel panel-info">
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
                               aria-expanded="true" aria-controls="collapseOne">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a class="btn btn-info"
                                       href="javascript:$('#MainId').load('${path}/user/user.jsp')">展示用户</a></li>
                                <li><a class="btn btn-info"
                                       href="javascript:$('#MainId').load('${path}/echart-goeasy/echarts1.jsp')">用户统计</a>
                                </li>
                                <li><a class="btn btn-info"
                                       href="javascript:$('#MainId').load('${path}/echart-goeasy/echarts-map1.jsp')">用户分布</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-warning">
                    <div class="panel-heading" role="tab" id="headingTwo">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                类别管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a class="btn btn-warning"
                                       href="javascript:$('#MainId').load('${path}/category/category.jsp')">展示类别</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-success">
                    <div class="panel-heading" role="tab" id="headingThree">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                视频管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingThree">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a class="btn btn-success"
                                       href="javascript:$('#MainId').load('${path}/video/video.jsp')">展示视频</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-danger">
                    <div class="panel-heading" role="tab" id="headingFour">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                                日志管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingFour">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a class="btn btn-danger"
                                       href="javascript:$('#MainId').load('${path}/log/log.jsp')">展示日志</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading" role="tab" id="headingFive">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                                反馈管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingFive">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a class="btn btn-primary"
                                       href="javascript:$('#MainId').load('${path}/feedback/feedback.jsp')">展示反馈</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-10" id="MainId">
            <!--巨幕开始-->
            <div class="jumbotron">
                <h1>欢迎光临应学App后台管理系统</h1>
            </div>
            <!--右边轮播图部分-->
            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                <!-- 轮播图空间 -->
                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="3"></li>
                </ol>

                <!-- 轮播图内容 -->
                <div class="carousel-inner" align="center" role="listbox">
                    <div class="item active">
                        <img src="${path}/bootstrap/img/pic1.jpg">-->

                        <div class="carousel-caption">
                        </div>
                    </div>
                    <div class="item">
                        <img src="${path}/bootstrap/img/pic2.jpg">
                        <div class="carousel-caption">
                        </div>
                    </div>
                    <div class="item">
                        <img src="${path}/bootstrap/img/pic3.jpg">
                        <div class="carousel-caption">
                        </div>
                    </div>
                    <div class="item">
                        <img src="${path}/bootstrap/img/pic4.jpg">
                        <div class="carousel-caption">
                        </div>
                    </div>
                </div>

                <!-- 两个按钮 -->
                <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
        </div>
    </div>
</div>
<!--页脚-->
<div class="panel panel-footer text-center">@百知教育 baizhi@zparkhr.com</div>
<!--栅格系统-->


</body>
</html>

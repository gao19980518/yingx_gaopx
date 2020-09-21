<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function () {
        $("#logTable").jqGrid(
            {
                url: "${path}/log/queryPageLog",//发送请求 传递什么参数？  page页码    rows每页展示多少条
                editurl: "${path}/log/edit", //oper  三个值  edit add del
                datatype: "json",    //响应  拿到的返回值？page页码   rows当前页的数据  total总页数    records总条数
                rowNum: 2,
                rowList: [2, 10, 20, 30],
                pager: '#logPage',
                styleUI: "Bootstrap",
                height: "auto",
                autowidth: true,
                viewrecords: true,    //展示总条数
                colNames: ['Id', '名字', '操作', '操作时间', '状态'],
                colModel: [
                    {name: 'id', width: 55, align: "center"},
                    {name: 'username', width: 90, align: "center"},
                    {name: 'options', width: 100, align: "center"},
                    {name: 'date', align: "center"},
                    {name: 'status', align: "center"},
                ]
            });
        $("#logTable").jqGrid('navGrid', '#logPage', {edit: true, add: true, del: true},
            {},//操作修改之后的额外操作
            {
                //操作添加之后的额外操作
                //关闭文本框
                closeAfterAdd: true,
            },
            {});
    });
</script>
<%--初始化面板--%>
<div class="panel panel-danger">
    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>日志信息</h2>
    </div>
    <%--标签页--%>
    <div class="nav nav-tabs">
        <li class="active"><a>日志信息</a></li>
    </div>
    <table id="logTable"></table>
    <div id="logPage"></div>
</div>
</div>
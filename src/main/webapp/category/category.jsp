<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function () {
        //父表格
        $("#categoryTable").jqGrid(
            {
                url: "${path}/category/queryPage",//父表格提供数据
                editurl: "${path}/category/edit", //oper  三个值  edit add del
                closeAfterAdd: true, //关闭文本框
                datatype: "json",
                height: "auto",
                styleUI: "Bootstrap",
                autowidth: true,
                rowNum: 2,
                rowList: [2, 8, 10, 20, 30],
                pager: '#categoryPage',
                viewrecords: true,
                subGrid: true,//添加subgird支持
                caption: "展示类别信息",
                colNames: ['id', '类别名', '级别', '父类id'],
                colModel: [
                    {name: 'id', width: 55},
                    {name: 'name', editable: true, width: 90},
                    {name: 'levels', width: 100},
                    {name: 'parentId', width: 80, align: "right"},
                ],
                //子表格
                subGridRowExpanded: function (subgrid_id, row_id) {//第一个参数 子容器的id  第二个参数  当前行id
                    var subgrid_table_id = subgrid_id + "_t";
                    var pager_id = "p_" + subgrid_table_id;
                    //在子容器中，创建了一个子表格和子表格的分页工具
                    $("#" + subgrid_id).html(
                        "<table id='" + subgrid_table_id + "' class='scroll'></table>" +
                        "<div id='" + pager_id + "' class='scroll'></div>");
                    //对子表格进行初始化
                    $("#" + subgrid_table_id).jqGrid(
                        {
                            url: "${path}/category/queryPageSon?pid=" + row_id,//子表格提供数据
                            editurl: "${path}/category/edit?parentId=" + row_id, //oper  三个值  edit add del
                            datatype: "json",
                            rowNum: 2,
                            rowList: [2, 8, 10, 20, 30],
                            pager: pager_id,
                            styleUI: "Bootstrap",
                            autowidth: true,
                            height: '100%',
                            colNames: ['id', '类别名', '级别', '父类id'],
                            colModel: [
                                {name: 'id', width: 55},
                                {name: 'name', editable: true, width: 90},
                                {name: 'levels', width: 100},
                                {name: 'parentId', width: 80, align: "right"},
                            ]
                        });
                    $("#" + subgrid_table_id).jqGrid('navGrid',
                        "#" + pager_id, {
                            edit: true,
                            add: true,
                            del: true
                        }, {}, {}, {
                            //控制删除的额外操作
                            closeAfterDel: true,
                            //返回提示信息
                            afterSubmit: function (response) {
                                var message = response.responseJSON.message;
                                //将提示信息展示到span
                                $("#showMsg").html(message);
                                //展示警告框
                                $("#deleteMsg").show();
                                //自动关闭
                                setTimeout(function () {
                                    $("#deleteMsg").hide();
                                }, 3000)
                                return "123";
                            }
                        });
                },
            });
        $("#categoryTable").jqGrid('navGrid', '#categoryPage', {
            add: true,
            edit: true,
            del: true
        }, {}, {}, {
            //控制删除的额外操作
            closeAfterDel: true,
            //返回提示信息
            afterSubmit: function (response) {
                var message = response.responseJSON.message;
                //将提示信息展示到span
                $("#showMsg").html(message);
                //展示警告框
                $("#deleteMsg").show();
                //自动关闭
                setTimeout(function () {
                    $("#deleteMsg").hide();
                }, 3000)
                return "123";
            }
        });
    });
</script>
<%--初始化面板--%>
<div class="panel panel-warning">
    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>类别信息</h2>
    </div>
    <%--标签页--%>
    <div class="nav nav-tabs">
        <li class="active"><a>类别信息</a></li>
    </div>
    <!--警告框-->
    <div id="deleteMsg" style="width: 400px;display: none" class="alert alert-warning alert-dismissible" role="alert">
        <span id="showMsg"></span>
    </div>
    <table id="categoryTable"></table>
    <div id="categoryPage"></div>
</div>
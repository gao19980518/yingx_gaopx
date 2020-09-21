<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function () {
        $("#userTable").jqGrid(
            {
                url: "${path}/user/queryPageUser",//发送请求 传递什么参数？  page页码    rows每页展示多少条
                editurl: "${path}/user/edit", //oper  三个值  edit add del
                datatype: "json",    //响应  拿到的返回值？page页码   rows当前页的数据  total总页数    records总条数
                rowNum: 2,
                rowList: [2, 10, 20, 30],
                pager: '#userPage',
                styleUI: "Bootstrap",
                height: "auto",
                autowidth: true,
                viewrecords: true,    //展示总条数
                colNames: ['Id', '用户名', '手机号', '微信', '头像', '签名', '状态', '注册时间'],
                colModel: [
                    {name: 'id', width: 55},
                    {name: 'username', editable: true, width: 90},
                    {name: 'phone', editable: true, width: 100},
                    {name: 'wechat', editable: true, align: "center"},
                    {
                        name: 'headImg', editable: true, edittype: "file", align: "center",
                        formatter: function (cellvalue, options, rowObject) {
                            //三个参数  列的值 ，操作 ，行对象
                            return "<img width='120px' height='80px' src='" + cellvalue + "'/>";
                        }
                    },
                    {name: 'sign', editable: true, align: "center"},
                    {
                        name: 'status', width: 150, sortable: false,
                        formatter: function (cellvalue, options, rowObject) {
                            //三个参数  列的值 ，操作 ，行对象
                            if (cellvalue == "正常") {
                                return "<a class='btn btn-success'  onclick='updateStatus(\"" + cellvalue + "\",\"" + rowObject.id + "\")'>正常</a>"
                            } else {
                                return "<a class='btn btn-danger' onclick='updateStatus(\"" + cellvalue + "\",\"" + rowObject.id + "\")'>冻结</a>"
                            }
                        }
                    },
                    {name: 'createDate', align: "center"},
                ]
            });
        $("#userTable").jqGrid('navGrid', '#userPage', {edit: true, add: true, del: true},
            {},//操作修改之后的额外操作
            {
                //操作添加之后的额外操作
                //关闭文本框
                closeAfterAdd: true,
                //手动文件上传
                afterSubmit: function (response) {
                    var id = response.responseJSON.id;
                    alert(id)
                    //异步文件上传
                    $.ajaxFileUpload({
                        url: "${path}/user/uploadFile",
                        type: "post",
                        dataType: "text",
                        fileElementId: "headImg",//上传的文件域id
                        data: {id: id},
                        success: function () {
                            jQuery("#userTable").trigger("reloadGrid");
                        }
                    });
                    //必须要有返回值
                    return "123";
                }
            },
            {});
    });

    function updateStatus(status, id) {
        //修改状态
        if (status == "正常") {
            //发送修改请求
            $.ajax({
                url: "${path}/user/edit",
                type: "post",
                data: {"id": id, "status": "冻结", "oper": "edit"},
                success: function () {
                    //刷新表单
                    $("#userTable").trigger("reloadGrid");
                }
            })
        } else {
            //发送修改请求
            $.ajax({
                url: "${path}/user/edit",
                type: "post",
                data: {"id": id, "status": "正常", "oper": "edit"},
                success: function () {
                    //刷新表单
                    $("#userTable").trigger("reloadGrid");
                }
            })
        }
    }


    function sendCode() {
        var phone = $("#phone").val();
        alert(phone);
        //发送修改请求
        $.ajax({
            url: "${path}/user/sendCode",
            type: "post",
            data: {"phone": phone},
            success: function () {
                //刷新表单
                $("#userTable").trigger("reloadGrid");
            }
        })
    }

    function esayPoiOut() {
        alert("导出");
        //发送修改请求
        $.ajax({
            url: "${path}/user/esayPoiOut",
            type: "post",
            success: function () {
                //刷新表单
                $("#userTable").trigger("reloadGrid");
            }
        })
    }

    function esayPoiIn() {
        alert("导入");
        //发送修改请求
        $.ajax({
            url: "${path}/user/esayPoiIn",
            type: "post",
            success: function () {
                //刷新表单
                $("#userTable").trigger("reloadGrid");
            }
        })
    }
</script>
<%--初始化面板--%>
<div class="panel panel-info">
    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>用户信息</h2>
    </div>
    <%--标签页--%>
    <div class="nav nav-tabs">
        <li class="active"><a>用户信息</a></li>
    </div>
    <%--按钮--%>
    <div class="panel panel-body">
        <a class="btn btn-success" onclick="esayPoiOut()">导出用户数据</a>
        <a class="btn btn-danger" onclick="esayPoiIn()">导入用户</a>
        <div class="input-group" style="width: 400px;height: 30px;float: right">
            <input id="phone" type="text" class="form-control" placeholder="请输入手机号" aria-describedby="basic-addon2">
            <a class="input-group-addon" onclick="sendCode()" id="basic-addon2">发送验证码</a>
        </div>
    </div>
    <table id="userTable"></table>
    <div id="userPage"></div>
</div>
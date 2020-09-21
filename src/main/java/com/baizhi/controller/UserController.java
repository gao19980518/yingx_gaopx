package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.util.AliyunOssUtil;
import com.baizhi.util.AliyunSendMessageUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("edit")
    @ResponseBody
    public HashMap<String, Object> edit(User user, String oper) {
        HashMap<String, Object> map = new HashMap<>();
        if ("add".equals(oper)) {
            System.out.println("添加的user======" + user);
            String id = userService.add(user);
            map.put("id", id);
        }
        if ("edit".equals(oper)) {
            userService.edit(user);
        }
        if ("del".equals(oper)) {
            System.out.println("控制的删除user==" + user);
            userService.del(user);
        }
        return map;
    }

    //文件上传
    @RequestMapping("upload")
    public void upload(String id, MultipartFile headImg, HttpServletRequest request) {
        System.out.println("文件上传===" + headImg);
        //文件上传本地
        //获取绝对路径
        String path = request.getSession().getServletContext().getRealPath("/bootstrap/img");
        System.out.println("path=========" + path);
        //文件上传
        String cover = new Date().getTime() + "-" + headImg.getOriginalFilename();
        System.out.println("cover========" + cover);
        try {
            headImg.transferTo(new File(path, cover));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //修改当前用户的头像信息
        User user = new User();
        user.setId(id);
        user.setHeadImg(cover);
        userService.edit(user);
        System.out.println("user===" + user);

    }


    //文件上传
    @RequestMapping("uploadFile")
    public void uploadFile(String id, MultipartFile headImg, HttpServletRequest request) {
        byte[] content = new byte[0];
        try {
            content = headImg.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = new Date().getTime() + "-" + headImg.getOriginalFilename();
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4GAs8nVdqE2UTMxUHgw1";
        String accessKeySecret = "CzInzGr4yi6vF81Vsqp8ANEfpeKiOC";
        //yourBucktName
        String bucketName = "yingx-gaopx";  //存储空间名
        String fileName = "photo/" + name;
// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
// 上传Byte数组。
        ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(content));

// 关闭OSSClient。
        ossClient.shutdown();

        //截取视频封面
        //将封面上传到阿里云

        //修改用户数据
        User user = new User();
        user.setId(id);
        String cover = "https://yingx-gaopx.oss-cn-beijing.aliyuncs.com/" + fileName;
        user.setHeadImg(cover);
        userService.edit(user);

    }

    @ResponseBody
    @RequestMapping("queryPageUser")
    public HashMap<String, Object> queryPageUser(Integer page, Integer rows) {
        HashMap<String, Object> map = userService.queryPageUser(page, rows);
        return map;
    }

    @RequestMapping("sendCode")
    public void sendCode(String phone) {
        System.out.println("phone" + phone);
        //获取验证码
        String code = AliyunSendMessageUtil.getCode();
        System.out.println("验证码：" + code);
        //保存验证码     redis
        //发送短信
        String s = AliyunSendMessageUtil.sendCode(phone, code);
        System.out.println("s:" + s);


    }

    @RequestMapping("esayPoiOut")
    public void esayPoiOut() throws IOException {
        System.out.println("导出用户数据到Excel");
        List<User> list = userService.queryAll();

        List<User> list1 = new ArrayList<>();

        for (User u : list) {
            String[] split = u.getHeadImg().split("/");
            String fileName = split[4];
            //下载到本地的路径
            String filePath = "D:\\img\\" + fileName;
            AliyunOssUtil.testDownload("photo/" + fileName, filePath);
            u.setHeadImg(filePath);
            list1.add(u);
        }

        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息", "用户"), User.class, list1);

        workbook.write(new FileOutputStream(new File("D:/user.xls")));
        workbook.close();

    }

    @RequestMapping("esayPoiIn")
    public void esayPoiIn() {
        System.out.println("导入用户数据");
        //配置导入的相关参数
        ImportParams params = new ImportParams();
        params.setTitleRows(1);//表格标题行数
        params.setHeadRows(1);//表头所占行
        try {
            FileInputStream stream = new FileInputStream(new File("D:/user.xls"));
            List<User> list = ExcelImportUtil.importExcel(stream, User.class, params);
            list.forEach(user -> System.out.println(user));
            //批量入库
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

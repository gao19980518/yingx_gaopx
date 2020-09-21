package com.baizhi.controller;

import com.baizhi.entity.Video;
import com.baizhi.service.VideoService;
import com.baizhi.util.AliyunOssUtil;
import com.baizhi.util.IntercepterPhotoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService videoService;


    @ResponseBody
    @RequestMapping("/queryPageVideo")
    public HashMap<String, Object> queryPageVideo(Integer page, Integer rows) {
        System.out.println("分页展示视频");
        HashMap<String, Object> map = videoService.queryPageVideo(page, rows);
        return map;
    }

    @RequestMapping("edit")
    @ResponseBody
    public HashMap<String, Object> edit(Video video, String oper) {
        HashMap<String, Object> map = new HashMap<>();

        if ("add".equals(oper)) {
            System.out.println("添加的Video======" + video);
            String id = videoService.add(video);
            map.put("id", id);
        }
        if ("edit".equals(oper)) {
            map = videoService.delete(video);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println("key" + entry.getKey() + "---value" + entry.getValue());
            }

        }
        if ("del".equals(oper)) {
            System.out.println("删除的Video的Controller======" + video);
            map = videoService.delete(video);
        }
        return map;
    }


    //视频上传
    //不考虑封面
    @RequestMapping("uploadVideo")
    public void uploadVideo(String id, MultipartFile path, HttpServletRequest request) {
        System.out.println("id===" + id + "path==" + path);
        //获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/bootstrap/img");
        //获取上传的文件名
        String name = new Date().getTime() + "-" + path.getOriginalFilename();
        //1.进行文件上传
        AliyunOssUtil.uploadByteFile(path, "video/" + name);
        //获取远程视频存放路径
        String fetFilePath = "https://yingx-gaopx.oss-cn-beijing.aliyuncs.com/video/" + name;

        //获取封面文件名
        String[] split = name.split("\\.");
        String fileName = split[0] + ".jpg";
        String filePath = realPath + "\\" + fileName;

        //截取视频封面
        try {
            IntercepterPhotoUtil.fetchFrame(fetFilePath, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //上传本地封面文件
        AliyunOssUtil.uploadLocalFile("photo/" + fileName, filePath);

        //删除本地封面文件（在上传完封面后，把项目里的img删除）
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            file.delete();
        }


        //2.修改视频数据
        Video video = new Video();
        video.setId(id);
        video.setPath(fetFilePath);
        video.setCover("https://yingx-gaopx.oss-cn-beijing.aliyuncs.com/photo/" + fileName);
        videoService.edit(video);

    }

}

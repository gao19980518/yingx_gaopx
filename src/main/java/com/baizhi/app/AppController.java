package com.baizhi.app;

import com.baizhi.common.ResultCommon;
import com.baizhi.entity.Video;
import com.baizhi.service.CategoryService;
import com.baizhi.service.UserService;
import com.baizhi.service.VideoService;
import com.baizhi.util.AliyunSendMessageUtil;
import com.baizhi.vo.CategoryVo;
import com.baizhi.vo.UserVo;
import com.baizhi.vo.VideoVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("app")
public class AppController {
    @Resource
    private VideoService videoService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private UserService userService;

    @RequestMapping("getPhoneCode")
    public ResultCommon getPhoneCode(String phone) {
        //获取验证码
        String code = AliyunSendMessageUtil.getCode();
        //保存验证码
        //发送短信
        String message = AliyunSendMessageUtil.sendCode(phone, code);

        if (message.equals("发送成功")) {
            return new ResultCommon(phone, "验证码发送成功", "100");
        } else {
            return new ResultCommon(phone, "验证码发送失败", "104");
        }
    }

    //接口展示首页视屏
    @RequestMapping("queryByReleaseTime")
    public ResultCommon queryByReleaseTime() {
        List<VideoVo> videoVos = videoService.queryFirst();
        if (videoVos.size() != 0) {
            return new ResultCommon(videoVos, "查询成功", "100");

        } else {
            return new ResultCommon(null, "查询失败", "104");

        }
    }

    //模糊查询视频
    @RequestMapping("queryByLikeVideoName")
    public ResultCommon queryByLikeVideoName(String content) {
        System.out.println("模糊查询视屏层content" + content);
        List<VideoVo> videoVos = videoService.queryByLikeVideoName(content);
        if (videoVos.size() != 0) {
            return new ResultCommon(videoVos, "查询成功", "100");

        } else {
            return new ResultCommon(null, "查询失败", "104");

        }
    }

    //播放视频
    @RequestMapping("queryByVideoDetail")
    public ResultCommon queryByVideoDetail(String videoId, String cateId, String userId) {
        System.out.println("播放视频=videoId" + videoId + "cateId==" + cateId + "userId==" + userId);
        VideoVo videoVo = videoService.queryByVideoDetail(videoId, "10", "1");
        System.out.println("controller===" + videoVo);
        if (videoVo != null) {
            return new ResultCommon(videoVo, "查询成功", "100");

        } else {
            return new ResultCommon(null, "查询失败", "104");

        }
    }

    @RequestMapping("save")
    public ResultCommon save(String description, String videoFile, String videoTitle, String categoryId, String userId) {
        Video video = new Video();
        video.setBiref(description);
        video.setPath(videoFile);
        video.setTitle(videoTitle);
        video.setCategoryId(categoryId);
        video.setUserId(userId);
        String id = videoService.add(video);
        if (id != null) {
            return new ResultCommon(id, "查询成功", "100");

        } else {
            return new ResultCommon(null, "查询失败", "104");

        }
    }

    //接口查询类别
    @RequestMapping("queryAllCategory")
    public ResultCommon queryAllCategory() {
        System.out.println("前台查询类别controller");
        List<CategoryVo> categoryVos = categoryService.queryAllCategory();
        if (categoryVos.size() != 0) {
            return new ResultCommon(categoryVos, "查询成功", "100");

        } else {
            return new ResultCommon(null, "查询失败", "104");

        }
    }

    //查询二级类别下的视频
    @RequestMapping("queryCateVideoList")
    public ResultCommon queryCateVideoList(String cateId) {
        List<VideoVo> videoVos = videoService.queryCateVideoList(cateId);
        if (videoVos.size() != 0) {
            return new ResultCommon(videoVos, "查询成功", "100");
        } else {
            return new ResultCommon(null, "查询成功", "104");
        }
    }

    //接口展示动态
    @RequestMapping("queryByUserMoving")
    public ResultCommon queryByUserMoving(String userId) {
        System.out.println("前台查询动态controller");
        List<VideoVo> videoVos = videoService.queryByUserMoving(userId);
        if (videoVos.size() != 0) {
            return new ResultCommon(videoVos, "查询成功", "100");

        } else {
            return new ResultCommon(null, "查询失败", "104");

        }
    }

    //接口查询用户信息
    @RequestMapping("queryByUserDetail")
    public ResultCommon queryByUserDetail(String userId) {
        System.out.println("前台查询用户userId" + userId);
        UserVo userVo = userService.queryByUserDetail(userId);
        if (userVo != null) {
            return new ResultCommon(userVo, "查询成功", "100");

        } else {
            return new ResultCommon(null, "查询失败", "104");

        }
    }


}

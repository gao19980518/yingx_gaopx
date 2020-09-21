package com.baizhi.service;

import com.baizhi.entity.Video;
import com.baizhi.vo.VideoVo;

import java.util.HashMap;
import java.util.List;

public interface VideoService {
    public HashMap<String, Object> queryPageVideo(Integer page, Integer rows);

    public String add(Video video);

    public void edit(Video video);

    public void del(Video video);

    public HashMap<String, Object> delete(Video video);


    //测试接口
    public List<VideoVo> queryFirst();

    public List<VideoVo> queryByLikeVideoName(String content);

    //播放视屏
    public VideoVo queryByVideoDetail(String videoId, String cateId, String userId);

    //点击二级类别展示视频
    public List<VideoVo> queryCateVideoList(String cateId);

    //展示动态
    public List<VideoVo> queryByUserMoving(String userId);

}

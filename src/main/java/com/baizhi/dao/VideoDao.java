package com.baizhi.dao;

import com.baizhi.entity.Video;
import com.baizhi.po.VideoPo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoDao extends Mapper<Video> {
    public List<VideoPo> queryFirst();

    public List<VideoPo> queryByLikeVideoName(@Param("content") String content);

    //展示动态接口
    public List<VideoPo> queryByUserMoving(String userId);

    public VideoPo queryByVideoDetail(@Param("videoId") String videoId, @Param("userId") String userId);

    //播放视频下面推荐同一类别下视频
    public List<VideoPo> queryCateVideo(@Param("cateId") String cateId);

    //点击二级类别播放视频
    public List<VideoPo> queryCateVideoList(@Param("cateId") String cateId);
}

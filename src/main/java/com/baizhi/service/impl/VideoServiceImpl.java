package com.baizhi.service.impl;

import com.baizhi.annotation.AddLog;
import com.baizhi.dao.VideoDao;
import com.baizhi.entity.Video;
import com.baizhi.po.VideoPo;
import com.baizhi.service.VideoService;
import com.baizhi.util.AliyunOssUtil;
import com.baizhi.vo.VideoVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoDao videoDao;

    @Override
    @AddLog(value = "查询视频数据")
    public HashMap<String, Object> queryPageVideo(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        Video video = new Video();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Video> videos = videoDao.selectByRowBounds(video, rowBounds);
        int count = videoDao.selectCount(video);
        map.put("total", (count % rows == 0) ? (count / rows) : (count / rows + 1));
        map.put("records", count);
        map.put("page", page);
        map.put("rows", videos);
        return map;
    }

    @Override
    @AddLog(value = "添加视频数据")
    public String add(Video video) {
        String id = UUID.randomUUID().toString();
        video.setId(id);
        video.setPublishDate(new Date());
        video.setUserId("1");
        video.setGroupId("1");
        video.setCategoryId("10");
        videoDao.insertSelective(video);
        return id;
    }

    @Override
    @AddLog(value = "修改视频数据")
    public void edit(Video video) {
        System.out.println("service的修改" + video);
        videoDao.updateByPrimaryKeySelective(video);
    }

    @Override
    public void del(Video video) {
    }

    @Override
    @AddLog(value = "删除视频数据")
    public HashMap<String, Object> delete(Video video) {
        System.out.println("删除video的service" + video);
        HashMap<String, Object> map = new HashMap<>();
        try {
            Video video1 = videoDao.selectByPrimaryKey(video);
            String path = video1.getPath();
            String cover = video1.getCover();

            String[] split = path.split("/");
            String[] split1 = cover.split("/");
            String videoPath = split[split.length - 2] + "/" + split[split.length - 1];
            String coverPath = split1[split1.length - 2] + "/" + split1[split1.length - 1];
            System.out.println("videoPath====" + videoPath);
            AliyunOssUtil.TestDelete(videoPath);
            System.out.println("coverPath===" + coverPath);
            AliyunOssUtil.TestDelete(coverPath);

            videoDao.deleteByPrimaryKey(video);
            map.put("message", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", "删除失败");
        }
        return map;
    }

    //测试展示视频接口
    @Override
    public List<VideoVo> queryFirst() {
        List<VideoVo> list = new ArrayList<>();
        try {
            //查询连接的数据
            List<VideoPo> videoPos = videoDao.queryFirst();
            //转化日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //将po对象的值赋值给vo对象
            for (VideoPo p : videoPos) {
                String time = sdf.format(p.getPublishDate());
                VideoVo v = new VideoVo(p.getId(), p.getTitle(), p.getCover(), p.getPath(), time, p.getBiref(), 0, p.getName(), p.getHeadImg());
                list.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<VideoVo> queryByLikeVideoName(String content) {
        System.out.println("模糊查询service==" + content);
        List<VideoVo> list = new ArrayList<>();
        try {
            //查询连接的数据
            List<VideoPo> videoPos = videoDao.queryByLikeVideoName(content);
            //转化日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //将po对象的值赋值给vo对象
            for (VideoPo p : videoPos) {
                String time = sdf.format(p.getPublishDate());
                VideoVo v = new VideoVo(p.getId(), p.getTitle(), p.getCover(), p.getPath(), time, p.getBiref(), 0, p.getName(), p.getCategoryId(), p.getUserId(), p.getUsername());
                list.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //播放视屏
    @Override
    public VideoVo queryByVideoDetail(String videoId, String cateId, String userId) {
        VideoVo videoVo = null;
        List<VideoVo> list = new ArrayList<>();
        try {
            //转化日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            //根据类别id查询相关视频   查询连接的数据
            List<VideoPo> videoPos = videoDao.queryCateVideo(cateId);
            for (VideoPo p : videoPos) {
                String time = sdf.format(p.getPublishDate());
                VideoVo v = new VideoVo(p.getId(), p.getTitle(), p.getCover(), p.getPath(), time, p.getBiref(), 0, p.getName(), p.getCategoryId(), p.getUserId());
                list.add(v);
            }
            //查询连接的数据
            VideoPo videoPo = videoDao.queryByVideoDetail(videoId, userId);
            System.out.println("sevice的videoPo" + videoPo);

            //将po对象的值赋值给vo对象
            String time = sdf.format(videoPo.getPublishDate());
            videoVo = new VideoVo(videoPo.getId(), videoPo.getTitle(), videoPo.getCover(), videoPo.getPath(), time, videoPo.getBiref(), 0, videoPo.getName(), videoPo.getCategoryId(), videoPo.getUserId(), videoPo.getHeadImg(), videoPo.getUsername(), 0, "true", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videoVo;
    }

    //二级类别下展示视频
    @Override
    public List<VideoVo> queryCateVideoList(String cateId) {
        ArrayList<VideoVo> videoVos = new ArrayList<>();
        try {
            //查询连接的数据
            List<VideoPo> videoPos = videoDao.queryCateVideoList(cateId);
            //转化日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //将po对象的值赋给vo对象
            for (VideoPo p : videoPos) {
                String time = sdf.format(p.getPublishDate());
                VideoVo v = new VideoVo(p.getId(), p.getTitle(), p.getCover(), p.getPath(), time, p.getBiref(), 0, p.getName(), p.getCategoryId(), p.getUserId(), p.getUsername());
                videoVos.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videoVos;
    }

    //测试展示动态接口
    @Override
    public List<VideoVo> queryByUserMoving(String userId) {
        List<VideoVo> list = new ArrayList<>();
        try {
            //查询连接的数据
            List<VideoPo> videoPos = videoDao.queryByUserMoving(userId);
            //转化日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //将po对象的值赋值给vo对象
            for (VideoPo p : videoPos) {
                String time = sdf.format(p.getPublishDate());
                VideoVo v = new VideoVo(p.getId(), p.getTitle(), p.getCover(), p.getPath(), time, p.getBiref(), 0, p.getName(), p.getCategoryId(), p.getUserId(), p.getHeadImg(), p.getUsername(), null, null, null);
                list.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

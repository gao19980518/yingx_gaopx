package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "yx_video")
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    @Id
    private String id;
    private String title;
    private String biref;
    private String path;
    private String cover;
    @Column(name = "publish_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishDate;
    @Column(name = "category_id")
    private String categoryId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "group_id")
    private String groupId;
}

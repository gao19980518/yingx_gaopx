package com.baizhi.service;

import com.baizhi.entity.Feedback;

import java.util.HashMap;

public interface FeedbackService {
    public HashMap<String, Object> queryPageFeedback(Integer page, Integer rows);

    public void add(Feedback feedback);

    public void del(Feedback feedback);
}

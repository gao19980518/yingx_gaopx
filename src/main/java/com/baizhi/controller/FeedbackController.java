package com.baizhi.controller;

import com.baizhi.entity.Feedback;
import com.baizhi.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping("feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    //分页展示反馈
    @RequestMapping("queryPageFeedback")
    @ResponseBody
    public HashMap<String, Object> queryPageFeedback(Integer page, Integer rows) {
        HashMap<String, Object> map = feedbackService.queryPageFeedback(page, rows);
        return map;
    }

    @RequestMapping("edit")
    @ResponseBody
    public HashMap<String, Object> edit(Feedback feedback, String oper) {
        HashMap<String, Object> map = new HashMap<>();
        if ("add".equals(oper)) {
            feedbackService.add(feedback);
        }
        if ("edit".equals(oper)) {

        }
        if ("del".equals(oper)) {
            System.out.println("删除反馈的controller" + feedback);
            feedbackService.del(feedback);
        }
        return map;
    }
}

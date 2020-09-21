package com.baizhi.controller;

import com.baizhi.util.ImageCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @ClassName CodeController
 * @Author wyy
 * @Date 2020/8/24 22:45
 * @Description TOOO
 */
@Controller
@RequestMapping("/code")
public class CodeController {
    @RequestMapping("/code")
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取验证码的文本
        String code = ImageCodeUtil.getSecurityCode();
        //2.保存验证码
        request.getSession().setAttribute("code", code);
        //3.绘制图片
        BufferedImage image = ImageCodeUtil.createImage(code);
        //4.发送验证码的图片
        try {
            ImageIO.write(image, "png", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

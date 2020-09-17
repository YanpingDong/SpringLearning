package com.dyp.dashboard.module.sys.ctrl;

import com.dyp.dashboard.util.RandomUtils;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Map;

@Controller
public class LoginController {
    private long verifyTTL = 60;//验证码过期时间60秒

    @Resource
    DefaultKaptcha defaultKaptcha;
    /**
     * 2、生成验证码

     * @throws Exception
     */
    @RequestMapping(value = "/getVerifyCode", method = RequestMethod.GET)
    public void defaultKaptcha(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        byte[] bytesCaptchaImg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // 生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            request.getSession().setAttribute("verifyCode", createText);
            request.getSession().setAttribute("verifyCodeTTL", System.currentTimeMillis());
            // 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage bufferedImage = defaultKaptcha.createImage(createText);
            ImageIO.write(bufferedImage, "jpg", jpegOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        bytesCaptchaImg = jpegOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(bytesCaptchaImg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
    private String create16String()
    {
        return RandomUtils.generateString(16);
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public String toLogin(Map<String, Object> map, HttpServletRequest request) {
        System.out.println("longin get");
//        loginService.logout();
        String key = create16String();

        map.put("key",key);
        return "/sys/login";
    }
}

package com.dyp.dashboard.module.sys.ctrl;

import com.dyp.dashboard.util.RandomUtils;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLogin(Map<String, Object> map, HttpServletRequest request) {
        System.out.println("longin get");
//        loginService.logout();
        String key = create16String();

        map.put("key",key);
        return "/sys/login";
    }
    private class menue{
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }
    @RequestMapping({"/", "/index"})
    public String index(HttpServletRequest request, Model model) {
       // List<SysPermission> sysPermissions = userRepository.findUserRolePermissionByUserName("admin");

        String userName = request.getParameter("userName");

        Map<String, List<menue>> menues = new HashMap<>();
        List<menue> subMenues = new ArrayList<>();
        menue ulistMenue = new menue();
        ulistMenue.setName("用户管理");
        ulistMenue.setUrl("/user/ulist");

        menue rlistMenue = new menue();
        rlistMenue.setName("角色管理");
        rlistMenue.setUrl("/user/rlist");

        menue mlistMenue = new menue();
        mlistMenue.setName("菜单管理");
        mlistMenue.setUrl("/menue/mlist");

        menue pwdChgMenue = new menue();
        pwdChgMenue.setName("修改密码");
        pwdChgMenue.setUrl("/user/toChangePassword");
        subMenues.add(ulistMenue);
        subMenues.add(rlistMenue);
        subMenues.add(pwdChgMenue);
        subMenues.add(mlistMenue);

        List<menue> subMenues1 = new ArrayList<>();
        menue loglistMenue = new menue();
        loglistMenue.setName("日志查看");
        loglistMenue.setUrl("/log/list");
        subMenues1.add(loglistMenue);

        //editor

        List<menue> subMenues2 = new ArrayList<>();
        menue editorlistMenue = new menue();
        editorlistMenue.setName("新闻编辑");
        editorlistMenue.setUrl("/cms/editor");
        subMenues2.add(editorlistMenue);


        if("admin".equalsIgnoreCase("admin"))
        {
            menues.put("系统管理",subMenues);
            menues.put("日志管理",subMenues1);
            menues.put("新闻管理",subMenues2);
        }
        else
        {
            menues.put("日志管理",subMenues1);
            menues.put("新闻管理",subMenues2);
        }

        model.addAttribute("menues", menues);
        return "/sys/index";
    }

    @RequestMapping(value = "/home/prompt", method = RequestMethod.GET)
    public String toHome(Map<String, Object> map, HttpServletRequest request) {
        System.out.println("longin get");
//        loginService.logout();
        String key = create16String();

        map.put("key",key);
        return "/sys/homeList";
    }

    private String create16String()
    {
        return RandomUtils.generateString(16);
    }
}

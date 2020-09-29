package com.dyp.dashboard.module.sys.ctrl;

import com.dyp.dashboard.module.sys.entity.SysPermission;
import com.dyp.dashboard.module.sys.service.UserService;
import com.dyp.dashboard.util.RandomUtils;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    UserService userService;

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
        List<SysPermission> sysPermissions = userService.findUserRolePermissionByUserName("admin");
        String userName = request.getParameter("userName");

        Map<String, List<menue>> menues = new HashMap<>();
        Map<Integer, String> parentMenueInfo = new HashMap<>();
        if(sysPermissions != null)
        {
            for(SysPermission sysPermission : sysPermissions)
            {
                if(sysPermission.getLevel() == 1)
                {
                    String permissionName = sysPermission.getPermissionName();
                    int parentId = sysPermission.getPermissionId();
                    parentMenueInfo.put(parentId, permissionName);
                    if(!menues.containsKey(permissionName))
                    {
                        List<menue> submenue = new ArrayList<>();
                        menues.put(permissionName,submenue);
                    }
                }
                else if(sysPermission.getLevel() != 3)
                {
                    String parentPermissionName = parentMenueInfo.get(sysPermission.getParentId());
                    List<menue> submenues = menues.get(parentPermissionName);
                    menue subMenue = new menue();
                    subMenue.setName(sysPermission.getPermissionName());
                    subMenue.setUrl(sysPermission.getUrl());
                    submenues.add(subMenue);
                }

            }
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

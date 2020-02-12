package com.dyp.demo.thymeleaf.ctr;

import com.dyp.demo.thymeleaf.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class IndexCtr {

    @GetMapping(value = "/test")
    public String test(Model model)
    {
        model.addAttribute("msg","tset info");
        return "hello";
    }

    @GetMapping("test2")
    public String test2(Model model){
        User user = new User();
        user.setRole("admin");
        user.setAge(19);
        user.setName("jim");
        user.setSex(true);
        user.setFriend(new User("tom", 30));
        model.addAttribute("user", user);

        List<User> users = new ArrayList<>();

        User user1 = new User();
        user1.setAge(20);
        user1.setName("tom");
        user1.setSex(true);
        users.add(user);
        users.add(user1);

        model.addAttribute("users", users);



        return "/sys/user";
    }

    @GetMapping("test3")
    public String test3(Model model){
        model.addAttribute("today", new Date());
        return "hello3";
    }
}

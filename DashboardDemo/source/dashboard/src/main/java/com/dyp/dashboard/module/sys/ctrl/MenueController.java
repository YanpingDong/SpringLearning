package com.dyp.dashboard.module.sys.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menue")
public class MenueController {
    @RequestMapping(value="/mlist")
    public String listMenue()
    {
        return "/sys/menueList";
    }
}

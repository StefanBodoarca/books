package com.ro.controllers;

import com.ro.services.LoggedUserManagementService;
import com.ro.services.LoginCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    private final LoggedUserManagementService loggedUserManagementService;
    private final LoginCountService loginCountService;

    @Autowired
    public MainController(LoggedUserManagementService loggedUserManagementService, LoginCountService loginCountService) {
        this.loggedUserManagementService = loggedUserManagementService;
        this.loginCountService = loginCountService;
    }

    @GetMapping("/main")
    public String home(@RequestParam(required = false) String logout, Model model){
        String username = loggedUserManagementService.getUsername();


        if (logout != null) {
            loggedUserManagementService.setUsername(null);
        }

        if (username == null) {
            return "redirect:/";
        }

        int count = loginCountService.getCount();
        model.addAttribute("username", username);
        model.addAttribute("loginCount", count);
        return "main.html";
    }
}

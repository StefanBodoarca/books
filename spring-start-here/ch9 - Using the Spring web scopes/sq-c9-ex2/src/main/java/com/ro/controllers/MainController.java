package com.ro.controllers;

import com.ro.services.LoggedUserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    private final LoggedUserManagementService loggedUserManagementService;

    @Autowired
    public MainController(LoggedUserManagementService loggedUserManagementService) {
        this.loggedUserManagementService = loggedUserManagementService;
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

        model.addAttribute("username", username);
        return "main.html";
    }
}

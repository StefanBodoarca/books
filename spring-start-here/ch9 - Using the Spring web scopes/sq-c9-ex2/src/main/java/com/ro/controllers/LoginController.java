package com.ro.controllers;

import com.ro.processor.LoginProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private LoginProcessor loginProcessor;

    public LoginController(LoginProcessor loginProcessor) {
        this.loginProcessor = loginProcessor;
    }

    @GetMapping("/")
    public String loginGet() {
        return "login.html";
    }

    @PostMapping("/")
    public String loginPost(
            @RequestParam String username,
            @RequestParam String password,
            Model model
    ) {
        loginProcessor.setPassword(password);
        loginProcessor.setUsername(username);

        if (loginProcessor.login()) {
            return "redirect:/main";
        } else {
            model.addAttribute("message", "Login failed!");
            return "login.html";
        }
    }
}

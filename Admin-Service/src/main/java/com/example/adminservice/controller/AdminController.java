package com.example.adminservice.controller;

import com.example.adminservice.repository.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin/login")
    public String login() {
        return "AdminLogin";
    }

    @PostMapping("/admin/adminLogin")
    public String findByUsername(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("password") String password) {
        String ans = adminService.findByUsername(username, password);

        if (ans == "true") {
            String redirectUrl = request.getScheme() + "://localhost:8080/grpc/welcome/" + username;
            return "redirect:" + redirectUrl;
        } else {
            String redirectUrl = request.getScheme() + "://localhost:8080/admin/login";
            return "redirect:" + redirectUrl;
        }
    }
}

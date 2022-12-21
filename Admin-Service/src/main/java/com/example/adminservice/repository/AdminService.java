package com.example.adminservice.repository;

import com.example.adminservice.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private AdminRepository adminRepository;


    @Autowired
    public AdminService(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

    public String findByUsername(String username, String password)
    {
        Admin t = new Admin();
        String u = "";
        String p = "";
        u = adminRepository.findByUsername(username).getUsername();
        p = adminRepository.findByUsername(username).getPassword();
        t.setUsername(username);
        t.setPassword(password);
        if (username.equals(u) && password.equals(p)){
            return "true";
//            return "adminMenu/" + u;
        }else {
            return "false";
//            return "login";
        }
    }
}

package com.hashsnap.login.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = {"/", "/main"})
    public String main(){

        return "main";
    }

    @GetMapping("/admin/admin")
    public String adminPage(){
        return "admin/admin";
    }

    @GetMapping("/user/user")
    public String userPage(){
        return "user/user";
    }

}

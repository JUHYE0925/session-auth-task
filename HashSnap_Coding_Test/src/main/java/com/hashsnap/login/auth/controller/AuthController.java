package com.hashsnap.login.auth.controller;

import com.hashsnap.login.auth.model.service.AuthService;
import com.hashsnap.login.user.model.repository.UserRepository;
import com.hashsnap.login.user.model.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    public AuthController(){}

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    /* comment. 로그아웃 처리 */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();  // 세션 무효화
        return "redirect:/main";
    }


    @GetMapping("/fail")
    public ModelAndView loginFail(ModelAndView mv, @RequestParam String message){

        mv.addObject("message", message);
        mv.setViewName("auth/fail");

        return mv;
    }
}
package com.hashsnap.login.user.model.controller;

import com.hashsnap.login.user.model.dto.SignupDTO;
import com.hashsnap.login.user.model.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signupPage() {

        return "user/signup";
    }

    @PostMapping("/signup")
    public ModelAndView registNewUser(ModelAndView mv, @ModelAttribute SignupDTO signupDTO){

        String message = "";

        try{
            int result = userService.registNewUser(signupDTO);

            if(result > 0){
                message = "회원 가입이 정상적으로 완료되었습니다.";
                mv.setViewName("auth/success");
            } else {
                message = "회원 가입에 실패하였습니다.";
                mv.setViewName("user/signup");
            }

            mv.addObject("message", message);

            return mv;
        } catch (Exception e){
            message = "회원가입 도중 오류가 발생했습니다.";
            mv.setViewName("auth/fail");

            mv.addObject("message", message);
            return mv;
        }
    }

    @PostMapping("/check-duplicate")
    public ResponseEntity<Map<String, Boolean>> checkDuplicateNickname(@RequestBody Map<String, String> request){
        Map<String, Boolean> response = new HashMap<>();

        if(request.get("nickname") != null){
            String nickname = request.get("nickname");
            boolean isDuplicate = userService.isNicknameDuplicate(nickname);
            response.put("isDuplicate", isDuplicate);
        } else if (request.get("userPhone") != null) {
            String userPhone = request.get("userPhone");
            boolean isDuplicate = userService.isUserPhoneDuplicate(userPhone);
            response.put("isDuplicate", isDuplicate);
        } else if (request.get("userEmail") != null){
            String userEmail = request.get("userEmail");
            boolean isDuplicate = userService.isUserEmailDuplicate(userEmail);
            response.put("isDuplicate", isDuplicate);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-page")
    public String myPage(){
        return "user/my-page";
    }
}

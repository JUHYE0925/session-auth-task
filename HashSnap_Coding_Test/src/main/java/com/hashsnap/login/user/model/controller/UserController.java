package com.hashsnap.login.user.model.controller;

import com.hashsnap.login.user.model.dto.EmailVerificationResult;
import com.hashsnap.login.user.model.dto.SignupDTO;
import com.hashsnap.login.user.model.dto.SingleResponseDTO;
import com.hashsnap.login.user.model.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
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

    @GetMapping("/reset-password")
    public String resetPwdPage(){
        return "user/reset-password";
    }

    // sendMessage(): 이메일 전송 API.
    // 이메일을 파라미터로 받아 해당 UserService.sendCodeToEmail() 메서드로 넘겨준다.
    @PostMapping("/email/vefirication-requests")
    public ResponseEntity<?> snedMessage(@RequestParam("email") String email){
        userService.sendCodeToEmail(email);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // verificationEmail(): 이메일 인증을 진행하는 API.
    // 이메일과 사용자가 작성한 인증 코드를 파라미터로 받아 UserService.verifiedCode() 메서드로 넘긴다.
    // 인증에 성공하면 ture를 실패하면 false를 반환한다.
    @GetMapping("/email/verification")
    public ResponseEntity<?> verificationEmail(@RequestParam("email") String email, @RequestParam("code") String authCode){
        EmailVerificationResult response = userService.verifiedCode(email, authCode);

        return new ResponseEntity<>(new SingleResponseDTO<>(response), HttpStatus.OK);
    }
}

package com.hashsnap.login.config.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.servlet.View;

import java.io.IOException;
import java.net.URLEncoder;

/* 사용자가 로그인  실패 시 실패 요청을 커스텀하기 위한 핸들러 */
@Configuration
public class AuthFailHandler extends SimpleUrlAuthenticationFailureHandler {

//    private final View error;
//
//    public AuthFailHandler(View error){
//        this.error = error;
//    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException{

        String errorMessage = "";
        if(exception instanceof BadCredentialsException){
            // 잘못된 정보 입력할 경우
            errorMessage = "전화번호가 존재하지 않거나 비밀번호가 일치하지 않습니다.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            // 서버에서 사용자 정보를 검증하는 과정에서 에러 발생하는 경우
            errorMessage = "서버에서 오류가 발생했습니다. 관리자에게 문의하여 주세요.";
        } else if (exception instanceof UsernameNotFoundException) {
            // DB에 사용자 정보가 없는 경우
            errorMessage = "존재하지 않는 전화번호 입니다. 다시 입력해주세요.";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            // 보안 컨텍스트에 인증 객체가 존재하지 않거나 인증 정보가 없는 상태에서 보안처리된 리소스에 접근할 경우
            errorMessage = "인증 요청이 거부되었습니다.";
        }else {
            errorMessage = "알 수 없는 오류로 인해 로그인 요청을 처리할 수 없습니다. 관리자에게 문의하여 주세요.";
        }

        // 응답 메시지와 페이지 경로를 설정할 수 있도록 재정의
        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");

        setDefaultFailureUrl("/auth/fail?message=" + errorMessage);

        super.onAuthenticationFailure(request, response, exception);
    }

}

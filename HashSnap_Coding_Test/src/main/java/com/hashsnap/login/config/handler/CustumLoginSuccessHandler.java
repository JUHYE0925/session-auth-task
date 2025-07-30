package com.hashsnap.login.config.handler;

import com.hashsnap.login.auth.model.AuthDetail;
import com.hashsnap.login.user.model.dto.LoginUserDTO;
import com.hashsnap.login.user.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustumLoginSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        // 사용자 권한 확인
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String redirectUrl = "/";

        if(authorities.stream().anyMatch(user -> user.getAuthority().equals("ROLE_USER"))){
            redirectUrl = "/user/user";
        } else if(authorities.stream().anyMatch(user -> user.getAuthority().equals("ROLE_ADMIN"))){
            redirectUrl = "/admin/admin";
        }

        AuthDetail authDetail = (AuthDetail) authentication.getPrincipal();
        LoginUserDTO loginUser = authDetail.getLoginUserDTO();

        HttpSession session = request.getSession();
        session.setAttribute("loginUser", loginUser);

        response.sendRedirect(redirectUrl);
    }

}

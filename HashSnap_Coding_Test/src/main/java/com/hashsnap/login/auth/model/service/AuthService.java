package com.hashsnap.login.auth.model.service;

import com.hashsnap.login.auth.model.AuthDetail;
import com.hashsnap.login.user.model.dto.LoginUserDTO;
import com.hashsnap.login.user.model.entity.User;
import com.hashsnap.login.user.model.repository.UserRepository;
import com.hashsnap.login.user.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/*
* comment.
*  security에서 사용자의 ID를 인정하기 위한 인터페이스이다.
*  LoadUserByUserName을 필수로 구현해야하며
*  로그인 인증 시 해당 메소드에 전달된 사용자의 ID를 매개변수로 DB에서 조회한다.
*/

@Service
public class AuthService implements UserDetailsService {

    private final UserService userService;
    private final UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserService userService, UserRepository userRepository){
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public boolean authenticate(String userPhone, String password){
        User user = userRepository.findByUserPhone(userPhone);

        if(user != null && passwordEncoder.matches(password, user.getPassword())){
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String userPhone) throws UsernameNotFoundException {

        LoginUserDTO loginUserDTO = userService.findByUserPhone(userPhone);

        if(Objects.isNull(loginUserDTO)){
            throw new UsernameNotFoundException("해당하는 회원 정보가 존재하지 않습니다.");
        }

        return new AuthDetail(loginUserDTO);
    }
}

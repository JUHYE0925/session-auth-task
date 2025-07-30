package com.hashsnap.login.user.model.service;

import com.hashsnap.login.common.UserRole;
import com.hashsnap.login.user.model.dto.LoginUserDTO;
import com.hashsnap.login.user.model.dto.SignupDTO;
import com.hashsnap.login.user.model.entity.User;
import com.hashsnap.login.user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public int registNewUser(SignupDTO signupDTO) {

        String encodedPwd = passwordEncoder.encode(signupDTO.getPassword());
        signupDTO.setPassword(encodedPwd);
        signupDTO.setUserRole(UserRole.USER);

        User newUser = modelMapper.map(signupDTO, User.class);

        userRepository.save(newUser);
        int userCode = newUser.getUserCode();

        return userCode;
    }

    public LoginUserDTO findByUserPhone(String userPhone) {

        User userInfo = userRepository.findByUserPhone(userPhone);

        System.out.println("😄😄😄userInfo = " + userInfo);

        return modelMapper.map(userInfo, LoginUserDTO.class);
    }

    public boolean isNicknameDuplicate(String nickname) {

        User userInfo = userRepository.findByNickname(nickname);

        return userInfo != null && userInfo.getNickname() != null;
    }

    public boolean isUserPhoneDuplicate(String userPhone) {

        User userInfo = userRepository.findByUserPhone(userPhone);

        return userInfo != null && userInfo.getUserPhone() != null;
    }

    public boolean isUserEmailDuplicate(String userEmail) {

        User userInfo = userRepository.findUserEmail(userEmail);

        return userInfo != null && userInfo.getUserEmail() != null;
    }
}

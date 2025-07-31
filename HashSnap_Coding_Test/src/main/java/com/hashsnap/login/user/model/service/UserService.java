package com.hashsnap.login.user.model.service;

import com.hashsnap.login.common.UserRole;
import com.hashsnap.login.common.service.RedisService;
import com.hashsnap.login.mail.service.MailService;
import com.hashsnap.login.user.model.dto.EmailVerificationResult;
import com.hashsnap.login.user.model.dto.LoginUserDTO;
import com.hashsnap.login.user.model.dto.SignupDTO;
import com.hashsnap.login.user.model.entity.User;
import com.hashsnap.login.user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final String AUTH_CODE_PREFIX = "AuthCode ";
    private final MailService mailService;
    private final RedisService redisService;

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

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

    public void sendCodeToEmail(String email) {
        this.checkDuplicatedEmail(email);
        String title = "HashSnap 이메일 인증 번호";
        String authCode = this.createCode();
        mailService.sendEmail(email, title, authCode);

        // 이메일 인증 요청 시 인증 번호 Redis에 저장 ( key = "AuthCode " + Email / value = AuthCode )
        redisService.setValues(AUTH_CODE_PREFIX + email, authCode, Duration.ofMillis(this.authCodeExpirationMillis));
    }

    private void checkDuplicatedEmail(String email){
        User user = userRepository.findUserEmail(email);

        if(user != null){
            log.debug("MemberServiceImpl.checkDuplicatedEmail exception occur email : {} ", email);
            throw new RuntimeException("이미 존재하는 이메일");
        }
    }

    private String createCode(){
        int length = 6;

        try{
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < length; i++){
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        }catch (NoSuchAlgorithmException e){
            log.debug("MemberService.createCode() exception occur");
            throw new RuntimeException("특정 암호화를 찾을 수 없음.");
        }
    }

    public EmailVerificationResult verifiedCode(String email, String authCode){
        this.checkDuplicatedEmail(email);
        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + email);
        boolean authResult = redisService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode);

        return EmailVerificationResult.of(authResult);
    }
}

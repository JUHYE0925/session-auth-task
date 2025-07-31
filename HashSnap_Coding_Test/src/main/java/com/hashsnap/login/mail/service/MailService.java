package com.hashsnap.login.mail.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* 이메일 발송하는 서비스 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    // 이메일을 발송하는 메소드 파라미터로 이메일 주소, 제목, 내용을 입력받아 이메일 createEmailForm() 메소드로 넘겨준다.
    // createForm() 메소드가 SimpleMailMessage 객체를 생성하여 반환하면 주입 받은 emailSender.send() 메소드에 담아 메일을 발송한다.
    public void sendEmail(String toEmail, String title, String text){

        SimpleMailMessage emailform = createEmailForm(toEmail, title, text);
        try{
            mailSender.send(emailform);
        } catch(RuntimeException e){
            log.debug("MailService.sendEmail exception occur toEmail : {}, " +
                    "title: {}, text: {}", toEmail, title, text);
            throw new RuntimeException("이메일 발송 실패");
        }
    }

    // 발신할 이메일 데이터 세팅
    // 발송할 이메일 데이터를 설정하는 메소드.
    private SimpleMailMessage createEmailForm(String toEmail, String title, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(text);

        return message;
    }

}

package com.hashsnap.login.user.model.entity;

import com.hashsnap.login.common.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userCode;
    private String userName;
    private String nickname;
    private String password;
    private String userPhone;
    private String userEmail;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    // 비밀번호 수정 메소드 (암호화는 서비스에서 처리)
    public void modifyPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }
}

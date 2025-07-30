package com.hashsnap.login.user.model.entity;

import com.hashsnap.login.common.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    public void modifyPassword(String password){
        this.password = password;
    }
}

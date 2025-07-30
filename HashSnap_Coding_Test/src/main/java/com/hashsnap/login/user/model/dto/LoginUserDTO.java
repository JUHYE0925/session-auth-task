package com.hashsnap.login.user.model.dto;

import com.hashsnap.login.common.UserRole;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginUserDTO implements Serializable {

    private Integer userCode;
    private String userPhone;
    private String nickname;
    private String userEmail;
    private String userName;
    private String password;
    private UserRole userRole;

    public String getRole(){
        return this.userRole.getRole();
    }
}

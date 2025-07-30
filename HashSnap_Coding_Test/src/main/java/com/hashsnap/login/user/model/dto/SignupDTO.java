package com.hashsnap.login.user.model.dto;

import com.hashsnap.login.common.UserRole;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SignupDTO {

    private Integer userCode;
    private String userName;
    private String nickname;
    private String password;
    private String userPhone;
    private String userEmail;
    private UserRole userRole;

}

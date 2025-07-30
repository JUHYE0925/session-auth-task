package com.hashsnap.login.auth.model;

import com.hashsnap.login.user.model.dto.LoginUserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/*
* comment.
*  UserDetailService에서 사용자의 전화번호를 기준으로 조회한 결과가 반환되는 클래스를
*  사용자 타입으로 해당 객체에 조회된 사용자의 정보가 담겨서 session에 저장할 수 있다.
*/
public class AuthDetail implements UserDetails {

    private LoginUserDTO loginUserDTO;

    public AuthDetail(){}

    public AuthDetail(LoginUserDTO loginUserDTO){
        this.loginUserDTO = loginUserDTO;
    }

    public LoginUserDTO getLoginUserDTO(){
        return loginUserDTO;
    }

    public void setLoginUserDTO(LoginUserDTO loginUserDTO){
        this.loginUserDTO = loginUserDTO;
    }

    /* comment. 권한 정보를 반환하는 메소드*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        /*
        * Spring Security는 "ROLE_"로 시작하는 권한 이름을 기본 전제로 사용합니다.
        * toString()을 어떻게 바꿔도 "ROLE_USER" 형식이 아니면 인식 오류나 예외가 생길 수 있습니다.
        * 따라서 GrantedAuthority에 넣는 문자열은 항상 명시적으로 가공하는 게 좋습니다.
        */
       authorities.add(() -> "ROLE_" + loginUserDTO.getRole());

        return authorities;
    }

    /* comment. 사용자의 비밀번호를 반환하는 메소드 */
    @Override
    public String getPassword(){
        return loginUserDTO.getPassword();
    }

    /* comment. 사용자의 닉네임을 반환하는 메소드 */
    @Override
    public String getUsername(){
        return loginUserDTO.getUserName();
    }

    /* comment. 사용자의 전화번호를 반환하는 메소드 */
    public String getUserPhone(){
        return loginUserDTO.getUserPhone();
    }
    
    /* comment. 계정 만료 여부를 표현하는 메소드로, false이면 해당 계정을 사용할 수 없다. */
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }
    
    /* comment. 계정 잠겨있는지 확인하는 메소드로, false이면 해당 계정 사용할 수 없다. */
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }
    
    /* comment. 탈퇴 계정 여부 표현하는 메소드로, false이면 해당 계정 사용할 수 없다. */
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }
    
    /* comment. 계정 비활성화 여부를 나타내는 메소드 */
    @Override
    public boolean isEnabled(){
        return true;
    }
}

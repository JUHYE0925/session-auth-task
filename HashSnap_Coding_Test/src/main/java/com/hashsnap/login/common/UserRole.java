package com.hashsnap.login.common;

import lombok.ToString;

@ToString
public enum UserRole {

    USER("USER"),
    ADMIN("ADMIN");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }

}

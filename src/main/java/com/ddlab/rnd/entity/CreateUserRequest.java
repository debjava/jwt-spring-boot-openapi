package com.ddlab.rnd.entity;

import java.util.Set;

import com.ddlab.rnd.role.Role;

import lombok.Data;

@Data
public class CreateUserRequest {

    private String username;
    private String password;
    private Set<Role> authorities;
}
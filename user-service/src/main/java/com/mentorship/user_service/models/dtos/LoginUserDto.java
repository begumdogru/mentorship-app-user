package com.mentorship.user_service.models.dtos;

import lombok.Data;

@Data
public class LoginUserDto {
    private String email;
    private String password;
}
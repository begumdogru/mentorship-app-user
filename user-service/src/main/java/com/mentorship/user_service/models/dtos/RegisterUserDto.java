package com.mentorship.user_service.models.dtos;

import lombok.Data;

@Data
public class RegisterUserDto{
    public String username;
    public String fullName;    
    public String password;
    public String email;
    //TODO:: add other fields if necessary mesela sektor, deneyim yili vs
}
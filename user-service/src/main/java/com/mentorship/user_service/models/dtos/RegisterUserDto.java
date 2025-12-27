package com.mentorship.user_service.models.dtos;

import lombok.Data;

@Data
public class RegisterUserDto{
    public String username;
    public String fullName;    
    public String password;
    public String email;
    public Integer universityId;
    public Integer role;
    public Integer sector;
    public String biography;
    private Integer experience;
    public Double rating;
    //TODO:: add other fields if necessary mesela sektor, deneyim yili vs
}
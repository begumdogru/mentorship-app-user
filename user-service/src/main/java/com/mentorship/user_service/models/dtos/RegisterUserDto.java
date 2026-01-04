package com.mentorship.user_service.models.dtos;

import com.mentorship.user_service.models.entity.Role;
import lombok.Data;

@Data
public class RegisterUserDto{
    public String username;
    public String fullName;    
    public String password;
    public String email;
    public Integer universityId;
    public Role role;
    public Integer sector;
    public String biography;
    private Integer experience;
    public Double rating;
}
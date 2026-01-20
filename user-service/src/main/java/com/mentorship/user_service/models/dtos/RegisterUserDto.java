package com.mentorship.user_service.models.dtos;

import com.mentorship.user_service.models.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto{
    private String username;
    private String fullName;    
    private String password;
    private String email;
    private Integer universityId;
    private Role role;
    private Integer sector;
    private String biography;
    private Integer experience;
    private Double rating;
}
package com.mentorship.user_service.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto{
    public Integer user_id;
    public String username;
    public String fullName;    
    public String email;
}
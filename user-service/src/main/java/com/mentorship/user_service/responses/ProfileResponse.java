package com.mentorship.user_service.responses;

import com.mentorship.user_service.models.dtos.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponse {
    private String token;
    private UserDto user;
    private long expiresIn;
}

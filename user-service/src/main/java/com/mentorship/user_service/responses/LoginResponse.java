package com.mentorship.user_service.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String username;
    private long expiresIn;
    private String token;

}

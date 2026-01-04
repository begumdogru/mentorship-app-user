package com.mentorship.user_service.responses;

import com.mentorship.user_service.models.entity.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSubsResponse {
    private Integer userId;
    private Role role;
}

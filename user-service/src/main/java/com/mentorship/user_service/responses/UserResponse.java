package com.mentorship.user_service.responses;

import com.mentorship.user_service.models.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Integer userId;
    private String username;
    private String fullName;
    private String email;
    private Integer universityId;
    private String universityName;
    private Role role;
    private Integer sectorId;
    private String sectorName;
    private String biography;
    private Integer experience;
    private Double rating;
}

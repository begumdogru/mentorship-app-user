package com.mentorship.user_service.models.entity;

public enum Role {
    MENTOR(1),
    MENTEE(2);

    private Integer role;

    Role(Integer role) {
        this.role = role;
    }
    public Integer getRole() {
        return role;
    }
}

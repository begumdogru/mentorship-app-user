package com.mentorship.user_service.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "university")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class University {

    @Id
    @Column(name = "university_id", nullable = false)
    private Integer universityId;

    @Column(name = "university_name", nullable = false, length = 200)
    private String universityName;
}

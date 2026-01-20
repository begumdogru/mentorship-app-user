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
@Table(name = "sector")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sector {

    @Id
    @Column(name = "sector_id", nullable = false)
    private Integer sectorId;

    @Column(name = "sector_name", nullable = false, length = 200)
    private String sectorName;
}

package com.sirius.spurt.store.repository.database.entity;

import com.sirius.spurt.common.meta.JobGroup;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER")
public class UserEntity extends BaseEntity {
    @Id private String userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JobGroup jobGroup;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Boolean hasPined;
}

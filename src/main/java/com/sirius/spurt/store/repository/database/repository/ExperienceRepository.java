package com.sirius.spurt.store.repository.database.repository;

import com.sirius.spurt.store.repository.database.entity.ExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<ExperienceEntity, Long> {
    ExperienceEntity findByExperienceIdAndUserEntityUserId(
            final Long experienceId, final String userId);
}

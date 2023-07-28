package com.sirius.spurt.store.repository.database.repository;

import com.sirius.spurt.store.repository.database.entity.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleRepository extends JpaRepository<SampleEntity, String> {
    SampleEntity findBySampleId(Long smapleId);
}

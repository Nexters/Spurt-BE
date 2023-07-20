package com.sirius.spurt.store.repository.database.repository;

import com.sirius.spurt.store.repository.database.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, String> {}

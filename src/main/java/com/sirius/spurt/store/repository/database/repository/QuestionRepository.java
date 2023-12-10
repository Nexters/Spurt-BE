package com.sirius.spurt.store.repository.database.repository;

import com.sirius.spurt.store.repository.database.custom.QuestionRepositoryCustom;
import com.sirius.spurt.store.repository.database.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository
        extends JpaRepository<QuestionEntity, String>, QuestionRepositoryCustom {
    QuestionEntity findByQuestionId(final Long id);

    QuestionEntity findByQuestionIdAndUserId(final Long id, final String userId);

    QuestionEntity findTopByUserIdOrderByCreateTimestampDesc(String userId);
}

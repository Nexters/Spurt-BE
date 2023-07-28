package com.sirius.spurt.store.repository.database.custom;

import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.store.repository.database.entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface QuestionRepositoryCustom {

    Page<QuestionEntity> searchQuestion(
            final String subject,
            final JobGroup jobGroup,
            final Category category,
            final Boolean pinIndicator,
            final Boolean myQuestionIndicator,
            final String userId,
            final PageRequest pageRequest);
}

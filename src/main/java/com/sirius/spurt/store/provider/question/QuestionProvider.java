package com.sirius.spurt.store.provider.question;

import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.store.provider.question.vo.QuestionVo;
import com.sirius.spurt.store.provider.question.vo.QuestionVoList;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface QuestionProvider {

    void putPinQuestion(final String questionId, final String userId, final Boolean pinIndicator);

    QuestionVoList randomQuestion(
            final JobGroup jobGroup, final String userId, final Integer count, final Category category);

    void deleteQuestion(final String userId, final Long questionId);

    void saveQuestion(
            final String subject,
            final String mainText,
            final List<String> keyWordList,
            final List<Category> categoryList,
            final Long experienceId,
            final String userId);

    void putQuestion(
            final String questionId,
            final String subject,
            final String mainText,
            final List<String> keyWordList,
            final List<Category> categoryList,
            final String userId);

    QuestionVo getQuestion(final Long questionId);

    QuestionVoList searchQuestion(
            final String subject,
            final JobGroup jobGroup,
            final Category category,
            final Boolean pinIndicator,
            final Boolean myQuestionIndicator,
            final String userId,
            final PageRequest pageRequest);
}

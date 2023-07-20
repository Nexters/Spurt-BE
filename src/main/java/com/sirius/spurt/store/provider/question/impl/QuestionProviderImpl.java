package com.sirius.spurt.store.provider.question.impl;

import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.store.provider.question.QuestionProvider;
import com.sirius.spurt.store.repository.database.entity.CategoryEntity;
import com.sirius.spurt.store.repository.database.entity.KeyWordEntity;
import com.sirius.spurt.store.repository.database.entity.QuestionEntity;
import com.sirius.spurt.store.repository.database.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionProviderImpl implements QuestionProvider {
    private final QuestionRepository questionRepository;

    @Override
    @Transactional
    public void saveQuestion(
            final String subject,
            final String mainText,
            final List<String> keyWordList,
            final List<Category> categoryList,
            final JobGroup jobGroup,
            final String userId) {

        List<CategoryEntity> categoryEntityList =
                categoryList.stream()
                        .map(category -> CategoryEntity.builder().category(category).build())
                        .toList();

        List<KeyWordEntity> keyWordEntityList =
                keyWordList.stream()
                        .map(keyWord -> KeyWordEntity.builder().keyWord(keyWord).build())
                        .toList();

        QuestionEntity questionEntity =
                QuestionEntity.builder()
                        .categoryEntityList(categoryEntityList)
                        .KeyWordEntityList(keyWordEntityList)
                        .subject(subject)
                        .mainText(mainText)
                        .jobGroup(jobGroup)
                        .userId(userId)
                        .pinIndicator(false)
                        .build();

        questionRepository.save(questionEntity);
    }
}

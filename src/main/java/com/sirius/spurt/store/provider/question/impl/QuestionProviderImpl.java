package com.sirius.spurt.store.provider.question.impl;

import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.store.provider.question.QuestionProvider;
import com.sirius.spurt.store.provider.question.vo.QuestionVo;
import com.sirius.spurt.store.provider.question.vo.QuestionVoList;
import com.sirius.spurt.store.repository.database.entity.CategoryEntity;
import com.sirius.spurt.store.repository.database.entity.KeyWordEntity;
import com.sirius.spurt.store.repository.database.entity.QuestionEntity;
import com.sirius.spurt.store.repository.database.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionProviderImpl implements QuestionProvider {
    private final QuestionRepository questionRepository;

    @Override
    public QuestionVoList searchQuestion(
            final String subject,
            final JobGroup jobGroup,
            final Category category,
            final Boolean pinIndicator,
            final Boolean myQuestionIndicator,
            final String userId,
            final PageRequest pageRequest) {
        Page<QuestionEntity> pages =
                questionRepository.searchQuestion(
                        subject, jobGroup, category, pinIndicator, myQuestionIndicator, userId, pageRequest);
        return QuestionVoList.builder()
                .questions(QuestionProviderImplMapper.INSTANCE.toQuestionVos(pages.getContent()))
                .pageable(pages.getPageable())
                .totalPage(pages.getTotalPages())
                .totalCount(pages.getTotalElements())
                .build();
    }

    @Override
    public QuestionVo getQuestion(final Long questionId) {
        return QuestionProviderImplMapper.INSTANCE.toQuestionVo(
                questionRepository.findByQuestionId(questionId));
    }

    @Override
    @Transactional
    public void putQuestion(
            final String questionId,
            final String subject,
            final String mainText,
            final List<String> keyWordList,
            final List<Category> categoryList,
            final JobGroup jobGroup,
            final String userId) {

        QuestionEntity previous = questionRepository.findByQuestionId(Long.valueOf(questionId));

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
                        .questionId(Long.valueOf(questionId))
                        .categoryEntityList(categoryEntityList)
                        .KeyWordEntityList(keyWordEntityList)
                        .subject(subject)
                        .mainText(mainText)
                        .jobGroup(jobGroup)
                        .userId(userId)
                        .pinIndicator(previous.getPinIndicator())
                        .build();

        questionRepository.save(questionEntity);
    }

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

    @Mapper
    public interface QuestionProviderImplMapper {
        QuestionProviderImpl.QuestionProviderImplMapper INSTANCE =
                Mappers.getMapper(QuestionProviderImpl.QuestionProviderImplMapper.class);

        @Mapping(source = "categoryEntityList", target = "categoryList")
        @Mapping(source = "keyWordEntityList", target = "keyWordList")
        QuestionVo toQuestionVo(QuestionEntity entity);

        List<QuestionVo> toQuestionVos(List<QuestionEntity> entity);
    }
}

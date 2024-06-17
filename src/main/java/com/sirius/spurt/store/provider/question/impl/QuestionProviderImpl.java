package com.sirius.spurt.store.provider.question.impl;

import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.common.validator.QuestionValidator;
import com.sirius.spurt.common.validator.UserValidator;
import com.sirius.spurt.store.provider.question.QuestionProvider;
import com.sirius.spurt.store.provider.question.vo.QuestionVo;
import com.sirius.spurt.store.provider.question.vo.QuestionVoList;
import com.sirius.spurt.store.repository.database.entity.CategoryEntity;
import com.sirius.spurt.store.repository.database.entity.KeyWordEntity;
import com.sirius.spurt.store.repository.database.entity.QuestionEntity;
import com.sirius.spurt.store.repository.database.entity.UserEntity;
import com.sirius.spurt.store.repository.database.repository.ExperienceRepository;
import com.sirius.spurt.store.repository.database.repository.QuestionRepository;
import com.sirius.spurt.store.repository.database.repository.UserRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionProviderImpl implements QuestionProvider {
    private final QuestionRepository questionRepository;
    private final ExperienceRepository experienceRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void putPinQuestion(
            final String questionId, final String userId, final Boolean pinIndicator) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        UserValidator.validator(userEntity);

        QuestionEntity previous =
                questionRepository.findByQuestionIdAndUserId(Long.valueOf(questionId), userId);
        QuestionValidator.validate(previous);

        if (!userEntity.getHasPined()) {
            userRepository.save(
                    UserEntity.builder()
                            .userId(userId)
                            .email(userEntity.getEmail())
                            .jobGroup(userEntity.getJobGroup())
                            .hasPined(true)
                            .hasPosted(userEntity.getHasPosted())
                            .build());
        }

        QuestionEntity questionEntity =
                QuestionEntity.builder()
                        .questionId(Long.valueOf(questionId))
                        .categoryEntityList(previous.getCategoryEntityList())
                        .KeyWordEntityList(previous.getKeyWordEntityList())
                        .subject(previous.getSubject())
                        .mainText(previous.getMainText())
                        .jobGroup(userEntity.getJobGroup())
                        .experienceId(previous.getExperienceId())
                        .userId(userId)
                        .pinIndicator(pinIndicator)
                        .pinUpdatedTime(Timestamp.valueOf(LocalDateTime.now()))
                        .build();

        questionRepository.save(questionEntity);
    }

    @Override
    public QuestionVoList randomQuestion(
            final JobGroup jobGroup, final String userId, final Integer count, final Category category) {
        return QuestionVoList.builder()
                .questions(
                        QuestionProviderImplMapper.INSTANCE.toQuestionVos(
                                questionRepository.RandomQuestion(jobGroup, userId, count, category)))
                .build();
    }

    @Override
    public void deleteQuestion(final String userId, final Long questionId) {
        QuestionEntity questionEntity = questionRepository.findByQuestionIdAndUserId(questionId, userId);
        QuestionValidator.validate(questionEntity);
        questionRepository.delete(questionEntity);
    }

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
            final String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        UserValidator.validator(userEntity);

        QuestionEntity previous =
                questionRepository.findByQuestionIdAndUserId(Long.valueOf(questionId), userId);
        QuestionValidator.validate(previous);

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
                        .jobGroup(userEntity.getJobGroup())
                        .experienceId(previous.getExperienceId())
                        .userId(userId)
                        .pinIndicator(previous.getPinIndicator())
                        .pinUpdatedTime(previous.getPinUpdatedTime())
                        .build();

        questionRepository.save(questionEntity);
    }

    @Override
    @Transactional
    public QuestionVo saveQuestion(
            final String subject,
            final String mainText,
            final List<String> keyWordList,
            final List<Category> categoryList,
            final Long experienceId,
            final String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        UserValidator.validator(userEntity);

        QuestionEntity prevQuestion =
                questionRepository.findTopByUserIdOrderByCreateTimestampDesc(userId);
        QuestionValidator.validateTimestamp(prevQuestion);

        List<CategoryEntity> categoryEntityList =
                categoryList.stream()
                        .map(category -> CategoryEntity.builder().category(category).build())
                        .toList();

        List<KeyWordEntity> keyWordEntityList =
                keyWordList.stream()
                        .map(keyWord -> KeyWordEntity.builder().keyWord(keyWord).build())
                        .toList();

        if (!userEntity.getHasPosted()) {
            userRepository.save(
                    UserEntity.builder()
                            .userId(userId)
                            .email(userEntity.getEmail())
                            .jobGroup(userEntity.getJobGroup())
                            .hasPined(userEntity.getHasPined())
                            .hasPosted(true)
                            .build());
        }

        QuestionEntity questionEntity =
                QuestionEntity.builder()
                        .categoryEntityList(categoryEntityList)
                        .KeyWordEntityList(keyWordEntityList)
                        .subject(subject)
                        .mainText(mainText)
                        .jobGroup(userEntity.getJobGroup())
                        .experienceId(experienceId)
                        .userId(userId)
                        .pinIndicator(false)
                        .pinUpdatedTime(Timestamp.valueOf(LocalDateTime.now()))
                        .build();

        return QuestionProviderImplMapper.INSTANCE.toQuestionVo(
                questionRepository.save(questionEntity));
    }

    @Override
    public void deleteQuestionByUser(final String userId) {
        questionRepository.deleteByUserId(userId);
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

package com.sirius.spurt.store.provider.question.impl;

import static com.sirius.spurt.common.meta.ResultCode.NOT_EXIST_USER;
import static com.sirius.spurt.common.meta.ResultCode.NOT_QUESTION_OWNER;

import com.sirius.spurt.common.exception.GlobalException;
import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.common.meta.JobGroup;
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
import jakarta.transaction.Transactional;
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

@Service
@RequiredArgsConstructor
public class QuestionProviderImpl implements QuestionProvider {
    private final QuestionRepository questionRepository;
    private final ExperienceRepository experienceRepository;
    private final UserRepository userRepository;

    @Override
    public void putPinQuestion(
            final String questionId, final String userId, final Boolean pinIndicator) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new GlobalException(NOT_EXIST_USER);
        }

        QuestionEntity previous =
                questionRepository.findByQuestionIdAndUserId(Long.valueOf(questionId), userId);

        if (previous == null) {
            throw new GlobalException(NOT_QUESTION_OWNER);
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
        QuestionEntity entity = questionRepository.findByQuestionIdAndUserId(questionId, userId);
        if (entity == null) {
            throw new GlobalException(NOT_QUESTION_OWNER);
        }
        questionRepository.delete(entity);
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

        if (userEntity == null) {
            throw new GlobalException(NOT_EXIST_USER);
        }

        QuestionEntity previous =
                questionRepository.findByQuestionIdAndUserId(Long.valueOf(questionId), userId);

        if (previous == null) {
            throw new GlobalException(NOT_QUESTION_OWNER);
        }

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

        if (userEntity == null) {
            throw new GlobalException(NOT_EXIST_USER);
        }

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
                        .jobGroup(userEntity.getJobGroup())
                        .experienceId(experienceId)
                        .userId(userId)
                        .pinIndicator(false)
                        .pinUpdatedTime(Timestamp.valueOf(LocalDateTime.now()))
                        .build();

        return QuestionProviderImplMapper.INSTANCE.toQuestionVo(
                questionRepository.save(questionEntity));
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

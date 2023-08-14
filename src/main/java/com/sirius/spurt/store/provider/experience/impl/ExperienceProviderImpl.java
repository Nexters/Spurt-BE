package com.sirius.spurt.store.provider.experience.impl;

import static com.sirius.spurt.common.meta.ResultCode.NOT_EXIST_USER;
import static com.sirius.spurt.common.meta.ResultCode.NOT_EXPERIENCE_OWNER;
import static com.sirius.spurt.common.meta.ResultCode.NO_CONTENT;
import static com.sirius.spurt.common.meta.ResultCode.TIME_FORMAT_ERROR;

import com.sirius.spurt.common.exception.GlobalException;
import com.sirius.spurt.store.provider.experience.ExperienceProvider;
import com.sirius.spurt.store.provider.experience.vo.CategoryVo;
import com.sirius.spurt.store.provider.experience.vo.ExperienceVo;
import com.sirius.spurt.store.provider.experience.vo.ExperienceVoList;
import com.sirius.spurt.store.provider.experience.vo.KeyWordVo;
import com.sirius.spurt.store.provider.experience.vo.QuestionVo;
import com.sirius.spurt.store.provider.experience.vo.QuestionVoList;
import com.sirius.spurt.store.repository.database.entity.CategoryEntity;
import com.sirius.spurt.store.repository.database.entity.ExperienceEntity;
import com.sirius.spurt.store.repository.database.entity.KeyWordEntity;
import com.sirius.spurt.store.repository.database.entity.QuestionEntity;
import com.sirius.spurt.store.repository.database.entity.UserEntity;
import com.sirius.spurt.store.repository.database.repository.ExperienceRepository;
import com.sirius.spurt.store.repository.database.repository.UserRepository;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExperienceProviderImpl implements ExperienceProvider {
    private final ExperienceRepository experienceRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void saveExperience(
            final String title,
            final String content,
            final String startDate,
            final String endDate,
            final String link,
            final String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new GlobalException(NOT_EXIST_USER);
        }

        ExperienceEntity experienceEntity =
                ExperienceEntity.builder()
                        .title(title)
                        .content(content)
                        .startDate(stringToTimestamp(startDate))
                        .endDate(stringToTimestamp(endDate))
                        .link(link)
                        .userEntity(userEntity)
                        .build();

        experienceRepository.save(experienceEntity);
    }

    @Override
    @Transactional
    public void updateExperience(
            final Long experienceId,
            final String title,
            final String content,
            final String startDate,
            final String endDate,
            final String link,
            final String userId) {
        ExperienceEntity previous =
                experienceRepository.findByExperienceIdAndUserEntityUserId(experienceId, userId);

        if (previous == null) {
            throw new GlobalException(NOT_EXPERIENCE_OWNER);
        }

        ExperienceEntity experienceEntity =
                ExperienceEntity.builder()
                        .experienceId(experienceId)
                        .title(title)
                        .content(content)
                        .startDate(stringToTimestamp(startDate))
                        .endDate(stringToTimestamp(endDate))
                        .link(link)
                        .userEntity(previous.getUserEntity())
                        .questionEntityList(previous.getQuestionEntityList())
                        .build();

        experienceRepository.save(experienceEntity);
    }

    @Override
    @Transactional
    public void deleteExperience(final Long experienceId, final String userId) {
        ExperienceEntity previous =
                experienceRepository.findByExperienceIdAndUserEntityUserId(experienceId, userId);

        if (previous == null) {
            throw new GlobalException(NOT_EXPERIENCE_OWNER);
        }

        experienceRepository.deleteById(experienceId);
    }

    @Override
    public ExperienceVoList getAllExperience(final String userId) {
        List<ExperienceEntity> experienceEntityList =
                experienceRepository.findByUserEntityUserId(userId);

        if (CollectionUtils.isEmpty(experienceEntityList)) {
            throw new GlobalException(NO_CONTENT);
        }

        List<ExperienceVo> experienceVoList =
                ExperienceProviderImplMapper.INSTANCE.toExperienceVoList(experienceEntityList);

        return ExperienceVoList.builder()
                .experienceVoList(experienceVoList)
                .totalCount(experienceVoList.size())
                .build();
    }

    @Override
    public ExperienceVo getExperience(final Long experienceId, final String userId) {
        ExperienceEntity experienceEntity =
                experienceRepository.findByExperienceIdAndUserEntityUserId(experienceId, userId);

        if (experienceEntity == null) {
            throw new GlobalException(NO_CONTENT);
        }

        return ExperienceProviderImplMapper.INSTANCE.toExperienceVo(experienceEntity);
    }

    @Mapper
    public interface ExperienceProviderImplMapper {
        ExperienceProviderImplMapper INSTANCE = Mappers.getMapper(ExperienceProviderImplMapper.class);

        @Mapping(source = "startDate", target = "startDate")
        @Mapping(source = "endDate", target = "endDate")
        default String toStringTime(Timestamp timestamp) {
            return timestampToString(timestamp);
        }

        List<KeyWordVo> toKeyWordVoList(List<KeyWordEntity> keyWordEntityList);

        List<CategoryVo> toCategoryVoList(List<CategoryEntity> categoryEntityList);

        @Mapping(source = "keyWordEntityList", target = "keyWordVoList")
        @Mapping(source = "categoryEntityList", target = "categoryVoList")
        QuestionVo toQuestionVo(QuestionEntity questionEntity);

        default QuestionVoList toQuestionVoList(List<QuestionEntity> questionEntityList) {
            List<QuestionVo> unorderedQuestionList =
                    new java.util.ArrayList<>(questionEntityList.stream().map(this::toQuestionVo).toList());

            if (!CollectionUtils.isEmpty(unorderedQuestionList)) {
                unorderedQuestionList.sort(
                        ((o1, o2) -> {
                            if (o1.getPinIndicator() != o2.getPinIndicator()) {
                                return o2.getPinIndicator().compareTo(o1.getPinIndicator());
                            } else {
                                return o2.getPinUpdatedTime().compareTo(o1.getPinUpdatedTime());
                            }
                        }));
            }

            return QuestionVoList.builder()
                    .questionVoList(unorderedQuestionList)
                    .totalCount(questionEntityList.size())
                    .build();
        }

        @Mapping(source = "questionEntityList", target = "questionVoList")
        ExperienceVo toExperienceVo(ExperienceEntity experienceEntity);

        @Mapping(source = "questionEntityList", target = "questionVoList")
        List<ExperienceVo> toExperienceVoList(List<ExperienceEntity> experienceEntityList);
    }

    private Timestamp stringToTimestamp(String time) {
        if (time == null) {
            return null;
        }

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            Date date = simpleDateFormat.parse(time);
            return new Timestamp(date.getTime());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GlobalException(TIME_FORMAT_ERROR);
        }
    }

    private static String timestampToString(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }

        return new SimpleDateFormat("yyyy-MM").format(timestamp);
    }
}

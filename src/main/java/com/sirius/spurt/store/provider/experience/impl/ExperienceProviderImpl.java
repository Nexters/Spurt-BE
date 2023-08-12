package com.sirius.spurt.store.provider.experience.impl;

import static com.sirius.spurt.common.meta.ResultCode.NOT_EXIST_USER;
import static com.sirius.spurt.common.meta.ResultCode.NOT_EXPERIENCE_OWNER;
import static com.sirius.spurt.common.meta.ResultCode.TIME_FORMAT_ERROR;

import com.sirius.spurt.common.exception.GlobalException;
import com.sirius.spurt.store.provider.experience.ExperienceProvider;
import com.sirius.spurt.store.repository.database.entity.ExperienceEntity;
import com.sirius.spurt.store.repository.database.entity.UserEntity;
import com.sirius.spurt.store.repository.database.repository.ExperienceRepository;
import com.sirius.spurt.store.repository.database.repository.UserRepository;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}

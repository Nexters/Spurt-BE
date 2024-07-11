package com.sirius.spurt.common.validator;

import static com.sirius.spurt.common.meta.ResultCode.EXPERIENCE_THREE_SECONDS;
import static com.sirius.spurt.common.meta.ResultCode.NOT_EXPERIENCE_OWNER;
import static com.sirius.spurt.common.meta.ResultCode.NO_CONTENT;

import com.sirius.spurt.common.exception.GlobalException;
import com.sirius.spurt.store.repository.database.entity.ExperienceEntity;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class ExperienceValidator {
    private static long EXPERIENCE_DUPLICATE_TIME = 3000L;

    public static void validateNoContent(ExperienceEntity experienceEntity) {
        if (!isExistExperience(experienceEntity)) {
            throw new GlobalException(NO_CONTENT);
        }
    }

    public static void validateNoContents(List<ExperienceEntity> experienceEntities) {
        if (CollectionUtils.isEmpty(experienceEntities)) {
            throw new GlobalException(NO_CONTENT);
        }
    }

    public static void validate(ExperienceEntity experienceEntity) {
        if (!isExistExperience(experienceEntity)) {
            throw new GlobalException(NOT_EXPERIENCE_OWNER);
        }
    }

    public static void validateTimestamp(ExperienceEntity experienceEntity) {
        if (!isExistExperience(experienceEntity)) {
            return;
        }

        if (isWithin3SecondsDifference(experienceEntity.getCreateTimestamp())) {
            throw new GlobalException(EXPERIENCE_THREE_SECONDS);
        }
    }

    private static boolean isExistExperience(ExperienceEntity experienceEntity) {
        return experienceEntity != null;
    }

    private static boolean isWithin3SecondsDifference(Timestamp timestamp) {
        return new Timestamp(System.currentTimeMillis()).getTime() - timestamp.getTime()
                < EXPERIENCE_DUPLICATE_TIME;
    }
}

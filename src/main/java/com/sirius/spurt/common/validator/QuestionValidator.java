package com.sirius.spurt.common.validator;

import static com.sirius.spurt.common.meta.ResultCode.NOT_QUESTION_OWNER;
import static com.sirius.spurt.common.meta.ResultCode.QUESTION_THREE_SECONDS;

import com.sirius.spurt.common.exception.GlobalException;
import com.sirius.spurt.store.repository.database.entity.QuestionEntity;
import java.sql.Timestamp;

public class QuestionValidator {
    private static long BOARD_DUPLICATE_TIME = 3000L;

    public static void validate(QuestionEntity questionEntity) {
        if (!isExistQuestion(questionEntity)) {
            throw new GlobalException(NOT_QUESTION_OWNER);
        }
    }

    public static void validateTimestamp(QuestionEntity questionEntity) {
        if (!isExistQuestion(questionEntity)) {
            return;
        }

        if (isWithin3SecondsDifference(questionEntity.getCreateTimestamp())) {
            throw new GlobalException(QUESTION_THREE_SECONDS);
        }
    }

    private static boolean isExistQuestion(QuestionEntity questionEntity) {
        return questionEntity != null;
    }

    private static boolean isWithin3SecondsDifference(Timestamp timestamp) {
        return new Timestamp(System.currentTimeMillis()).getTime() - timestamp.getTime()
                < BOARD_DUPLICATE_TIME;
    }
}

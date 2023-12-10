package com.sirius.spurt.common.validator;

import static com.sirius.spurt.common.meta.ResultCode.QUESTION_THREE_SECONDS;

import com.sirius.spurt.common.exception.GlobalException;
import java.sql.Timestamp;

public class QuestionValidator {
    private static long BOARD_DUPLICATE_TIME = 3000L;

    public static void validate(Timestamp timestamp) {
        if (isWithin3SecondsDifference(timestamp)) {
            throw new GlobalException(QUESTION_THREE_SECONDS);
        }
    }

    private static boolean isWithin3SecondsDifference(Timestamp timestamp) {
        return new Timestamp(System.currentTimeMillis()).getTime() - timestamp.getTime() < BOARD_DUPLICATE_TIME;
    }
}

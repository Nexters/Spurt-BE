package com.sirius.spurt.common.validator;

import static com.sirius.spurt.common.meta.ResultCode.MISSING_CATEGORY;
import static com.sirius.spurt.common.meta.ResultCode.NOT_ALL_CATEGORY;
import static com.sirius.spurt.common.meta.ResultCode.NOT_QUESTION_OWNER;
import static com.sirius.spurt.common.meta.ResultCode.QUESTION_THREE_SECONDS;

import com.sirius.spurt.common.exception.GlobalException;
import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.service.business.question.PutQuestionBusiness;
import com.sirius.spurt.service.business.question.SaveQuestionBusiness;
import com.sirius.spurt.store.repository.database.entity.QuestionEntity;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.util.CollectionUtils;

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

    public static void validate(SaveQuestionBusiness.Dto dto) {
        if (isEmptyCategory(dto.getCategoryList())) {
            throw new GlobalException(MISSING_CATEGORY);
        }

        if (isContainsAllCategory(dto.getCategoryList())) {
            throw new GlobalException(NOT_ALL_CATEGORY);
        }
    }

    public static void validate(PutQuestionBusiness.Dto dto) {
        if (isEmptyCategory(dto.getCategoryList())) {
            throw new GlobalException(MISSING_CATEGORY);
        }

        if (isContainsAllCategory(dto.getCategoryList())) {
            throw new GlobalException(NOT_ALL_CATEGORY);
        }
    }

    private static boolean isExistQuestion(QuestionEntity questionEntity) {
        return questionEntity != null;
    }

    private static boolean isWithin3SecondsDifference(Timestamp timestamp) {
        return new Timestamp(System.currentTimeMillis()).getTime() - timestamp.getTime()
                < BOARD_DUPLICATE_TIME;
    }

    private static boolean isContainsAllCategory(List<Category> categoryList) {
        return categoryList.contains(Category.ALL);
    }

    private static boolean isEmptyCategory(List<Category> categoryList) {
        return CollectionUtils.isEmpty(categoryList);
    }
}

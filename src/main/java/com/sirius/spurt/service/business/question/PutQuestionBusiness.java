package com.sirius.spurt.service.business.question;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.common.template.Business;
import com.sirius.spurt.common.validator.QuestionValidator;
import com.sirius.spurt.service.business.question.PutQuestionBusiness.Dto;
import com.sirius.spurt.service.business.question.PutQuestionBusiness.Result;
import com.sirius.spurt.store.provider.question.QuestionProvider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@RequiredArgsConstructor
public class PutQuestionBusiness implements Business<Dto, Result> {

    private final QuestionProvider questionProvider;

    @Override
    public Result execute(Dto input) {
        QuestionValidator.validate(input);
        questionProvider.putQuestion(
                input.getQuestionId(),
                input.getSubject(),
                input.getMainText(),
                input.getKeyWordList(),
                input.getCategoryList(),
                input.getUserId());
        return new Result();
    }

    @JsonIgnoreProperties
    @Data
    @Validated
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Dto implements Business.Dto, Serializable {

        @Serial private static final long serialVersionUID = 4083263567083683976L;
        /** questionID */
        @NotBlank private String questionId;
        /** 제목 (최대 35자) */
        @Size(max = 35)
        @NotBlank
        private String subject;
        /** 본문 (최대 1000자) */
        @Size(max = 1000)
        @NotBlank
        private String mainText;
        /** 키워드 (최대 20개) */
        @Size(max = 20)
        private List<String> keyWordList;
        /** 카테고리 */
        private List<Category> categoryList;
        /** 사용자 ID (프론트 전달 x) */
        private String userId;
    }

    @Setter
    @NoArgsConstructor
    @Data
    @JsonIgnoreProperties
    @Builder
    public static class Result implements Business.Result, Serializable {}
}

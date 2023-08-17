package com.sirius.spurt.service.business.question;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.template.Business;
import com.sirius.spurt.service.business.question.PutPinQuestionBusiness.Dto;
import com.sirius.spurt.service.business.question.PutPinQuestionBusiness.Result;
import com.sirius.spurt.store.provider.question.QuestionProvider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
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
public class PutPinQuestionBusiness implements Business<Dto, Result> {

    private final QuestionProvider questionProvider;

    @Override
    public Result execute(Dto input) {
        questionProvider.putPinQuestion(
                input.getQuestionId(), input.getUserId(), input.getPinIndicator());
        return new Result();
    }

    @JsonIgnoreProperties
    @Data
    @Validated
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Dto implements Business.Dto, Serializable {

        /** questionID */
        @NotBlank private String questionId;
        /** 사용자 ID (프론트 전달 x) */
        private String userId;
        /** pin 여부 변경 값 */
        @NotNull private Boolean pinIndicator;
    }

    @Setter
    @NoArgsConstructor
    @Data
    @JsonIgnoreProperties
    @Builder
    public static class Result implements Business.Result, Serializable {}
}

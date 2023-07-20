package com.sirius.spurt.service.business.question;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.template.Business;
import com.sirius.spurt.service.business.question.SaveQuestionBusiness.Dto;
import com.sirius.spurt.service.business.question.SaveQuestionBusiness.Result;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
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
public class SaveQuestionBusiness implements Business<Dto, Result> {
    @Override
    public Result execute(Dto input) {
        log.info("Start SaveQuestionBusiness");
        return new Result();
    }

    @JsonIgnoreProperties
    @Data
    @Validated
    @Builder
    public static class Dto implements Business.Dto, Serializable {
        @Serial private static final long serialVersionUID = 3250576285065388951L;
        /** 제목 (최대 30자) */
        @Size(max = 30)
        @NotBlank
        private String subject;
        /** 본문 (최대 1000자) */
        @Size(max = 1000)
        @NotBlank
        private String mainText;
        /** 키워드 (최대 20개) */
        @Size(max = 20)
        private List<String> keyWord;
        /** 카테고리 */
        private String category;
        /** 직군 */
        private String jobGroup;
        /** 사용자 ID */
        private String userId;
    }

    @Setter
    @NoArgsConstructor
    @Data
    @JsonIgnoreProperties
    @Builder
    public static class Result implements Business.Result, Serializable {}
}

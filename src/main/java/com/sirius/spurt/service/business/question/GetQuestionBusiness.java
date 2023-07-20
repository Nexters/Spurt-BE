package com.sirius.spurt.service.business.question;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.template.Business;
import com.sirius.spurt.service.business.question.GetQuestionBusiness.Dto;
import com.sirius.spurt.service.business.question.GetQuestionBusiness.Result;
import java.io.Serializable;
import java.sql.Timestamp;
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
public class GetQuestionBusiness implements Business<Dto, Result> {
    @Override
    public Result execute(Dto input) {
        log.info("Start GetQuestionBusiness");
        return new Result();
    }

    @JsonIgnoreProperties
    @Data
    @Validated
    @Builder
    public static class Dto implements Business.Dto, Serializable {
        private String questionId;
    }

    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @JsonIgnoreProperties
    @Builder
    public static class Result implements Business.Result, Serializable {
        /** 제목 */
        private String subject;
        /** 본문 */
        private String mainText;
        /** 카테고리 */
        private String category;
        /** 직군 */
        private String jobGroup;
        /** 생성시간 */
        private Timestamp createTimestamp;
    }
}

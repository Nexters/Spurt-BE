package com.sirius.spurt.service.business.question;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.template.Business;
import com.sirius.spurt.service.business.question.RetrieveQuestionBusiness.Dto;
import com.sirius.spurt.service.business.question.RetrieveQuestionBusiness.Result;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
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
public class RetrieveQuestionBusiness implements Business<Dto, Result> {
    @Override
    public Result execute(Dto input) {
        log.info("Start RetrieveQuestionBusiness");
        return new Result();
    }

    @JsonIgnoreProperties
    @Data
    @Validated
    @Builder
    public static class Dto implements Business.Dto, Serializable {

        @Serial private static final long serialVersionUID = -4507368296777999659L;
        private String userId;
        private String jobGroup;
        private String subject;
        private String category;
        private String pinIndecator;
        private String myQuestionIndecator;
        private String size;
        private String sort;
        private String page;
    }

    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @JsonIgnoreProperties
    @Builder
    public static class Result implements Business.Result, Serializable {

        @Serial private static final long serialVersionUID = -6167887863876370836L;
        /** 질문 정보 */
        private List<Question> question;
        /** 메타 정보 */
        private MetaData meta;

        @Data
        @NoArgsConstructor
        @Builder
        @AllArgsConstructor
        public static class Question {

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

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class MetaData implements Serializable {

            /** 전체 질문 개수 */
            private Integer totalCount;
            /** 현재 page */
            private Integer pageableCount;
            /** 마지막 페이지 여부 */
            private Boolean isEnd;
        }
    }
}

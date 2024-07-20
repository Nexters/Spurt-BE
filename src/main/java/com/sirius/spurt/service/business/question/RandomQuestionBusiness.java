package com.sirius.spurt.service.business.question;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.common.template.Business;
import com.sirius.spurt.service.business.question.RandomQuestionBusiness.Dto;
import com.sirius.spurt.service.business.question.RandomQuestionBusiness.Result;
import com.sirius.spurt.store.provider.question.QuestionProvider;
import com.sirius.spurt.store.provider.question.vo.QuestionVoList;
import com.sirius.spurt.store.provider.user.UserProvider;
import com.sirius.spurt.store.provider.user.vo.UserVo;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@RequiredArgsConstructor
public class RandomQuestionBusiness implements Business<Dto, Result> {

    private final QuestionProvider questionProvider;
    private final UserProvider userProvider;

    @Override
    public Result execute(Dto input) {
        if (Category.ALL == input.getCategory()) {
            input.setCategory(null);
        }

        // 비로그인 유저
        if (input.getUserId() == null) {
            return RandomQuestionBusinessMapper.INSTANCE.toResult(
                    questionProvider.randomQuestion(null, null, input.getCount(), input.getCategory()));
        }

        final UserVo userVo = userProvider.getUserInfo(input.getUserId());
        JobGroup jobGroup = null;
        if (userVo != null) {
            jobGroup = userVo.getJobGroup();
        }

        return RandomQuestionBusinessMapper.INSTANCE.toResult(
                questionProvider.randomQuestion(
                        jobGroup, input.getUserId(), input.getCount(), input.getCategory()));
    }

    @JsonIgnoreProperties
    @Data
    @Validated
    @Builder
    public static class Dto implements Business.Dto, Serializable {

        /** userId (전달 필요 x) */
        private String userId;
        /** 전달 질문 개수 (기본값 4) */
        private Integer count;
        /** 카테고리 */
        private Category category;
    }

    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @JsonIgnoreProperties
    @Builder
    public static class Result implements Business.Result, Serializable {
        /** 질문 정보 */
        private List<RandomQuestionBusiness.Result.Question> questions;

        @Data
        @NoArgsConstructor
        @Builder
        @AllArgsConstructor
        public static class Question {
            /** 제목 */
            private String subject;
            /** 직군 */
            private JobGroup jobGroup;
        }
    }

    @Mapper
    public interface RandomQuestionBusinessMapper {
        RandomQuestionBusiness.RandomQuestionBusinessMapper INSTANCE =
                Mappers.getMapper(RandomQuestionBusiness.RandomQuestionBusinessMapper.class);

        RandomQuestionBusiness.Result toResult(QuestionVoList questions);
    }
}

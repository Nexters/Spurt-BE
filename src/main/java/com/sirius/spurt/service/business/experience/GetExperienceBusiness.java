package com.sirius.spurt.service.business.experience;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.common.template.Business;
import com.sirius.spurt.service.business.experience.GetExperienceBusiness.Dto;
import com.sirius.spurt.service.business.experience.GetExperienceBusiness.Result;
import com.sirius.spurt.store.provider.experience.ExperienceProvider;
import com.sirius.spurt.store.provider.experience.vo.CategoryVo;
import com.sirius.spurt.store.provider.experience.vo.ExperienceVo;
import com.sirius.spurt.store.provider.experience.vo.KeyWordVo;
import com.sirius.spurt.store.provider.experience.vo.QuestionVo;
import com.sirius.spurt.store.provider.experience.vo.QuestionVoList;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@RequiredArgsConstructor
public class GetExperienceBusiness implements Business<Dto, Result> {
    private final ExperienceProvider experienceProvider;

    @Override
    public Result execute(Dto input) {
        return GetExperienceBusinessMapper.INSTANCE.toResult(
                experienceProvider.getExperience(input.getExperienceId(), input.getUserId()));
    }

    @JsonIgnoreProperties
    @Data
    @Validated
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Dto implements Business.Dto, Serializable {
        /** userId 프론트 전달 x */
        private String userId;
        /** experienceId */
        private Long experienceId;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @JsonIgnoreProperties
    @Builder
    public static class Result implements Business.Result, Serializable {
        /** experienceId */
        private Long experienceId;
        /** 제목 (최대 30자) */
        private String title;
        /** 본문 (최대 1000자) */
        private String content;
        /** 시작 날짜 (yyyy-MM) */
        private String startDate;
        /** 종료 날짜 (yyyy-MM) */
        private String endDate;
        /** 관련 링크 */
        private String link;
        /** 관련 질문들 */
        private QuestionList questionList;

        @Data
        @NoArgsConstructor
        @Builder
        @AllArgsConstructor
        public static class QuestionList {
            /** 관련 질문들 */
            private List<Question> questionList;
            /** 관련 질문 갯수 */
            private Integer totalCount;

            @Data
            @NoArgsConstructor
            @Builder
            @AllArgsConstructor
            public static class Question {
                /** questionId */
                private Long questionId;
                /** 제목 */
                private String subject;
                /** 본문 */
                private String mainText;
                /** 핀 여부 */
                private Boolean pinIndicator;
                /** 카테고리 리스트 */
                private List<Category> categoryList;
                /** 키워드 리스트 */
                private List<String> keyWordList;
            }
        }
    }

    @Mapper
    public interface GetExperienceBusinessMapper {
        GetExperienceBusinessMapper INSTANCE = Mappers.getMapper(GetExperienceBusinessMapper.class);

        default List<Category> toCategoryList(List<CategoryVo> categoryVoList) {
            return categoryVoList.stream().map(CategoryVo::getCategory).toList();
        }

        default List<String> toKeywordList(List<KeyWordVo> keyWordVoList) {
            return keyWordVoList.stream().map(KeyWordVo::getKeyWord).toList();
        }

        @Mapping(source = "keyWordVoList", target = "keyWordList")
        @Mapping(source = "categoryVoList", target = "categoryList")
        GetExperienceBusiness.Result.QuestionList.Question toQuestion(QuestionVo questionVo);

        List<GetExperienceBusiness.Result.QuestionList.Question> toQuestionListQuestion(
                List<QuestionVo> questionVoList);

        @Mapping(source = "totalCount", target = "totalCount")
        @Mapping(source = "questionVoList", target = "questionList")
        GetExperienceBusiness.Result.QuestionList toQuestionList(QuestionVoList questionVoList);

        @Mapping(source = "questionVoList", target = "questionList")
        GetExperienceBusiness.Result toResult(ExperienceVo experienceVo);
    }
}

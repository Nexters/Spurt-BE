package com.sirius.spurt.service.business.question;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.common.template.Business;
import com.sirius.spurt.service.business.question.GetQuestionBusiness.Dto;
import com.sirius.spurt.service.business.question.GetQuestionBusiness.Result;
import com.sirius.spurt.store.provider.question.QuestionProvider;
import com.sirius.spurt.store.provider.question.vo.CategoryVo;
import com.sirius.spurt.store.provider.question.vo.KeyWordVo;
import com.sirius.spurt.store.provider.question.vo.QuestionVo;
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
public class GetQuestionBusiness implements Business<Dto, Result> {

    private final QuestionProvider questionProvider;

    @Override
    public Result execute(Dto input) {
        return GetQuestionBusinessMapper.INSTANCE.toResult(
                questionProvider.getQuestion(input.getQuestionId()));
    }

    @JsonIgnoreProperties
    @Data
    @Validated
    @Builder
    public static class Dto implements Business.Dto, Serializable {
        private Long questionId;
    }

    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @JsonIgnoreProperties
    @Builder
    public static class Result implements Business.Result, Serializable {
        /** 질문 ID */
        private Long questionId;
        /** 제목 */
        private String subject;
        /** 본문 */
        private String mainText;
        /** 직군 */
        private JobGroup jobGroup;
        /** 생성시간 */
        private String createTime;
        /** 유저 Key */
        private String userId;
        /** pin 여부 확인 */
        private Boolean pinIndicator;
        /** 키워드 리스트 */
        private List<String> keyWordList;
        /** 카테고리 리스트 */
        private List<Category> categoryList;
    }

    @Mapper
    public interface GetQuestionBusinessMapper {
        GetQuestionBusiness.GetQuestionBusinessMapper INSTANCE =
                Mappers.getMapper(GetQuestionBusiness.GetQuestionBusinessMapper.class);

        default List<Category> toCategory(List<CategoryVo> categoryList) {
            return categoryList.stream().map(categoryVo -> categoryVo.getCategory()).toList();
        }

        default List<String> toKeyword(List<KeyWordVo> keyWordList) {
            return keyWordList.stream().map(KeyWordVo -> KeyWordVo.getKeyWord()).toList();
        }

        Result toResult(QuestionVo vo);
    }
}

package com.sirius.spurt.service.business.question;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.common.template.Business;
import com.sirius.spurt.service.business.question.RetrieveQuestionBusiness.Dto;
import com.sirius.spurt.service.business.question.RetrieveQuestionBusiness.Result;
import com.sirius.spurt.store.provider.question.QuestionProvider;
import com.sirius.spurt.store.provider.question.vo.CategoryVo;
import com.sirius.spurt.store.provider.question.vo.KeyWordVo;
import com.sirius.spurt.store.provider.question.vo.QuestionVoList;
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
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@RequiredArgsConstructor
public class RetrieveQuestionBusiness implements Business<Dto, Result> {

    private final QuestionProvider questionProvider;

    @Override
    public Result execute(Dto input) {

        if (Category.ALL == input.getCategory()) {
            input.setCategory(null);
        }

        QuestionVoList questionVoList =
                questionProvider.searchQuestion(
                        input.getSubject(),
                        input.getJobGroup(),
                        input.getCategory(),
                        input.getPinIndicator(),
                        input.getMyQuestionIndicator(),
                        input.getUserId(),
                        PageRequest.of(Integer.parseInt(input.getOffset()), Integer.parseInt(input.getSize())));

        return RetrieveQuestionBusinessMapper.INSTANCE.toResult(questionVoList);
    }

    @JsonIgnoreProperties
    @Data
    @Validated
    @Builder
    public static class Dto implements Business.Dto, Serializable {

        @Serial private static final long serialVersionUID = -4507368296777999659L;
        private String userId;
        private JobGroup jobGroup;
        private String subject;
        private Category category;
        private Boolean pinIndicator;
        private Boolean myQuestionIndicator;
        private String size;
        private String offset;
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
        private List<Question> questions;
        /** 메타 정보 */
        private MetaData meta;

        @Data
        @NoArgsConstructor
        @Builder
        @AllArgsConstructor
        public static class Question {
            /** 질문 ID */
            private Long questionId;
            /** 제목 */
            private String subject;
            /** 본문 */
            private String mainText;

            /** 키워드 리스트 */
            private List<String> keyWordList;
            /** 카테고리 */
            private List<Category> categoryList;
            /** 직군 */
            private JobGroup jobGroup;
            /** 생성시간 */
            private Timestamp createTimestamp;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class MetaData implements Serializable {
            /** 전체 개수 */
            private Long totalCount;
            /** 전체 페이지 수 */
            private Integer totalPage;
        }
    }

    @Mapper
    public interface RetrieveQuestionBusinessMapper {
        RetrieveQuestionBusiness.RetrieveQuestionBusinessMapper INSTANCE =
                Mappers.getMapper(RetrieveQuestionBusiness.RetrieveQuestionBusinessMapper.class);

        default List<Category> toCategory(List<CategoryVo> categoryListe) {
            return categoryListe.stream().map(categoryVo -> categoryVo.getCategory()).toList();
        }

        default List<String> toKeyword(List<KeyWordVo> keyWordList) {
            return keyWordList.stream().map(KeyWordVo -> KeyWordVo.getKeyWord()).toList();
        }

        @Mapping(source = "totalCount", target = "meta.totalCount")
        @Mapping(source = "totalPage", target = "meta.totalPage")
        RetrieveQuestionBusiness.Result toResult(QuestionVoList questions);
    }
}

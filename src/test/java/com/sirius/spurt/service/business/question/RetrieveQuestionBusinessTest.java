package com.sirius.spurt.service.business.question;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sirius.spurt.service.business.question.RetrieveQuestionBusiness.Dto;
import com.sirius.spurt.service.business.question.RetrieveQuestionBusiness.Result;
import com.sirius.spurt.store.provider.question.QuestionProvider;
import com.sirius.spurt.store.provider.question.vo.CategoryVo;
import com.sirius.spurt.store.provider.question.vo.KeyWordVo;
import com.sirius.spurt.store.provider.question.vo.QuestionVo;
import com.sirius.spurt.store.provider.question.vo.QuestionVoList;
import com.sirius.spurt.test.CategoryTest;
import com.sirius.spurt.test.KeyWordTest;
import com.sirius.spurt.test.QuestionTest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class RetrieveQuestionBusinessTest implements QuestionTest, CategoryTest, KeyWordTest {
    @InjectMocks private RetrieveQuestionBusiness retrieveQuestionBusiness;

    @Mock private QuestionProvider questionProvider;

    @Test
    void 질문_조회_테스트() {
        // given
        Dto dto =
                Dto.builder()
                        .userId(TEST_USER_ID)
                        .subject(TEST_QUESTION_SUBJECT)
                        .jobGroup(TEST_JOB_GROUP)
                        .category(TEST_CATEGORY)
                        .pinIndicator(null)
                        .myQuestionIndicator(Boolean.TRUE)
                        .size(String.valueOf(10))
                        .offset(0)
                        .build();
        KeyWordVo keyWordVo =
                KeyWordVo.builder()
                        .keyWordId(TEST_KEY_WORD_ID)
                        .questionId(TEST_QUESTION_ID)
                        .keyWord(TEST_KEY_WORD_VALUE)
                        .build();
        CategoryVo categoryVo =
                CategoryVo.builder()
                        .categoryId(TEST_CATEGORY_ID)
                        .questionId(TEST_QUESTION_ID)
                        .category(TEST_CATEGORY)
                        .build();
        PageRequest pageRequest = PageRequest.of(0, 10);
        QuestionVo questionVo =
                QuestionVo.builder()
                        .questionId(TEST_QUESTION_ID)
                        .userId(TEST_USER_ID)
                        .subject(TEST_QUESTION_SUBJECT)
                        .mainText(TEST_QUESTION_MAIN_TEXT)
                        .jobGroup(TEST_JOB_GROUP)
                        .createTimestamp(Timestamp.valueOf(LocalDateTime.now()))
                        .keyWordList(List.of(keyWordVo))
                        .categoryList(List.of(categoryVo))
                        .build();
        QuestionVoList questionVoList =
                QuestionVoList.builder()
                        .questions(List.of(questionVo))
                        .pageable(pageRequest)
                        .totalPage(1)
                        .totalCount(1L)
                        .build();
        when(questionProvider.searchQuestion(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(questionVoList);

        // when
        Result result = retrieveQuestionBusiness.execute(dto);

        // then
        verify(questionProvider).searchQuestion(any(), any(), any(), any(), any(), any(), any());
        assertThat(result.getQuestions().size()).isEqualTo(1);
        assertThat(result.getQuestions().get(0).getSubject()).isEqualTo(TEST_QUESTION_SUBJECT);
        assertThat(result.getQuestions().get(0).getMainText()).isEqualTo(TEST_QUESTION_MAIN_TEXT);
        assertThat(result.getMeta().getTotalCount()).isEqualTo(1L);
        assertThat(result.getMeta().getTotalPage()).isEqualTo(1);
    }
}

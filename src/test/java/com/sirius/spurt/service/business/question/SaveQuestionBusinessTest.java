package com.sirius.spurt.service.business.question;

import static com.sirius.spurt.common.meta.ResultCode.NOT_ALL_CATEGORY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sirius.spurt.common.exception.GlobalException;
import com.sirius.spurt.service.business.question.SaveQuestionBusiness.Dto;
import com.sirius.spurt.service.business.question.SaveQuestionBusiness.Result;
import com.sirius.spurt.store.provider.question.QuestionProvider;
import com.sirius.spurt.store.provider.question.vo.QuestionVo;
import com.sirius.spurt.test.CategoryTest;
import com.sirius.spurt.test.KeyWordTest;
import com.sirius.spurt.test.QuestionTest;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SaveQuestionBusinessTest implements QuestionTest, CategoryTest, KeyWordTest {
    @InjectMocks private SaveQuestionBusiness saveQuestionBusiness;

    @Mock private QuestionProvider questionProvider;

    @Nested
    class 질문_저장 {
        @Test
        void 질문_저장_성공_테스트() {
            // given
            Dto dto =
                    Dto.builder()
                            .subject(TEST_QUESTION_SUBJECT)
                            .mainText(TEST_QUESTION_MAIN_TEXT)
                            .keyWordList(List.of(TEST_KEY_WORD_VALUE, TEST_ANOTHER_KEY_WORD_VALUE))
                            .categoryList(List.of(TEST_CATEGORY))
                            .userId(TEST_USER_ID)
                            .build();
            QuestionVo questionVo = QuestionVo.builder().questionId(TEST_QUESTION_ID).build();
            when(questionProvider.saveQuestion(any(), any(), any(), any(), any(), any()))
                    .thenReturn(questionVo);

            // when
            Result result = saveQuestionBusiness.execute(dto);

            // then
            verify(questionProvider).saveQuestion(any(), any(), any(), any(), any(), any());
            assertThat(result.getQuestionId()).isEqualTo(TEST_QUESTION_ID);
        }

        @Test
        void 질문_저장_실패_테스트() {
            // given
            Dto dto =
                    Dto.builder()
                            .subject(TEST_QUESTION_SUBJECT)
                            .mainText(TEST_QUESTION_MAIN_TEXT)
                            .keyWordList(List.of(TEST_KEY_WORD_VALUE, TEST_ANOTHER_KEY_WORD_VALUE))
                            .categoryList(List.of(TEST_ANOTHER_CATEGORY))
                            .userId(TEST_USER_ID)
                            .build();

            // when
            GlobalException exception =
                    assertThrows(
                            GlobalException.class,
                            () -> {
                                saveQuestionBusiness.execute(dto);
                            });

            // then
            verify(questionProvider, times(0)).saveQuestion(any(), any(), any(), any(), any(), any());
            assertThat(exception.getResultCode()).isEqualTo(NOT_ALL_CATEGORY);
        }
    }
}

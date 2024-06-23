package com.sirius.spurt.service.business.question;

import static com.sirius.spurt.common.meta.ResultCode.NOT_ALL_CATEGORY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.sirius.spurt.common.exception.GlobalException;
import com.sirius.spurt.service.business.question.PutQuestionBusiness.Dto;
import com.sirius.spurt.store.provider.question.QuestionProvider;
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
class PutQuestionBusinessTest implements QuestionTest, KeyWordTest, CategoryTest {
    @InjectMocks private PutQuestionBusiness putQuestionBusiness;

    @Mock private QuestionProvider questionProvider;

    @Nested
    class 질문_수정 {
        @Test
        void 질문_수정_성공_테스트() {
            // given
            Dto dto =
                    Dto.builder()
                            .questionId(String.valueOf(TEST_QUESTION_ID))
                            .subject(TEST_QUESTION_SUBJECT)
                            .mainText(TEST_QUESTION_MAIN_TEXT)
                            .keyWordList(List.of(TEST_KEY_WORD_VALUE, TEST_ANOTHER_KEY_WORD_VALUE))
                            .categoryList(List.of(TEST_CATEGORY))
                            .userId(TEST_USER_ID)
                            .build();

            // when
            putQuestionBusiness.execute(dto);

            // then
            verify(questionProvider).putQuestion(any(), any(), any(), any(), any(), any());
        }

        @Test
        void 질문_수정_실패_테스트() {
            // given
            Dto dto =
                    Dto.builder()
                            .questionId(String.valueOf(TEST_QUESTION_ID))
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
                                putQuestionBusiness.execute(dto);
                            });

            // then
            verify(questionProvider, times(0)).putQuestion(any(), any(), any(), any(), any(), any());
            assertThat(exception.getResultCode()).isEqualTo(NOT_ALL_CATEGORY);
        }
    }
}

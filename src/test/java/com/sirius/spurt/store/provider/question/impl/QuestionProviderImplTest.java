package com.sirius.spurt.store.provider.question.impl;

import static com.sirius.spurt.common.meta.ResultCode.NOT_EXIST_USER;
import static com.sirius.spurt.common.meta.ResultCode.NOT_QUESTION_OWNER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sirius.spurt.common.exception.GlobalException;
import com.sirius.spurt.store.repository.database.repository.QuestionRepository;
import com.sirius.spurt.store.repository.database.repository.UserRepository;
import com.sirius.spurt.test.QuestionTest;
import com.sirius.spurt.test.UserTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QuestionProviderImplTest implements QuestionTest, UserTest {
    @InjectMocks private QuestionProviderImpl questionProvider;

    @Mock private QuestionRepository questionRepository;
    @Mock private UserRepository userRepository;

    @Nested
    class 질문_핀_수정 {
        @Test
        void 질문_핀_수정_성공_테스트() {
            // given
            when(userRepository.findByUserId(any())).thenReturn(TEST_USER);
            when(questionRepository.findByQuestionIdAndUserId(any(), any())).thenReturn(TEST_QUESTION);

            // when
            questionProvider.putPinQuestion(
                    String.valueOf(TEST_QUESTION_ID), UserTest.TEST_USER_ID, Boolean.FALSE);

            // then
            verify(userRepository).findByUserId(any());
            verify(questionRepository).findByQuestionIdAndUserId(any(), any());
            verify(userRepository, times(0)).save(any());
            verify(questionRepository).save(any());
        }

        @Test
        void 질문_핀_수정_성공_테스트_첫핀() {
            // given
            when(userRepository.findByUserId(any())).thenReturn(TEST_ANOTHER_USER);
            when(questionRepository.findByQuestionIdAndUserId(any(), any())).thenReturn(TEST_QUESTION);

            // when
            questionProvider.putPinQuestion(
                    String.valueOf(TEST_QUESTION_ID), UserTest.TEST_USER_ID, Boolean.FALSE);

            // then
            verify(userRepository).findByUserId(any());
            verify(questionRepository).findByQuestionIdAndUserId(any(), any());
            verify(userRepository).save(any());
            verify(questionRepository).save(any());
        }

        @Test
        void 질문_핀_수정_실패_테스트_유저없음() {
            // given
            when(userRepository.findByUserId(any())).thenReturn(null);

            // when
            GlobalException exception =
                    assertThrows(
                            GlobalException.class,
                            () -> {
                                questionProvider.putPinQuestion(
                                        String.valueOf(TEST_QUESTION_ID), UserTest.TEST_USER_ID, Boolean.FALSE);
                            });

            // then
            verify(userRepository).findByUserId(any());
            verify(questionRepository, times(0)).findByQuestionIdAndUserId(any(), any());
            verify(userRepository, times(0)).save(any());
            verify(questionRepository, times(0)).save(any());
            assertThat(exception.getResultCode()).isEqualTo(NOT_EXIST_USER);
        }

        @Test
        void 질문_핀_수정_실패_테스트_질문없음() {
            // given
            when(userRepository.findByUserId(any())).thenReturn(TEST_USER);
            when(questionRepository.findByQuestionIdAndUserId(any(), any())).thenReturn(null);

            // when
            GlobalException exception =
                    assertThrows(
                            GlobalException.class,
                            () -> {
                                questionProvider.putPinQuestion(
                                        String.valueOf(TEST_QUESTION_ID), UserTest.TEST_USER_ID, Boolean.FALSE);
                            });

            // then
            verify(userRepository).findByUserId(any());
            verify(questionRepository).findByQuestionIdAndUserId(any(), any());
            verify(userRepository, times(0)).save(any());
            verify(questionRepository, times(0)).save(any());
            assertThat(exception.getResultCode()).isEqualTo(NOT_QUESTION_OWNER);
        }
    }
}
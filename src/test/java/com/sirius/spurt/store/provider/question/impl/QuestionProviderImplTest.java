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
import com.sirius.spurt.store.provider.question.vo.QuestionVo;
import com.sirius.spurt.store.provider.question.vo.QuestionVoList;
import com.sirius.spurt.store.repository.database.entity.QuestionEntity;
import com.sirius.spurt.store.repository.database.repository.QuestionRepository;
import com.sirius.spurt.store.repository.database.repository.UserRepository;
import com.sirius.spurt.test.CategoryTest;
import com.sirius.spurt.test.KeyWordTest;
import com.sirius.spurt.test.QuestionTest;
import com.sirius.spurt.test.UserTest;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class QuestionProviderImplTest implements QuestionTest, UserTest, CategoryTest, KeyWordTest {
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
                    String.valueOf(QuestionTest.TEST_QUESTION_ID), UserTest.TEST_USER_ID, Boolean.FALSE);

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
                    String.valueOf(QuestionTest.TEST_QUESTION_ID), UserTest.TEST_USER_ID, Boolean.FALSE);

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
                                        String.valueOf(QuestionTest.TEST_QUESTION_ID),
                                        UserTest.TEST_USER_ID,
                                        Boolean.FALSE);
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
                                        String.valueOf(QuestionTest.TEST_QUESTION_ID),
                                        UserTest.TEST_USER_ID,
                                        Boolean.FALSE);
                            });

            // then
            verify(userRepository).findByUserId(any());
            verify(questionRepository).findByQuestionIdAndUserId(any(), any());
            verify(userRepository, times(0)).save(any());
            verify(questionRepository, times(0)).save(any());
            assertThat(exception.getResultCode()).isEqualTo(NOT_QUESTION_OWNER);
        }
    }

    @Test
    void 랜덤_질문_조회_테스트() {
        // given
        when(questionRepository.randomQuestion(any(), any(), any(), any()))
                .thenReturn(List.of(TEST_QUESTION, TEST_ANOTHER_QUESTION));

        // when
        QuestionVoList questionVoList = questionProvider.randomQuestion(null, null, 2, null);

        // then
        verify(questionRepository).randomQuestion(any(), any(), any(), any());
        assertThat(questionVoList.getQuestions().size()).isEqualTo(2);
        assertThat(questionVoList.getQuestions().get(0).getSubject()).isEqualTo(TEST_QUESTION_SUBJECT);
        assertThat(questionVoList.getQuestions().get(0).getMainText())
                .isEqualTo(TEST_QUESTION_MAIN_TEXT);
        assertThat(questionVoList.getQuestions().get(1).getSubject())
                .isEqualTo(TEST_ANOTHER_QUESTION_SUBJECT);
        assertThat(questionVoList.getQuestions().get(1).getMainText())
                .isEqualTo(TEST_ANOTHER_QUESTION_MAIN_TEXT);
    }

    @Nested
    class 질문_삭제 {
        @Test
        void 질문_삭제_성공_테스트() {
            // given
            when(questionRepository.findByQuestionIdAndUserId(any(), any())).thenReturn(TEST_QUESTION);

            // when
            questionProvider.deleteQuestion(UserTest.TEST_USER_ID, QuestionTest.TEST_QUESTION_ID);

            // then
            verify(questionRepository).findByQuestionIdAndUserId(any(), any());
            verify(questionRepository).delete(any());
        }

        @Test
        void 질문_삭제_실패_테스트() {
            // given
            when(questionRepository.findByQuestionIdAndUserId(any(), any())).thenReturn(null);

            // when
            GlobalException exception =
                    assertThrows(
                            GlobalException.class,
                            () -> {
                                questionProvider.deleteQuestion(
                                        UserTest.TEST_USER_ID, QuestionTest.TEST_QUESTION_ID);
                            });

            // then
            verify(questionRepository).findByQuestionIdAndUserId(any(), any());
            verify(questionRepository, times(0)).deleteByUserId(any());
            assertThat(exception.getResultCode()).isEqualTo(NOT_QUESTION_OWNER);
        }
    }

    @Test
    void 질문_페이지_조회_테스트() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 1);
        Page<QuestionEntity> pages = new PageImpl<>(List.of(TEST_QUESTION), pageRequest, 1);
        when(questionRepository.searchQuestion(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(pages);

        // when
        QuestionVoList questionVoList =
                questionProvider.searchQuestion(
                        TEST_QUESTION_SUBJECT,
                        TEST_QUESTION_JOB_GROUP,
                        TEST_CATEGORY,
                        TEST_PIN_INDICATOR,
                        Boolean.TRUE,
                        UserTest.TEST_USER_ID,
                        pageRequest);

        // then
        verify(questionRepository).searchQuestion(any(), any(), any(), any(), any(), any(), any());
        assertThat(questionVoList.getPageable()).isEqualTo(pages.getPageable());
        assertThat(questionVoList.getTotalPage()).isEqualTo(pages.getTotalPages());
        assertThat(questionVoList.getTotalCount()).isEqualTo(pages.getTotalElements());
        assertThat(questionVoList.getQuestions().size()).isEqualTo(pages.getContent().size());
        assertThat(questionVoList.getQuestions().get(0).getSubject())
                .isEqualTo(pages.getContent().get(0).getSubject());
        assertThat(questionVoList.getQuestions().get(0).getMainText())
                .isEqualTo(pages.getContent().get(0).getMainText());
    }

    @Test
    void 질문_단건_조회_테스트() {
        // given
        when(questionRepository.findByQuestionId(any())).thenReturn(TEST_QUESTION);

        // when
        QuestionVo questionVo = questionProvider.getQuestion(QuestionTest.TEST_QUESTION_ID);

        // then
        verify(questionRepository).findByQuestionId(any());
        assertThat(questionVo.getSubject()).isEqualTo(TEST_QUESTION_SUBJECT);
        assertThat(questionVo.getMainText()).isEqualTo(TEST_QUESTION_MAIN_TEXT);
    }

    @Nested
    class 질문_수정 {
        @Test
        void 질문_수정_성공_테스트() {
            // given
            when(userRepository.findByUserId(any())).thenReturn(TEST_USER);
            when(questionRepository.findByQuestionIdAndUserId(any(), any())).thenReturn(TEST_QUESTION);

            // when
            questionProvider.putQuestion(
                    String.valueOf(QuestionTest.TEST_QUESTION_ID),
                    TEST_QUESTION_SUBJECT,
                    TEST_QUESTION_MAIN_TEXT,
                    List.of(TEST_KEY_WORD),
                    List.of(TEST_CATEGORY),
                    UserTest.TEST_USER_ID);

            // then
            verify(userRepository).findByUserId(any());
            verify(questionRepository).findByQuestionIdAndUserId(any(), any());
            verify(questionRepository).save(any());
        }

        @Test
        void 질문_수정_실패_테스트_유저없음() {
            // given
            when(userRepository.findByUserId(any())).thenReturn(null);

            // when
            GlobalException exception =
                    assertThrows(
                            GlobalException.class,
                            () -> {
                                questionProvider.putQuestion(
                                        String.valueOf(QuestionTest.TEST_QUESTION_ID),
                                        TEST_QUESTION_SUBJECT,
                                        TEST_QUESTION_MAIN_TEXT,
                                        List.of(TEST_KEY_WORD),
                                        List.of(TEST_CATEGORY),
                                        UserTest.TEST_USER_ID);
                            });

            // then
            verify(userRepository).findByUserId(any());
            verify(questionRepository, times(0)).findByQuestionIdAndUserId(any(), any());
            verify(questionRepository, times(0)).save(any());
            assertThat(exception.getResultCode()).isEqualTo(NOT_EXIST_USER);
        }

        @Test
        void 질문_수정_실패_테스트_질문없음() {
            // given
            when(userRepository.findByUserId(any())).thenReturn(TEST_USER);
            when(questionRepository.findByQuestionIdAndUserId(any(), any())).thenReturn(null);

            // when
            GlobalException exception =
                    assertThrows(
                            GlobalException.class,
                            () -> {
                                questionProvider.putQuestion(
                                        String.valueOf(QuestionTest.TEST_QUESTION_ID),
                                        TEST_QUESTION_SUBJECT,
                                        TEST_QUESTION_MAIN_TEXT,
                                        List.of(TEST_KEY_WORD),
                                        List.of(TEST_CATEGORY),
                                        UserTest.TEST_USER_ID);
                            });

            // then
            verify(userRepository).findByUserId(any());
            verify(questionRepository).findByQuestionIdAndUserId(any(), any());
            verify(questionRepository, times(0)).save(any());
            assertThat(exception.getResultCode()).isEqualTo(NOT_QUESTION_OWNER);
        }
    }
}

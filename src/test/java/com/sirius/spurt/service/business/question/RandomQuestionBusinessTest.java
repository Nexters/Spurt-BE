package com.sirius.spurt.service.business.question;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sirius.spurt.service.business.question.RandomQuestionBusiness.Dto;
import com.sirius.spurt.service.business.question.RandomQuestionBusiness.Result;
import com.sirius.spurt.store.provider.question.QuestionProvider;
import com.sirius.spurt.store.provider.question.vo.QuestionVo;
import com.sirius.spurt.store.provider.question.vo.QuestionVoList;
import com.sirius.spurt.store.provider.user.UserProvider;
import com.sirius.spurt.store.provider.user.vo.UserVo;
import com.sirius.spurt.test.CategoryTest;
import com.sirius.spurt.test.QuestionTest;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RandomQuestionBusinessTest implements QuestionTest, CategoryTest {
    @InjectMocks private RandomQuestionBusiness randomQuestionBusiness;

    @Mock private QuestionProvider questionProvider;
    @Mock private UserProvider userProvider;

    @Nested
    class 질문_랜덤_조회 {
        @Test
        void 질문_랜덤_조회_성공_테스트_로그인() {
            // given
            Dto dto = Dto.builder().userId(TEST_USER_ID).count(1).category(TEST_CATEGORY).build();
            UserVo userVo = UserVo.builder().userId(TEST_USER_ID).jobGroup(TEST_JOB_GROUP).build();
            QuestionVo questionVo =
                    QuestionVo.builder()
                            .questionId(TEST_QUESTION_ID)
                            .userId(TEST_USER_ID)
                            .subject(TEST_QUESTION_SUBJECT)
                            .mainText(TEST_QUESTION_MAIN_TEXT)
                            .jobGroup(TEST_JOB_GROUP)
                            .build();
            QuestionVoList questionVoList =
                    QuestionVoList.builder().questions(List.of(questionVo)).build();
            when(userProvider.getUserInfo(any())).thenReturn(userVo);
            when(questionProvider.randomQuestion(isNotNull(), isNotNull(), any(), any()))
                    .thenReturn(questionVoList);

            // when
            Result result = randomQuestionBusiness.execute(dto);

            // then
            verify(questionProvider, times(0)).randomQuestion(isNull(), isNull(), any(), any());
            verify(userProvider).getUserInfo(any());
            verify(questionProvider).randomQuestion(isNotNull(), isNotNull(), any(), any());
            assertThat(result.getQuestions().size()).isEqualTo(1);
            assertThat(result.getQuestions().get(0).getSubject()).isEqualTo(TEST_QUESTION_SUBJECT);
            assertThat(result.getQuestions().get(0).getJobGroup()).isEqualTo(TEST_JOB_GROUP);
        }

        @Test
        void 질문_랜덤_조회_성공_테스트_비로그인() {
            // given
            Dto dto = Dto.builder().userId(null).count(1).category(TEST_CATEGORY).build();
            QuestionVo questionVo =
                    QuestionVo.builder()
                            .questionId(TEST_ANOTHER_QUESTION_ID)
                            .userId(TEST_ANOTHER_USER_ID)
                            .subject(TEST_ANOTHER_QUESTION_SUBJECT)
                            .mainText(TEST_ANOTHER_QUESTION_MAIN_TEXT)
                            .jobGroup(TEST_JOB_GROUP)
                            .build();
            QuestionVoList questionVoList =
                    QuestionVoList.builder().questions(List.of(questionVo)).build();
            when(questionProvider.randomQuestion(isNull(), isNull(), any(), any()))
                    .thenReturn(questionVoList);

            // when
            Result result = randomQuestionBusiness.execute(dto);

            // then
            verify(questionProvider).randomQuestion(isNull(), isNull(), any(), any());
            verify(userProvider, times(0)).getUserInfo(any());
            verify(questionProvider, times(0)).randomQuestion(isNotNull(), isNotNull(), any(), any());
            assertThat(result.getQuestions().size()).isEqualTo(1);
            assertThat(result.getQuestions().get(0).getSubject())
                    .isEqualTo(TEST_ANOTHER_QUESTION_SUBJECT);
        }
    }
}

package com.sirius.spurt.service.business.question;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sirius.spurt.service.business.question.GetQuestionBusiness.Dto;
import com.sirius.spurt.service.business.question.GetQuestionBusiness.Result;
import com.sirius.spurt.store.provider.experience.ExperienceProvider;
import com.sirius.spurt.store.provider.question.QuestionProvider;
import com.sirius.spurt.store.provider.question.vo.CategoryVo;
import com.sirius.spurt.store.provider.question.vo.KeyWordVo;
import com.sirius.spurt.store.provider.question.vo.QuestionVo;
import com.sirius.spurt.test.CategoryTest;
import com.sirius.spurt.test.KeyWordTest;
import com.sirius.spurt.test.QuestionTest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetQuestionBusinessTest implements QuestionTest, KeyWordTest, CategoryTest {
    @InjectMocks private GetQuestionBusiness getQuestionBusiness;

    @Mock private QuestionProvider questionProvider;
    @Mock private ExperienceProvider experienceProvider;

    private KeyWordVo keyWordVo;
    private CategoryVo categoryVo;

    @BeforeEach
    void init() {
        keyWordVo =
                KeyWordVo.builder()
                        .keyWordId(TEST_KEY_WORD_ID)
                        .questionId(TEST_QUESTION_ID)
                        .keyWord(TEST_KEY_WORD_VALUE)
                        .build();
        categoryVo =
                CategoryVo.builder()
                        .categoryId(TEST_CATEGORY_ID)
                        .questionId(TEST_QUESTION_ID)
                        .category(TEST_CATEGORY)
                        .build();
    }

    @Nested
    class 질문_단건_조회 {
        @Test
        void 질문_단건_조회_성공_테스트() {
            // given
            Dto dto = Dto.builder().questionId(TEST_QUESTION_ID).userId(TEST_USER_ID).build();
            QuestionVo questionVo =
                    QuestionVo.builder()
                            .questionId(TEST_QUESTION_ID)
                            .userId(TEST_USER_ID)
                            .subject(TEST_QUESTION_SUBJECT)
                            .mainText(TEST_QUESTION_MAIN_TEXT)
                            .experienceId(TEST_EXPERIENCE_ID)
                            .createTimestamp(Timestamp.valueOf(LocalDateTime.now()))
                            .keyWordList(List.of(keyWordVo))
                            .categoryList(List.of(categoryVo))
                            .build();
            when(questionProvider.getQuestion(any())).thenReturn(questionVo);
            when(experienceProvider.getQuestionExperienceTitle(any(), any()))
                    .thenReturn(TEST_EXPERIENCE_TITLE);

            // when
            Result result = getQuestionBusiness.execute(dto);

            // then
            verify(questionProvider).getQuestion(any());
            verify(experienceProvider).getQuestionExperienceTitle(any(), any());
            assertThat(result.getSubject()).isEqualTo(TEST_QUESTION_SUBJECT);
            assertThat(result.getMainText()).isEqualTo(TEST_QUESTION_MAIN_TEXT);
            assertThat(result.getExperienceId()).isEqualTo(TEST_EXPERIENCE_ID);
            assertThat(result.getKeyWordList()).contains(TEST_KEY_WORD_VALUE);
            assertThat(result.getCategoryList()).contains(TEST_CATEGORY);
        }

        @Test
        void 질문_단건_조회_성공_테스트_경험없음() {
            // given
            Dto dto = Dto.builder().questionId(TEST_QUESTION_ID).userId(TEST_USER_ID).build();
            QuestionVo questionVo =
                    QuestionVo.builder()
                            .questionId(TEST_QUESTION_ID)
                            .userId(TEST_USER_ID)
                            .subject(TEST_QUESTION_SUBJECT)
                            .mainText(TEST_QUESTION_MAIN_TEXT)
                            .createTimestamp(Timestamp.valueOf(LocalDateTime.now()))
                            .keyWordList(List.of(keyWordVo))
                            .categoryList(List.of(categoryVo))
                            .build();
            when(questionProvider.getQuestion(any())).thenReturn(questionVo);

            // when
            Result result = getQuestionBusiness.execute(dto);

            // then
            verify(questionProvider).getQuestion(any());
            verify(experienceProvider, times(0)).getQuestionExperienceTitle(any(), any());
            assertThat(result.getSubject()).isEqualTo(TEST_QUESTION_SUBJECT);
            assertThat(result.getMainText()).isEqualTo(TEST_QUESTION_MAIN_TEXT);
            assertThat(result.getExperienceId()).isEqualTo(null);
            assertThat(result.getKeyWordList()).contains(TEST_KEY_WORD_VALUE);
            assertThat(result.getCategoryList()).contains(TEST_CATEGORY);
        }
    }
}

package com.sirius.spurt.store.repository.database.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sirius.spurt.common.config.QuerydslConfigTest;
import com.sirius.spurt.store.repository.database.entity.CategoryEntity;
import com.sirius.spurt.store.repository.database.entity.QuestionEntity;
import com.sirius.spurt.store.repository.database.entity.UserEntity;
import com.sirius.spurt.test.CategoryTest;
import com.sirius.spurt.test.QuestionTest;
import com.sirius.spurt.test.UserTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslConfigTest.class)
class QuestionRepositoryTest implements QuestionTest, UserTest, CategoryTest {
    @Autowired private QuestionRepository questionRepository;
    @Autowired private UserRepository userRepository;

    private UserEntity savedUser;
    private CategoryEntity savedCategory;
    private QuestionEntity savedQuestion;

    @BeforeEach
    void init() {
        savedUser = userRepository.save(TEST_USER);
        savedCategory = CategoryEntity.builder().category(TEST_CATEGORY).build();
        savedQuestion =
                questionRepository.save(
                        QuestionEntity.builder()
                                .questionId(QuestionTest.TEST_QUESTION_ID)
                                .userId(savedUser.getUserId())
                                .subject(TEST_QUESTION_SUBJECT)
                                .mainText(TEST_QUESTION_MAIN_TEXT)
                                .jobGroup(TEST_QUESTION_JOB_GROUP)
                                .pinIndicator(TEST_PIN_INDICATOR)
                                .pinUpdatedTime(TEST_PIN_UPDATED_TIME)
                                .experienceId(null)
                                .categoryEntityList(List.of(savedCategory))
                                .build());
    }

    @Test
    void 질문_조회_테스트() {
        // given

        // when
        QuestionEntity questionEntity =
                questionRepository.findByQuestionId(savedQuestion.getQuestionId());

        // then
        assertThat(questionEntity.getUserId()).isEqualTo(savedQuestion.getUserId());
        assertThat(questionEntity.getSubject()).isEqualTo(savedQuestion.getSubject());
        assertThat(questionEntity.getMainText()).isEqualTo(savedQuestion.getMainText());
        assertThat(questionEntity.getJobGroup()).isEqualTo(savedQuestion.getJobGroup());
        assertThat(questionEntity.getPinIndicator()).isEqualTo(savedQuestion.getPinIndicator());
        assertThat(questionEntity.getPinUpdatedTime()).isEqualTo(savedQuestion.getPinUpdatedTime());
        assertThat(questionEntity.getExperienceId()).isEqualTo(savedQuestion.getExperienceId());
    }

    @Test
    void 질문_조회_question_user_테스트() {
        // given

        // when
        QuestionEntity questionEntity =
                questionRepository.findByQuestionIdAndUserId(
                        savedQuestion.getQuestionId(), savedUser.getUserId());

        // then
        assertThat(questionEntity.getUserId()).isEqualTo(savedQuestion.getUserId());
        assertThat(questionEntity.getSubject()).isEqualTo(savedQuestion.getSubject());
        assertThat(questionEntity.getMainText()).isEqualTo(savedQuestion.getMainText());
        assertThat(questionEntity.getJobGroup()).isEqualTo(savedQuestion.getJobGroup());
        assertThat(questionEntity.getPinIndicator()).isEqualTo(savedQuestion.getPinIndicator());
        assertThat(questionEntity.getPinUpdatedTime()).isEqualTo(savedQuestion.getPinUpdatedTime());
        assertThat(questionEntity.getExperienceId()).isEqualTo(savedQuestion.getExperienceId());
    }

    @Test
    void 최근_등록한_질문_조회_테스트() {
        // given

        // when
        QuestionEntity questionEntity =
                questionRepository.findTopByUserIdOrderByCreateTimestampDesc(savedUser.getUserId());

        // then
        assertThat(questionEntity.getUserId()).isEqualTo(savedQuestion.getUserId());
        assertThat(questionEntity.getSubject()).isEqualTo(savedQuestion.getSubject());
        assertThat(questionEntity.getMainText()).isEqualTo(savedQuestion.getMainText());
        assertThat(questionEntity.getJobGroup()).isEqualTo(savedQuestion.getJobGroup());
        assertThat(questionEntity.getPinIndicator()).isEqualTo(savedQuestion.getPinIndicator());
        assertThat(questionEntity.getPinUpdatedTime()).isEqualTo(savedQuestion.getPinUpdatedTime());
        assertThat(questionEntity.getExperienceId()).isEqualTo(savedQuestion.getExperienceId());
    }

    @Test
    void 질문_삭제_user_테스트() {
        // given
        questionRepository.deleteByUserId(savedUser.getUserId());

        // when
        QuestionEntity questionEntity =
                questionRepository.findTopByUserIdOrderByCreateTimestampDesc(savedUser.getUserId());

        // then
        assertThat(questionEntity).isNull();
    }

    @Test
    void 질문_페이지_조회_테스트() {
        // given
        questionRepository.save(
                QuestionEntity.builder()
                        .questionId(QuestionTest.TEST_QUESTION_ID)
                        .userId(savedUser.getUserId())
                        .subject(TEST_QUESTION_SUBJECT)
                        .mainText(TEST_QUESTION_MAIN_TEXT)
                        .jobGroup(TEST_QUESTION_JOB_GROUP)
                        .pinIndicator(TEST_PIN_INDICATOR)
                        .pinUpdatedTime(TEST_PIN_UPDATED_TIME)
                        .experienceId(null)
                        .build());
        PageRequest pageRequest = PageRequest.of(0, 1);

        // when
        Page<QuestionEntity> pages =
                questionRepository.searchQuestion(
                        null, null, null, null, Boolean.TRUE, savedUser.getUserId(), pageRequest);

        // then
        assertThat(pages.getTotalPages()).isEqualTo(2);
        assertThat(pages.getTotalElements()).isEqualTo(2);
        assertThat(pages.getContent().get(0)).isEqualTo(savedQuestion);
    }

    @Test
    void 랜덤_질문_조회_테스트() {
        // given
        CategoryEntity savedCategory2 = CategoryEntity.builder().category(TEST_CATEGORY).build();
        questionRepository.save(
                QuestionEntity.builder()
                        .questionId(QuestionTest.TEST_ANOTHER_QUESTION_ID)
                        .userId(savedUser.getUserId())
                        .subject(TEST_QUESTION_SUBJECT)
                        .mainText(TEST_QUESTION_MAIN_TEXT)
                        .jobGroup(TEST_QUESTION_JOB_GROUP)
                        .pinIndicator(TEST_PIN_INDICATOR)
                        .pinUpdatedTime(TEST_PIN_UPDATED_TIME)
                        .experienceId(null)
                        .categoryEntityList(List.of(savedCategory2))
                        .build());

        // when
        List<QuestionEntity> questionEntity1 =
                questionRepository.randomQuestion(
                        TEST_QUESTION_JOB_GROUP, TEST_ANOTHER_USER_ID, 1, TEST_CATEGORY);

        List<QuestionEntity> questionEntity2 = null;
        int cnt = 100;
        do {
            questionEntity2 =
                    questionRepository.randomQuestion(
                            TEST_QUESTION_JOB_GROUP, TEST_ANOTHER_USER_ID, 1, TEST_CATEGORY);
            cnt--;
        } while (questionEntity1.get(0).equals(questionEntity2.get(0)) && cnt > 0);

        // then
        assertThat(questionEntity1.get(0)).isNotEqualTo(questionEntity2.get(0));
    }
}

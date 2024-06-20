package com.sirius.spurt.store.repository.database.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sirius.spurt.common.config.QuerydslConfigTest;
import com.sirius.spurt.store.repository.database.entity.ExperienceEntity;
import com.sirius.spurt.store.repository.database.entity.QuestionEntity;
import com.sirius.spurt.store.repository.database.entity.UserEntity;
import com.sirius.spurt.test.ExperienceTest;
import com.sirius.spurt.test.QuestionTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslConfigTest.class)
class QuestionRepositoryTest implements QuestionTest, ExperienceTest {
    @Autowired private QuestionRepository questionRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ExperienceRepository experienceRepository;

    private UserEntity savedUser;
    private ExperienceEntity savedExperience;
    private QuestionEntity savedQuestion;

    @BeforeEach
    void init() {
        savedUser = userRepository.save(TEST_USER);
        savedExperience = experienceRepository.save(TEST_EXPERIENCE);
        savedQuestion =
                questionRepository.save(
                        QuestionEntity.builder()
                                .questionId(TEST_QUESTION_ID)
                                .userId(savedUser.getUserId())
                                .subject(TEST_QUESTION_SUBJECT)
                                .mainText(TEST_QUESTION_MAIN_TEXT)
                                .jobGroup(TEST_QUESTION_JOB_GROUP)
                                .pinIndicator(TEST_PIN_INDICATOR)
                                .pinUpdatedTime(TEST_PIN_UPDATED_TIME)
                                .experienceId(savedExperience.getExperienceId())
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
}

package com.sirius.spurt.test;

import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.store.repository.database.entity.QuestionEntity;
import java.sql.Timestamp;

public interface QuestionTest extends UserTest, ExperienceTest {
    Long TEST_QUESTION_ID = 1L;
    String TEST_QUESTION_SUBJECT = "subject";
    String TEST_QUESTION_MAIN_TEXT = "mainText";
    JobGroup TEST_QUESTION_JOB_GROUP = JobGroup.DEVELOPER;
    Boolean TEST_PIN_INDICATOR = Boolean.TRUE;
    Timestamp TEST_PIN_UPDATED_TIME = Timestamp.valueOf("2024-06-21 00:00:00");

    Long TEST_ANOTHER_QUESTION_ID = 2L;
    String TEST_ANOTHER_QUESTION_SUBJECT = "anotherSubject";
    String TEST_ANOTHER_QUESTION_MAIN_TEXT = "anotherMainText";

    QuestionEntity TEST_QUESTION =
            QuestionEntity.builder()
                    .questionId(TEST_QUESTION_ID)
                    .userId(TEST_USER_ID)
                    .subject(TEST_QUESTION_SUBJECT)
                    .mainText(TEST_QUESTION_MAIN_TEXT)
                    .jobGroup(TEST_QUESTION_JOB_GROUP)
                    .pinIndicator(TEST_PIN_INDICATOR)
                    .pinUpdatedTime(TEST_PIN_UPDATED_TIME)
                    .experienceId(TEST_EXPERIENCE_ID)
                    .build();

    QuestionEntity TEST_ANOTHER_QUESTION =
            QuestionEntity.builder()
                    .questionId(TEST_QUESTION_ID)
                    .userId(TEST_ANOTHER_USER_ID)
                    .subject(TEST_ANOTHER_QUESTION_SUBJECT)
                    .mainText(TEST_ANOTHER_QUESTION_MAIN_TEXT)
                    .jobGroup(TEST_QUESTION_JOB_GROUP)
                    .pinIndicator(TEST_PIN_INDICATOR)
                    .pinUpdatedTime(TEST_PIN_UPDATED_TIME)
                    .experienceId(TEST_ANOTHER_EXPERIENCE_ID)
                    .build();
}

package com.sirius.spurt.test;

import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.store.repository.database.entity.QuestionEntity;
import java.sql.Timestamp;

public interface QuestionTest {
    Long TEST_QUESTION_ID = 1L;
    Long TEST_ANOTHER_QUESTION_ID = 2L;
    String TEST_USER_ID = "userId";
    String TEST_QUESTION_SUBJECT = "subject";
    String TEST_QUESTION_MAIN_TEXT = "mainText";
    JobGroup TEST_QUESTION_JOB_GROUP = JobGroup.DEVELOPER;
    Boolean TEST_PIN_INDICATOR = Boolean.TRUE;
    Timestamp TEST_PIN_UPDATED_TIME = Timestamp.valueOf("2024-06-21 00:00:00");
    Long TEST_EXPERIENCE_ID = 1L;

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
}
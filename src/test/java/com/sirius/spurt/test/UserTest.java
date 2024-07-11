package com.sirius.spurt.test;

import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.store.repository.database.entity.UserEntity;

public interface UserTest {
    String TEST_USER_ID = "userId";
    JobGroup TEST_JOB_GROUP = JobGroup.DEVELOPER;
    String TEST_EMAIL = "email@gmail.com";
    Boolean TEST_HAS_PINED = Boolean.TRUE;
    Boolean TEST_HAS_POSTED = Boolean.TRUE;

    String TEST_ANOTHER_USER_ID = "anotherUserId";
    String TEST_ANOTHER_EMAIL = "another@gmail.com";
    Boolean TEST_ANOTHER_HAS_PINED = Boolean.FALSE;
    Boolean TEST_ANOTHER_HAS_POSTED = Boolean.FALSE;

    UserEntity TEST_USER =
            UserEntity.builder()
                    .userId(TEST_USER_ID)
                    .jobGroup(TEST_JOB_GROUP)
                    .email(TEST_EMAIL)
                    .hasPined(TEST_HAS_PINED)
                    .hasPosted(TEST_HAS_POSTED)
                    .build();

    UserEntity TEST_ANOTHER_USER =
            UserEntity.builder()
                    .userId(TEST_ANOTHER_USER_ID)
                    .jobGroup(TEST_JOB_GROUP)
                    .email(TEST_ANOTHER_EMAIL)
                    .hasPined(TEST_ANOTHER_HAS_PINED)
                    .hasPosted(TEST_ANOTHER_HAS_POSTED)
                    .build();
}

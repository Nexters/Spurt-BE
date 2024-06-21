package com.sirius.spurt.test;

import com.sirius.spurt.store.repository.database.entity.ExperienceEntity;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public interface ExperienceTest extends UserTest {
    Long TEST_EXPERIENCE_ID = 1L;
    String TEST_EXPERIENCE_TITLE = "title";
    String TEST_EXPERIENCE_CONTENT = "content";
    Timestamp TEST_EXPERIENCE_START_DATE = Timestamp.valueOf("2023-07-01 00:00:00");
    String TEST_EXPERIENCE_START_DATE_STRING =
            new SimpleDateFormat("yyyy-MM").format(TEST_EXPERIENCE_START_DATE);
    Timestamp TEST_EXPERIENCE_END_DATE = Timestamp.valueOf("2023-08-19 00:00:00");
    String TEST_EXPERIENCE_END_DATE_STRING =
            new SimpleDateFormat("yyyy-MM").format(TEST_EXPERIENCE_END_DATE);
    String TEST_EXPERIENCE_LINK = "link";

    Long TEST_ANOTHER_EXPERIENCE_ID = 2L;
    String TEST_ANOTHER_EXPERIENCE_TITLE = "anotherTitle";
    String TEST_ANOTHER_EXPERIENCE_CONTENT = "anotherContent";
    String TEST_ANOTHER_EXPERIENCE_LINK = "anotherLink";

    ExperienceEntity TEST_EXPERIENCE =
            ExperienceEntity.builder()
                    .experienceId(TEST_EXPERIENCE_ID)
                    .title(TEST_EXPERIENCE_TITLE)
                    .content(TEST_EXPERIENCE_CONTENT)
                    .startDate(TEST_EXPERIENCE_START_DATE)
                    .endDate(TEST_EXPERIENCE_END_DATE)
                    .link(TEST_EXPERIENCE_LINK)
                    .userEntity(TEST_USER)
                    .build();

    ExperienceEntity TEST_ANOTHER_EXPERIENCE =
            ExperienceEntity.builder()
                    .experienceId(TEST_ANOTHER_EXPERIENCE_ID)
                    .title(TEST_ANOTHER_EXPERIENCE_TITLE)
                    .content(TEST_ANOTHER_EXPERIENCE_CONTENT)
                    .startDate(TEST_EXPERIENCE_START_DATE)
                    .endDate(TEST_EXPERIENCE_END_DATE)
                    .link(TEST_ANOTHER_EXPERIENCE_LINK)
                    .userEntity(TEST_USER)
                    .build();
}

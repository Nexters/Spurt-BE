package com.sirius.spurt.store.repository.database.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sirius.spurt.common.config.QuerydslConfigTest;
import com.sirius.spurt.store.repository.database.entity.ExperienceEntity;
import com.sirius.spurt.test.ExperienceTest;
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
class ExperienceRepositoryTest implements ExperienceTest {
    @Autowired private ExperienceRepository experienceRepository;
    @Autowired private UserRepository userRepository;

    @Test
    void 경험_조회_experienceId_userId_테스트() {
        // given
        userRepository.save(TEST_USER);
        ExperienceEntity savedExperience = experienceRepository.save(TEST_EXPERIENCE);

        // when
        ExperienceEntity experienceEntity =
                experienceRepository.findByExperienceIdAndUserEntityUserId(
                        savedExperience.getExperienceId(), TEST_USER_ID);

        // then
        assertThat(experienceEntity.getTitle()).isEqualTo(savedExperience.getTitle());
        assertThat(experienceEntity.getContent()).isEqualTo(savedExperience.getContent());
        assertThat(experienceEntity.getStartDate()).isEqualTo(savedExperience.getStartDate());
        assertThat(experienceEntity.getEndDate()).isEqualTo(savedExperience.getEndDate());
        assertThat(experienceEntity.getLink()).isEqualTo(savedExperience.getLink());
        assertThat(experienceEntity.getUserEntity()).isEqualTo(savedExperience.getUserEntity());
    }
}

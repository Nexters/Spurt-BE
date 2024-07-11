package com.sirius.spurt.store.repository.database.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sirius.spurt.common.config.QuerydslConfigTest;
import com.sirius.spurt.store.repository.database.entity.ExperienceEntity;
import com.sirius.spurt.test.ExperienceTest;
import java.util.List;
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

    @Test
    void 경험_조회_userId_테스트() {
        // given
        userRepository.save(TEST_USER);
        ExperienceEntity savedExperience = experienceRepository.save(TEST_EXPERIENCE);

        // when
        List<ExperienceEntity> experienceEntities =
                experienceRepository.findByUserEntityUserId(TEST_USER_ID);

        // then
        assertThat(experienceEntities.get(0).getTitle()).isEqualTo(savedExperience.getTitle());
        assertThat(experienceEntities.get(0).getContent()).isEqualTo(savedExperience.getContent());
        assertThat(experienceEntities.get(0).getStartDate()).isEqualTo(savedExperience.getStartDate());
        assertThat(experienceEntities.get(0).getEndDate()).isEqualTo(savedExperience.getEndDate());
        assertThat(experienceEntities.get(0).getLink()).isEqualTo(savedExperience.getLink());
        assertThat(experienceEntities.get(0).getUserEntity())
                .isEqualTo(savedExperience.getUserEntity());
    }

    @Test
    void 최근_등록한_경험_조회_테스트() {
        // given
        userRepository.save(TEST_USER);
        ExperienceEntity savedExperience = experienceRepository.save(TEST_EXPERIENCE);

        // when
        ExperienceEntity experienceEntity =
                experienceRepository.findTopByUserEntityOrderByCreateTimestampDesc(TEST_USER);

        // then
        assertThat(experienceEntity.getTitle()).isEqualTo(savedExperience.getTitle());
        assertThat(experienceEntity.getContent()).isEqualTo(savedExperience.getContent());
        assertThat(experienceEntity.getStartDate()).isEqualTo(savedExperience.getStartDate());
        assertThat(experienceEntity.getEndDate()).isEqualTo(savedExperience.getEndDate());
        assertThat(experienceEntity.getLink()).isEqualTo(savedExperience.getLink());
        assertThat(experienceEntity.getUserEntity()).isEqualTo(savedExperience.getUserEntity());
    }

    @Test
    void 경험_삭제_user_테스트() {
        // given
        userRepository.save(TEST_USER);
        experienceRepository.save(TEST_EXPERIENCE);
        experienceRepository.deleteByUserEntity(TEST_USER);

        // when
        ExperienceEntity experienceEntity =
                experienceRepository.findTopByUserEntityOrderByCreateTimestampDesc(TEST_USER);

        // then
        assertThat(experienceEntity).isNull();
    }
}

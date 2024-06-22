package com.sirius.spurt.service.business.experience;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.sirius.spurt.service.business.experience.UpdateExperienceBusiness.Dto;
import com.sirius.spurt.store.provider.experience.ExperienceProvider;
import com.sirius.spurt.test.ExperienceTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateExperienceBusinessTest implements ExperienceTest {
    @InjectMocks private UpdateExperienceBusiness updateExperienceBusiness;

    @Mock private ExperienceProvider experienceProvider;

    @Test
    void 경험_수정_테스트() {
        // given
        Dto dto =
                Dto.builder()
                        .experienceId(TEST_EXPERIENCE_ID)
                        .title(TEST_EXPERIENCE_TITLE)
                        .content(TEST_EXPERIENCE_CONTENT)
                        .startDate(TEST_EXPERIENCE_START_DATE_STRING)
                        .endDate(TEST_EXPERIENCE_END_DATE_STRING)
                        .link(TEST_EXPERIENCE_LINK)
                        .userId(TEST_USER_ID)
                        .build();

        // when
        updateExperienceBusiness.execute(dto);

        // then
        verify(experienceProvider).updateExperience(any(), any(), any(), any(), any(), any(), any());
    }
}

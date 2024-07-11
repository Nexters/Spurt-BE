package com.sirius.spurt.service.business.experience;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sirius.spurt.service.business.experience.GetExperienceBusiness.Dto;
import com.sirius.spurt.service.business.experience.GetExperienceBusiness.Result;
import com.sirius.spurt.store.provider.experience.ExperienceProvider;
import com.sirius.spurt.store.provider.experience.vo.ExperienceVo;
import com.sirius.spurt.test.ExperienceTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetExperienceBusinessTest implements ExperienceTest {
    @InjectMocks private GetExperienceBusiness getExperienceBusiness;

    @Mock private ExperienceProvider experienceProvider;

    @Test
    void 경험_단건_조회_테스트() {
        // given
        ExperienceVo experienceVo =
                ExperienceVo.builder()
                        .experienceId(TEST_EXPERIENCE_ID)
                        .title(TEST_EXPERIENCE_TITLE)
                        .content(TEST_EXPERIENCE_CONTENT)
                        .startDate(TEST_EXPERIENCE_START_DATE_STRING)
                        .endDate(TEST_EXPERIENCE_END_DATE_STRING)
                        .link(TEST_EXPERIENCE_LINK)
                        .build();
        Dto dto = Dto.builder().userId(TEST_USER_ID).experienceId(TEST_EXPERIENCE_ID).build();
        when(experienceProvider.getExperience(any(), any())).thenReturn(experienceVo);

        // when
        Result result = getExperienceBusiness.execute(dto);

        // then
        verify(experienceProvider).getExperience(any(), any());
        assertThat(result.getExperienceId()).isEqualTo(TEST_EXPERIENCE_ID);
        assertThat(result.getTitle()).isEqualTo(TEST_EXPERIENCE_TITLE);
        assertThat(result.getContent()).isEqualTo(TEST_EXPERIENCE_CONTENT);
        assertThat(result.getStartDate()).isEqualTo(TEST_EXPERIENCE_START_DATE_STRING);
        assertThat(result.getEndDate()).isEqualTo(TEST_EXPERIENCE_END_DATE_STRING);
        assertThat(result.getLink()).isEqualTo(TEST_EXPERIENCE_LINK);
    }
}

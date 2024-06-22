package com.sirius.spurt.service.business.experience;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sirius.spurt.service.business.experience.GetAllExperienceBusiness.Dto;
import com.sirius.spurt.service.business.experience.GetAllExperienceBusiness.Result;
import com.sirius.spurt.store.provider.experience.ExperienceProvider;
import com.sirius.spurt.store.provider.experience.vo.ExperienceVo;
import com.sirius.spurt.store.provider.experience.vo.ExperienceVoList;
import com.sirius.spurt.test.ExperienceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetAllExperienceBusinessTest implements ExperienceTest {
    @InjectMocks private GetAllExperienceBusiness getAllExperienceBusiness;

    @Mock private ExperienceProvider experienceProvider;

    @Test
    void 경험_전체_조회_테스트() {
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
        ExperienceVo anotherExperienceVo =
                ExperienceVo.builder()
                        .experienceId(TEST_ANOTHER_EXPERIENCE_ID)
                        .title(TEST_ANOTHER_EXPERIENCE_TITLE)
                        .content(TEST_ANOTHER_EXPERIENCE_CONTENT)
                        .startDate(TEST_EXPERIENCE_START_DATE_STRING)
                        .endDate(TEST_EXPERIENCE_END_DATE_STRING)
                        .link(TEST_ANOTHER_EXPERIENCE_LINK)
                        .build();
        ExperienceVoList experienceVoList =
                ExperienceVoList.builder()
                        .experienceVoList(List.of(experienceVo, anotherExperienceVo))
                        .totalCount(2)
                        .build();
        Dto dto = Dto.builder().userId(TEST_USER_ID).build();
        when(experienceProvider.getAllExperience(any())).thenReturn(experienceVoList);

        // when
        Result result = getAllExperienceBusiness.execute(dto);

        // then
        verify(experienceProvider).getAllExperience(any());
        assertThat(result.getTotalCount()).isEqualTo(2);
        assertThat(result.getExperienceList().size()).isEqualTo(2);
        assertThat(result.getExperienceList().get(0).getExperienceId()).isEqualTo(TEST_EXPERIENCE_ID);
        assertThat(result.getExperienceList().get(0).getTitle()).isEqualTo(TEST_EXPERIENCE_TITLE);
        assertThat(result.getExperienceList().get(0).getContent()).isEqualTo(TEST_EXPERIENCE_CONTENT);
        assertThat(result.getExperienceList().get(0).getLink()).isEqualTo(TEST_EXPERIENCE_LINK);
        assertThat(result.getExperienceList().get(1).getExperienceId())
                .isEqualTo(TEST_ANOTHER_EXPERIENCE_ID);
        assertThat(result.getExperienceList().get(1).getTitle())
                .isEqualTo(TEST_ANOTHER_EXPERIENCE_TITLE);
        assertThat(result.getExperienceList().get(1).getContent())
                .isEqualTo(TEST_ANOTHER_EXPERIENCE_CONTENT);
        assertThat(result.getExperienceList().get(1).getLink()).isEqualTo(TEST_ANOTHER_EXPERIENCE_LINK);
    }
}

package com.sirius.spurt.service.business.experience;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.sirius.spurt.service.business.experience.DeleteExperienceBusiness.Dto;
import com.sirius.spurt.store.provider.experience.ExperienceProvider;
import com.sirius.spurt.test.ExperienceTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteExperienceBusinessTest implements ExperienceTest {
    @InjectMocks private DeleteExperienceBusiness deleteExperienceBusiness;

    @Mock private ExperienceProvider experienceProvider;

    @Test
    void 경험_삭제_테스트() {
        // given
        Dto dto = Dto.builder().experienceId(TEST_EXPERIENCE_ID).userId(TEST_USER_ID).build();

        // when
        deleteExperienceBusiness.execute(dto);

        // then
        verify(experienceProvider).deleteExperience(any(), any());
    }
}

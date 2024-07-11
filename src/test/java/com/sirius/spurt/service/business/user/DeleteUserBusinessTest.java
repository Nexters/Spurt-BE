package com.sirius.spurt.service.business.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.sirius.spurt.service.business.user.DeleteUserBusiness.Dto;
import com.sirius.spurt.store.provider.experience.ExperienceProvider;
import com.sirius.spurt.store.provider.question.QuestionProvider;
import com.sirius.spurt.store.provider.user.UserProvider;
import com.sirius.spurt.test.UserTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteUserBusinessTest implements UserTest {
    @InjectMocks private DeleteUserBusiness deleteUserBusiness;

    @Mock private UserProvider userProvider;
    @Mock private ExperienceProvider experienceProvider;
    @Mock private QuestionProvider questionProvider;

    @Test
    void 유저_삭제_테스트() {
        // given
        Dto dto = Dto.builder().userId(TEST_USER_ID).build();

        // when
        deleteUserBusiness.execute(dto);

        // then
        verify(experienceProvider).deleteExperienceByUser(any());
        verify(questionProvider).deleteQuestionByUser(any());
        verify(userProvider).deleteUser(any());
    }
}

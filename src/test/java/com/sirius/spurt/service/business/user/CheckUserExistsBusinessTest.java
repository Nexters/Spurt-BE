package com.sirius.spurt.service.business.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sirius.spurt.service.business.user.CheckUserExistsBusiness.Dto;
import com.sirius.spurt.service.business.user.CheckUserExistsBusiness.Result;
import com.sirius.spurt.store.provider.user.UserProvider;
import com.sirius.spurt.test.UserTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CheckUserExistsBusinessTest implements UserTest {
    @InjectMocks private CheckUserExistsBusiness checkUserExistsBusiness;

    @Mock private UserProvider userProvider;

    @Test
    void 유저_존재_확인_테스트() {
        // given
        Dto dto = Dto.builder().userId(TEST_USER_ID).build();
        when(userProvider.checkUserExists(any())).thenReturn(true);

        // when
        Result result = checkUserExistsBusiness.execute(dto);

        // then
        verify(userProvider).checkUserExists(any());
        assertThat(result.isUserExists()).isEqualTo(true);
    }
}

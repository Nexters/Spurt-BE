package com.sirius.spurt.service.business.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sirius.spurt.service.business.user.CheckUserHasPinedBusiness.Dto;
import com.sirius.spurt.service.business.user.CheckUserHasPinedBusiness.Result;
import com.sirius.spurt.store.provider.user.UserProvider;
import com.sirius.spurt.test.UserTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CheckUserHasPinedBusinessTest implements UserTest {
    @InjectMocks private CheckUserHasPinedBusiness checkUserHasPinedBusiness;

    @Mock private UserProvider userProvider;

    @Test
    void 유저_첫핀_확인_테스트() {
        // given
        Dto dto = Dto.builder().userId(TEST_USER_ID).build();
        when(userProvider.checkHasPined(any())).thenReturn(true);

        // when
        Result result = checkUserHasPinedBusiness.execute(dto);

        // then
        verify(userProvider).checkHasPined(any());
        assertThat(result.isHasPined()).isEqualTo(true);
    }
}

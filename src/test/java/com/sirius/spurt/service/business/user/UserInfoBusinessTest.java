package com.sirius.spurt.service.business.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sirius.spurt.service.business.user.UserInfoBusiness.Dto;
import com.sirius.spurt.service.business.user.UserInfoBusiness.Result;
import com.sirius.spurt.store.provider.user.UserProvider;
import com.sirius.spurt.store.provider.user.vo.UserVo;
import com.sirius.spurt.test.UserTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserInfoBusinessTest implements UserTest {
    @InjectMocks private UserInfoBusiness userInfoBusiness;

    @Mock private UserProvider userProvider;

    @Test
    void 유저_정보_조회_테스트() {
        // given
        Dto dto = Dto.builder().userId(TEST_USER_ID).build();
        UserVo userVo = UserVo.builder().userId(TEST_USER_ID).jobGroup(TEST_JOB_GROUP).build();
        when(userProvider.getUserInfo(any())).thenReturn(userVo);

        // when
        Result result = userInfoBusiness.execute(dto);

        // then
        verify(userProvider).getUserInfo(any());
        assertThat(result.getUserId()).isEqualTo(TEST_USER_ID);
        assertThat(result.getJobGroup()).isEqualTo(TEST_JOB_GROUP);
    }
}

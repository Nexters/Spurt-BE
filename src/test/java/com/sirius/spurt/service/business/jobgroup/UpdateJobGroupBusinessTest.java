package com.sirius.spurt.service.business.jobgroup;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.sirius.spurt.common.resolver.user.LoginUser;
import com.sirius.spurt.service.business.jobgroup.UpdateJobGroupBusiness.Dto;
import com.sirius.spurt.store.provider.jobgroup.JobGroupProvider;
import com.sirius.spurt.test.UserTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateJobGroupBusinessTest implements UserTest {
    @InjectMocks private UpdateJobGroupBusiness updateJobGroupBusiness;

    @Mock private JobGroupProvider jobGroupProvider;

    @Test
    void 직군_수정_테스트() {
        // given
        Dto dto =
                Dto.builder()
                        .loginUser(LoginUser.builder().userId(TEST_USER_ID).email(TEST_EMAIL).build())
                        .jobGroup(TEST_JOB_GROUP)
                        .build();

        // when
        updateJobGroupBusiness.execute(dto);

        // then
        verify(jobGroupProvider).updateJobGroup(any(), any(), any());
    }
}

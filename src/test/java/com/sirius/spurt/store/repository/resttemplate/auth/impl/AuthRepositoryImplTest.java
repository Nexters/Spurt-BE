package com.sirius.spurt.store.repository.resttemplate.auth.impl;

import static com.sirius.spurt.common.meta.ResultCode.AUTHENTICATION_FAILED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sirius.spurt.common.exception.GlobalException;
import com.sirius.spurt.store.repository.resttemplate.auth.playload.UserInfoPayload;
import com.sirius.spurt.test.UserTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class AuthRepositoryImplTest implements UserTest {
    @InjectMocks private AuthRepositoryImpl authRepository;

    @Mock private RestTemplate restTemplate;

    @Nested
    class 유저_정보_조회 {
        @Test
        void 유저_정보_조회_테스트() {
            // given
            UserInfoPayload userInfo =
                    UserInfoPayload.builder().userId(TEST_USER_ID).email(TEST_EMAIL).build();
            when(restTemplate.exchange(
                            anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(UserInfoPayload.class)))
                    .thenReturn(ResponseEntity.ok(userInfo));

            // when
            UserInfoPayload res = authRepository.getUserInfo("accessToken");

            // then
            verify(restTemplate)
                    .exchange(
                            anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(UserInfoPayload.class));
            assertThat(res.getUserId()).isEqualTo(userInfo.getUserId());
            assertThat(res.getEmail()).isEqualTo(userInfo.getEmail());
        }

        @Test
        void 유저_정보_조회_실패_테스트() {
            // given
            when(restTemplate.exchange(
                            anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(UserInfoPayload.class)))
                    .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

            // when
            GlobalException exception =
                    assertThrows(
                            GlobalException.class,
                            () -> {
                                authRepository.getUserInfo("accessToken");
                            });

            // then
            verify(restTemplate)
                    .exchange(
                            anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(UserInfoPayload.class));
            assertThat(exception.getResultCode()).isEqualTo(AUTHENTICATION_FAILED);
        }
    }
}

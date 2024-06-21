package com.sirius.spurt.store.provider.user.impl;

import static com.sirius.spurt.common.meta.ResultCode.NOT_EXIST_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sirius.spurt.common.exception.GlobalException;
import com.sirius.spurt.store.provider.user.vo.UserVo;
import com.sirius.spurt.store.repository.database.repository.UserRepository;
import com.sirius.spurt.test.UserTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserProviderImplTest implements UserTest {
    @InjectMocks private UserProviderImpl userProvider;

    @Mock private UserRepository userRepository;

    @Test
    void 유저_정보_조회_테스트() {
        // given
        when(userRepository.findByUserId(any())).thenReturn(TEST_USER);

        // when
        UserVo userVo = userProvider.getUserInfo(TEST_USER_ID);

        // then
        verify(userRepository).findByUserId(any());
        assertThat(userVo.getUserId()).isEqualTo(TEST_USER_ID);
        assertThat(userVo.getJobGroup()).isEqualTo(TEST_JOB_GROUP);
    }

    @Test
    void 유저_존재_확인_테스트() {
        // given
        when(userRepository.existsByUserId(any())).thenReturn(true);

        // when
        boolean isExistsUser = userProvider.checkUserExists(TEST_USER_ID);

        // then
        verify(userRepository).existsByUserId(any());
        assertThat(isExistsUser).isEqualTo(true);
    }

    @Nested
    class 유저_핀_등록_여부 {
        @Test
        void 핀_등록_여부_확인_성공_테스트() {
            // given
            when(userRepository.findByUserId(any())).thenReturn(TEST_USER);

            // when
            boolean hasPined = userProvider.checkHasPined(TEST_USER_ID);

            // then
            verify(userRepository).findByUserId(any());
            assertThat(hasPined).isEqualTo(true);
        }

        @Test
        void 핀_등록_여부_확인_실패_테스트() {
            // given
            when(userRepository.findByUserId(any())).thenReturn(null);

            // when
            GlobalException exception =
                    assertThrows(
                            GlobalException.class,
                            () -> {
                                userProvider.checkHasPined(TEST_USER_ID);
                            });

            // then
            verify(userRepository).findByUserId(any());
            assertThat(exception.getResultCode()).isEqualTo(NOT_EXIST_USER);
        }
    }

    @Nested
    class 유저_게시글_작성_여부 {
        @Test
        void 게시글_작성_여부_확인_성공_테스트() {
            // given
            when(userRepository.findByUserId(any())).thenReturn(TEST_USER);

            // when
            boolean hasPosted = userProvider.checkHasPosted(TEST_USER_ID);

            // then
            verify(userRepository).findByUserId(any());
            assertThat(hasPosted).isEqualTo(true);
        }

        @Test
        void 게시글_작성_여부_확인_실패_테스트() {
            // given
            when(userRepository.findByUserId(any())).thenReturn(null);

            // when
            GlobalException exception =
                    assertThrows(
                            GlobalException.class,
                            () -> {
                                userProvider.checkHasPosted(TEST_USER_ID);
                            });

            // then
            verify(userRepository).findByUserId(any());
            assertThat(exception.getResultCode()).isEqualTo(NOT_EXIST_USER);
        }
    }

    @Nested
    class 유저_삭제 {
        @Test
        void 유저_삭제_성공_테스트() {
            // given
            when(userRepository.findByUserId(any())).thenReturn(TEST_USER);

            // when
            userProvider.deleteUser(TEST_USER_ID);

            // then
            verify(userRepository).findByUserId(any());
        }

        @Test
        void 유저_삭제_실패_테스트() {
            // given
            when(userRepository.findByUserId(any())).thenReturn(null);

            // when
            GlobalException exception =
                    assertThrows(
                            GlobalException.class,
                            () -> {
                                userProvider.deleteUser(TEST_USER_ID);
                            });

            // then
            verify(userRepository).findByUserId(any());
            assertThat(exception.getResultCode()).isEqualTo(NOT_EXIST_USER);
        }
    }
}

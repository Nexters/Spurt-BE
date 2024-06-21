package com.sirius.spurt.store.provider.experience.impl;

import static com.sirius.spurt.common.meta.ResultCode.EXPERIENCE_THREE_SECONDS;
import static com.sirius.spurt.common.meta.ResultCode.NOT_EXIST_USER;
import static com.sirius.spurt.common.meta.ResultCode.NOT_EXPERIENCE_OWNER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sirius.spurt.common.exception.GlobalException;
import com.sirius.spurt.store.provider.experience.vo.ExperienceVo;
import com.sirius.spurt.store.repository.database.entity.BaseEntity;
import com.sirius.spurt.store.repository.database.repository.ExperienceRepository;
import com.sirius.spurt.store.repository.database.repository.UserRepository;
import com.sirius.spurt.test.ExperienceTest;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExperienceProviderImplTest implements ExperienceTest {
    @InjectMocks private ExperienceProviderImpl experienceProvider;

    @Mock private ExperienceRepository experienceRepository;
    @Mock private UserRepository userRepository;

    @Nested
    class 경험_저장 {
        @Test
        void 경험_저장_성공_테스트() {
            // given
            when(userRepository.findByUserId(any())).thenReturn(TEST_USER);
            when(experienceRepository.findTopByUserEntityOrderByCreateTimestampDesc(any()))
                    .thenReturn(null);
            when(experienceRepository.save(any())).thenReturn(TEST_EXPERIENCE);

            // when
            ExperienceVo experienceVo =
                    experienceProvider.saveExperience(
                            TEST_EXPERIENCE_TITLE,
                            TEST_EXPERIENCE_CONTENT,
                            TEST_EXPERIENCE_START_DATE_STRING,
                            TEST_EXPERIENCE_END_DATE_STRING,
                            TEST_EXPERIENCE_LINK,
                            TEST_USER_ID);

            // then
            verify(userRepository).findByUserId(any());
            verify(experienceRepository).findTopByUserEntityOrderByCreateTimestampDesc(any());
            verify(experienceRepository).save(any());
            assertThat(experienceVo.getTitle()).isEqualTo(TEST_EXPERIENCE_TITLE);
            assertThat(experienceVo.getContent()).isEqualTo(TEST_EXPERIENCE_CONTENT);
            assertThat(experienceVo.getStartDate()).isEqualTo(TEST_EXPERIENCE_START_DATE_STRING);
            assertThat(experienceVo.getEndDate()).isEqualTo(TEST_EXPERIENCE_END_DATE_STRING);
            assertThat(experienceVo.getLink()).isEqualTo(TEST_EXPERIENCE_LINK);
        }

        @Test
        void 경험_저장_실패_테스트_유저없음() {
            // given
            when(userRepository.findByUserId(any())).thenReturn(null);

            // when
            GlobalException exception =
                    assertThrows(
                            GlobalException.class,
                            () -> {
                                experienceProvider.saveExperience(
                                        TEST_EXPERIENCE_TITLE,
                                        TEST_EXPERIENCE_CONTENT,
                                        TEST_EXPERIENCE_START_DATE_STRING,
                                        TEST_EXPERIENCE_END_DATE_STRING,
                                        TEST_EXPERIENCE_LINK,
                                        TEST_USER_ID);
                            });

            // then
            verify(userRepository).findByUserId(any());
            verify(experienceRepository, times(0)).findTopByUserEntityOrderByCreateTimestampDesc(any());
            verify(experienceRepository, times(0)).save(any());
            assertThat(exception.getResultCode()).isEqualTo(NOT_EXIST_USER);
        }

        @Test
        void 경험_저장_실패_테스트_3초등록() {
            // given
            try {
                Field field = BaseEntity.class.getDeclaredField("createTimestamp");
                field.setAccessible(true);
                field.set(TEST_EXPERIENCE, Timestamp.valueOf(LocalDateTime.now()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            when(userRepository.findByUserId(any())).thenReturn(TEST_USER);
            when(experienceRepository.findTopByUserEntityOrderByCreateTimestampDesc(any()))
                    .thenReturn(TEST_EXPERIENCE);

            // when
            GlobalException exception =
                    assertThrows(
                            GlobalException.class,
                            () -> {
                                experienceProvider.saveExperience(
                                        TEST_EXPERIENCE_TITLE,
                                        TEST_EXPERIENCE_CONTENT,
                                        TEST_EXPERIENCE_START_DATE_STRING,
                                        TEST_EXPERIENCE_END_DATE_STRING,
                                        TEST_EXPERIENCE_LINK,
                                        TEST_USER_ID);
                            });

            // then
            verify(userRepository).findByUserId(any());
            verify(experienceRepository).findTopByUserEntityOrderByCreateTimestampDesc(any());
            verify(experienceRepository, times(0)).save(any());
            assertThat(exception.getResultCode()).isEqualTo(EXPERIENCE_THREE_SECONDS);
        }
    }

    @Nested
    class 경험_수정 {
        @Test
        void 경험_수정_성공_테스트() {
            // given
            when(experienceRepository.findByExperienceIdAndUserEntityUserId(any(), any()))
                    .thenReturn(TEST_EXPERIENCE);

            // when
            experienceProvider.updateExperience(
                    TEST_EXPERIENCE_ID,
                    TEST_EXPERIENCE_TITLE,
                    TEST_EXPERIENCE_CONTENT,
                    TEST_EXPERIENCE_START_DATE_STRING,
                    TEST_EXPERIENCE_END_DATE_STRING,
                    TEST_EXPERIENCE_LINK,
                    TEST_USER_ID);

            // then
            verify(experienceRepository).findByExperienceIdAndUserEntityUserId(any(), any());
            verify(experienceRepository).save(any());
        }

        @Test
        void 경험_수정_실패_테스트() {
            // given
            when(experienceRepository.findByExperienceIdAndUserEntityUserId(any(), any()))
                    .thenReturn(null);

            // when
            GlobalException exception =
                    assertThrows(
                            GlobalException.class,
                            () -> {
                                experienceProvider.updateExperience(
                                        TEST_EXPERIENCE_ID,
                                        TEST_EXPERIENCE_TITLE,
                                        TEST_EXPERIENCE_CONTENT,
                                        TEST_EXPERIENCE_START_DATE_STRING,
                                        TEST_EXPERIENCE_END_DATE_STRING,
                                        TEST_EXPERIENCE_LINK,
                                        TEST_USER_ID);
                            });

            // then
            verify(experienceRepository).findByExperienceIdAndUserEntityUserId(any(), any());
            verify(experienceRepository, times(0)).save(any());
            assertThat(exception.getResultCode()).isEqualTo(NOT_EXPERIENCE_OWNER);
        }
    }

    @Nested
    class 경험_삭제 {
        @Test
        void 경험_삭제_성공_테스트() {
            // given
            when(experienceRepository.findByExperienceIdAndUserEntityUserId(any(), any()))
                    .thenReturn(TEST_EXPERIENCE);

            // when
            experienceProvider.deleteExperience(TEST_EXPERIENCE_ID, TEST_USER_ID);

            // then
            verify(experienceRepository).findByExperienceIdAndUserEntityUserId(any(), any());
            verify(experienceRepository).deleteById(any());
        }

        @Test
        void 경험_삭제_실패_테스트() {
            // given
            when(experienceRepository.findByExperienceIdAndUserEntityUserId(any(), any()))
                    .thenReturn(null);

            // when
            GlobalException exception =
                    assertThrows(
                            GlobalException.class,
                            () -> {
                                experienceProvider.deleteExperience(TEST_EXPERIENCE_ID, TEST_USER_ID);
                            });

            // then
            verify(experienceRepository).findByExperienceIdAndUserEntityUserId(any(), any());
            verify(experienceRepository, times(0)).deleteById(any());
            assertThat(exception.getResultCode()).isEqualTo(NOT_EXPERIENCE_OWNER);
        }
    }
}

package com.sirius.spurt.store.repository.database.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sirius.spurt.common.config.QuerydslConfigTest;
import com.sirius.spurt.store.repository.database.entity.UserEntity;
import com.sirius.spurt.test.UserTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslConfigTest.class)
class UserRepositoryTest implements UserTest {
    @Autowired private UserRepository userRepository;

    @Test
    void 유저_존재_확인_테스트() {
        // given
        userRepository.save(TEST_USER);

        // when
        boolean isExistsUser = userRepository.existsByUserId(TEST_USER_ID);

        // then
        assertThat(isExistsUser).isEqualTo(true);
    }

    @Test
    void 유저_조회_테스트() {
        // given
        UserEntity savedUser = userRepository.save(TEST_USER);

        // when
        UserEntity userEntity = userRepository.findByUserId(TEST_USER_ID);

        // then
        assertThat(userEntity).isEqualTo(savedUser);
    }
}

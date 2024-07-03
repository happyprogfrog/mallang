package me.progfrog.mallang.socialmultiplication.repository;

import me.progfrog.mallang.socialmultiplication.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("대소문자를 구분하는지 확인")
    void testCaseSensitivity() {
        // given
        User user = new User("Frog");
        userRepository.save(user);

        // when
        Optional<User> foundUserLowerCase = userRepository.findByAlias("frog");
        Optional<User> foundUserExactCase = userRepository.findByAlias("Frog");

        // then
        assertThat(foundUserLowerCase).isEmpty(); // "frog"로 찾은 결과가 없는지 확인
        assertThat(foundUserExactCase).isNotEmpty(); // "Frog"로 찾은 결과가 있는지 확인
        assertThat(foundUserExactCase.get().getAlias()).isEqualTo("Frog");
    }
}
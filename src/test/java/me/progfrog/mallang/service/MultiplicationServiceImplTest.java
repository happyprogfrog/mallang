package me.progfrog.mallang.service;

import me.progfrog.mallang.domain.Multiplication;
import me.progfrog.mallang.domain.MultiplicationResultAttempt;
import me.progfrog.mallang.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MultiplicationServiceImplTest {

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @InjectMocks
    private MultiplicationServiceImpl multiplicationServiceImpl;

    @Test
    @DisplayName("랜덤한 인수에 대한 계산 결과가 잘 나오는 지 확인")
    void createRandomMultiplication() {
        // given (randomGeneratorService가 처음에 50, 나중에 30을 반환하도록 설정)
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

        // when
        Multiplication multiplication = multiplicationServiceImpl.createRandomMultiplication();

        // then
        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);
        // assertThat(multiplication.getResult()).isEqualTo(1500);
    }

    @Test
    @DisplayName("계산 결과가 맞으면 true 반환")
    void checkAttempt_1() {
        // given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("Frog");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000);

        // when
        boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

        // then
        assertThat(attemptResult).isTrue();
    }

    @Test
    @DisplayName("계산 결과가 틀리면 false 반환")
    void checkAttempt_2() {
        // given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("Frog");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010);

        // when
        boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

        // then
        assertThat(attemptResult).isFalse();
    }
}
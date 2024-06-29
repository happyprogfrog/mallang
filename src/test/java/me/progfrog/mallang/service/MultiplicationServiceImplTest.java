package me.progfrog.mallang.service;

import me.progfrog.mallang.domain.Multiplication;
import me.progfrog.mallang.domain.MultiplicationResultAttempt;
import me.progfrog.mallang.domain.User;
import me.progfrog.mallang.repository.MultiplicationRepository;
import me.progfrog.mallang.repository.MultiplicationResultAttemptRepository;
import me.progfrog.mallang.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MultiplicationServiceImplTest {

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @Mock
    private MultiplicationResultAttemptRepository attemptRepository;

    @Mock
    private MultiplicationRepository multiplicationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MultiplicationServiceImpl multiplicationServiceImpl;

    private final int factorA = 50;
    private final int factorB = 60;
    private User user;
    private Multiplication multiplication;
    private MultiplicationResultAttempt attempt;

    @BeforeEach
    void setUp() {
        user = new User("Frog");
        multiplication = new Multiplication(factorA, factorB);
        attempt = new MultiplicationResultAttempt(user, multiplication, 3000, false);
    }

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
        given(userRepository.findByAlias(anyString())).willReturn(Optional.of(user));
        given(multiplicationRepository.findByFactorAAndFactorB(anyInt(), anyInt())).willReturn(Optional.of(multiplication));
        given(attemptRepository.findByUserIdAndMultiplicationId(user.getId(), multiplication.getId())).willReturn(Optional.empty());

        MultiplicationResultAttempt correctAttempt = new MultiplicationResultAttempt(user, multiplication, factorA * factorB, false);

        // when
        boolean result = multiplicationServiceImpl.checkAttempt(correctAttempt);

        // then
        assertThat(result).isTrue();
        verify(attemptRepository).save(any(MultiplicationResultAttempt.class));
    }

    @Test
    @DisplayName("계산 결과가 틀리면 false 반환")
    void checkAttempt_2() {
        // given
        given(userRepository.findByAlias(anyString())).willReturn(Optional.of(user));
        given(multiplicationRepository.findByFactorAAndFactorB(anyInt(), anyInt())).willReturn(Optional.of(multiplication));
        given(attemptRepository.findByUserIdAndMultiplicationId(user.getId(), multiplication.getId())).willReturn(Optional.empty());

        MultiplicationResultAttempt wrongAttempt = new MultiplicationResultAttempt(user, multiplication, 3010, false);

        // when
        boolean result = multiplicationServiceImpl.checkAttempt(wrongAttempt);

        // then
        assertThat(result).isFalse();
        verify(attemptRepository).save(any(MultiplicationResultAttempt.class));
    }

    @Test
    @DisplayName("이미 채점된 상태로 답안을 보낼 때 예외 발생")
    public void checkAttempt_3() {
        // given
        attempt = new MultiplicationResultAttempt(user, multiplication, 3010, true);

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            multiplicationServiceImpl.checkAttempt(attempt);
        }, "채점된 상태로 보낼 수 없습니다!");
    }

    @Test
    @DisplayName("이전에 답안을 제출한 이력이 있으면 예외가 발생")
    public void checkAttempt_4() {
        // given
        given(userRepository.findByAlias(anyString())).willReturn(Optional.of(user));
        given(multiplicationRepository.findByFactorAAndFactorB(anyInt(), anyInt())).willReturn(Optional.of(multiplication));
        given(attemptRepository.findByUserIdAndMultiplicationId(user.getId(), multiplication.getId())).willReturn(Optional.of(attempt));

        // when, then
        assertThatThrownBy(() -> multiplicationServiceImpl.checkAttempt(attempt))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("이미 제출된 이력이 있는 답안 입니다!");
    }
}
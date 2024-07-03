package me.progfrog.mallang.socialmultiplication.service;

import me.progfrog.mallang.socialmultiplication.domain.Multiplication;
import me.progfrog.mallang.socialmultiplication.domain.MultiplicationResultAttempt;
import me.progfrog.mallang.socialmultiplication.domain.User;
import me.progfrog.mallang.socialmultiplication.event.EventDispatcher;
import me.progfrog.mallang.socialmultiplication.event.MultiplicationSolvedEvent;
import me.progfrog.mallang.socialmultiplication.repository.MultiplicationRepository;
import me.progfrog.mallang.socialmultiplication.repository.MultiplicationResultAttemptRepository;
import me.progfrog.mallang.socialmultiplication.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
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

    @Mock
    private EventDispatcher eventDispatcher;

    @InjectMocks
    private MultiplicationServiceImpl multiplicationServiceImpl;

    private final int factorA = 50;
    private final int factorB = 60;
    private User user;
    private Multiplication multiplication;

    @BeforeEach
    void setUp() {
        user = new User("Frog");
        multiplication = new Multiplication(factorA, factorB);
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

        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000, false);
        MultiplicationResultAttempt correctAttempt = new MultiplicationResultAttempt(user, multiplication, factorA * factorB, false);
        MultiplicationSolvedEvent event = new MultiplicationSolvedEvent(attempt.getId(), attempt.getUser().getId(), true);

        // when
        boolean result = multiplicationServiceImpl.checkAttempt(correctAttempt);

        // then
        assertThat(result).isTrue();
        verify(attemptRepository).save(any(MultiplicationResultAttempt.class));
        verify(eventDispatcher).send(eq(event));
    }

    @Test
    @DisplayName("계산 결과가 틀리면 false 반환")
    void checkAttempt_2() {
        // given
        given(userRepository.findByAlias(anyString())).willReturn(Optional.of(user));
        given(multiplicationRepository.findByFactorAAndFactorB(anyInt(), anyInt())).willReturn(Optional.of(multiplication));

        MultiplicationResultAttempt wrongAttempt = new MultiplicationResultAttempt(user, multiplication, 3010, false);
        MultiplicationSolvedEvent event = new MultiplicationSolvedEvent(wrongAttempt.getId(), wrongAttempt.getUser().getId(), false);

        // when
        boolean result = multiplicationServiceImpl.checkAttempt(wrongAttempt);

        // then
        assertThat(result).isFalse();
        verify(attemptRepository).save(any(MultiplicationResultAttempt.class));
        verify(eventDispatcher).send(eq(event));
    }

    @Test
    @DisplayName("이미 채점된 상태로 답안을 보낼 때 예외 발생")
    public void checkAttempt_3() {
        // given
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010, true);

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            multiplicationServiceImpl.checkAttempt(attempt);
        }, "채점된 상태로 보낼 수 없습니다!");
    }

    @Test
    @DisplayName("사용자의 최근 답안을 보여주기")
    public void retrieveStatsTest() {
        // given
        MultiplicationResultAttempt attempt1 = new MultiplicationResultAttempt(user, multiplication, 3010, false);
        MultiplicationResultAttempt attempt2 = new MultiplicationResultAttempt(user, multiplication, 3051, false);
        List<MultiplicationResultAttempt> latestAttempts = Lists.newArrayList(attempt1, attempt2);

        doReturn(latestAttempts).when(attemptRepository).findTop5ByUserAliasOrderByIdDesc(user.getAlias());

        // when
        List<MultiplicationResultAttempt> latestAttemptsResult = multiplicationServiceImpl.getStatsForUser(user.getAlias());

        // then
        assertThat(latestAttemptsResult).isEqualTo(latestAttempts);
    }

}
package me.progfrog.mallang.gamification.service;

import me.progfrog.mallang.gamification.client.MultiplicationResultAttemptClient;
import me.progfrog.mallang.gamification.client.dto.MultiplicationResultAttempt;
import me.progfrog.mallang.gamification.domain.Badge;
import me.progfrog.mallang.gamification.domain.BadgeCard;
import me.progfrog.mallang.gamification.domain.GameStats;
import me.progfrog.mallang.gamification.domain.ScoreCard;
import me.progfrog.mallang.gamification.repository.BadgeCardRepository;
import me.progfrog.mallang.gamification.repository.ScoreCardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @InjectMocks
    private GameServiceImpl gameService;

    @Mock
    private ScoreCardRepository scoreCardRepository;

    @Mock
    private BadgeCardRepository badgeCardRepository;

    @Mock
    private MultiplicationResultAttemptClient multiplicationClient;

    @Test
    @DisplayName("첫 시도에는 첫 번째 정답 배지를 획득")
    void processFirstCorrectAttemptTest() {
        // given
        Long userId = 1L;
        Long attemptId = 8L;
        int totalScore = 5;
        ScoreCard scoreCard = new ScoreCard(userId, attemptId);
        given(scoreCardRepository.getTotalScoreForUser(userId))
                .willReturn(totalScore);
        // 이 리파지토리는 방금 얻은 점수 카드를 반환
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
                .willReturn(Collections.singletonList(scoreCard));
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(Collections.emptyList());

        // 기본적으로 행운의 숫자를 포함하지 않는 답안
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(
                "Frog", 20, 70, 1400, true);
        given(multiplicationClient.retrieveMultiplicationResultAttemptById(anyLong()))
                .willReturn(attempt);

        // when
        GameStats iteration = gameService.processNewAttemptForUser(userId, attemptId, true);

        // then - 점수 카드 하나와 첫 번째 정답 배지를 획득
        assertThat(iteration.getScore()).isEqualTo(scoreCard.getScore());
        assertThat(iteration.getBadges()).containsOnly(Badge.FIRST_WON);
    }

    @Test
    @DisplayName("정답 10개는 동배지를 지급")
    void processCorrectAttemptForScoreBadgeTest_1() {
        // given
        Long userId = 1L;
        Long attemptId = 29L;
        int totalScore = 100;
        BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
        given(scoreCardRepository.getTotalScoreForUser(userId))
                .willReturn(totalScore);
        // 이 리파지토리는 방금 얻은 점수 카드를 반환
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
                .willReturn(createNScoreCards(10, userId));

        // 첫 번째 정답 배지는 이미 존재
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(Collections.singletonList(firstWonBadge));

        // 기본적으로 행운의 숫자를 포함하지 않는 답안
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(
                "Frog", 20, 70, 1400, true);
        given(multiplicationClient.retrieveMultiplicationResultAttemptById(anyLong()))
                .willReturn(attempt);

        // when
        GameStats iteration = gameService.processNewAttemptForUser(userId, attemptId, true);

        // then - 점수 카드 하나와 동배지를 획득
        assertThat(iteration.getScore()).isEqualTo(ScoreCard.DEFAULT_SCORE);
        assertThat(iteration.getBadges()).contains(Badge.BRONZE_MULTIPLICATOR);
    }

    @Test
    @DisplayName("정답 25개는 은배지를 지급")
    void processCorrectAttemptForScoreBadgeTest_2() {
        // given
        Long userId = 1L;
        Long attemptId = 29L;
        int totalScore = 500;
        BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
        BadgeCard bronzeBadge = new BadgeCard(userId, Badge.BRONZE_MULTIPLICATOR);
        given(scoreCardRepository.getTotalScoreForUser(userId))
                .willReturn(totalScore);
        // 이 리파지토리는 방금 얻은 점수 카드를 반환
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
                .willReturn(createNScoreCards(25, userId));

        // 정답 배지와 동배지는 이미 존재
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(Arrays.asList(firstWonBadge, bronzeBadge));

        // 기본적으로 행운의 숫자를 포함하지 않는 답안
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(
                "Frog", 20, 70, 1400, true);
        given(multiplicationClient.retrieveMultiplicationResultAttemptById(anyLong()))
                .willReturn(attempt);

        // when
        GameStats iteration = gameService.processNewAttemptForUser(userId, attemptId, true);

        // then - 점수 카드 하나와 은배지를 획득
        assertThat(iteration.getScore()).isEqualTo(ScoreCard.DEFAULT_SCORE);
        assertThat(iteration.getBadges()).contains(Badge.SILVER_MULTIPLICATOR);
    }

    @Test
    @DisplayName("정답 50개는 금배지를 지급")
    void processCorrectAttemptForScoreBadgeTest_3() {
        // given
        Long userId = 1L;
        Long attemptId = 29L;
        int totalScore = 999;
        BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
        BadgeCard bronzeBadge = new BadgeCard(userId, Badge.BRONZE_MULTIPLICATOR);
        BadgeCard silverBadge = new BadgeCard(userId, Badge.SILVER_MULTIPLICATOR);
        given(scoreCardRepository.getTotalScoreForUser(userId))
                .willReturn(totalScore);
        // 이 리파지토리는 방금 얻은 점수 카드를 반환
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
                .willReturn(createNScoreCards(50, userId));

        // 첫 번째 정답 배지는 이미 존재
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(Arrays.asList(firstWonBadge, bronzeBadge, silverBadge));

        // 기본적으로 행운의 숫자를 포함하지 않는 답안
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(
                "Frog", 20, 70, 1400, true);
        given(multiplicationClient.retrieveMultiplicationResultAttemptById(anyLong()))
                .willReturn(attempt);

        // when
        GameStats iteration = gameService.processNewAttemptForUser(userId, attemptId, true);

        // then - 점수 카드 하나와 동배지를 획득
        assertThat(iteration.getScore()).isEqualTo(ScoreCard.DEFAULT_SCORE);
        assertThat(iteration.getBadges()).contains(Badge.GOLD_MULTIPLICATOR);
    }

    @Test
    @DisplayName("숫자 42가 포함되어 있으면 행운의 배지 지급")
    void processCorrectAttemptForLuckyNumberBadgeTest() {
        // given
        Long userId = 1L;
        Long attemptId = 29L;
        int totalScore = 5;
        BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
        given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore);
        // 이 리파지토리는 방금 얻은 점수 카드를 반환
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId)).willReturn(createNScoreCards(1, userId));
        // 첫 번째 정답 배지는 이미 존재
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId)).willReturn(Collections.singletonList(firstWonBadge));
        // 행운의 숫자가 포함된 답안
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(
                "Frog", 42, 10, 420, true);
        given(multiplicationClient.retrieveMultiplicationResultAttemptById(attemptId))
                .willReturn(attempt);

        // when
        GameStats iteration = gameService.processNewAttemptForUser(userId, attemptId, true);

        // then - 점수 카드 하나와 행운의 숫자 배지를 획득
        assertThat(iteration.getScore()).isEqualTo(ScoreCard.DEFAULT_SCORE);
        assertThat(iteration.getBadges()).contains(Badge.LUCKY_NUMBER);
    }

    @Test
    @DisplayName("하나도 점수를 얻지 못함")
    void processWrongAttemptTest() {
        // given
        Long userId = 1L;
        Long attemptId = 8L;

        // when
        GameStats iteration = gameService.processNewAttemptForUser(userId, attemptId, false);

        // then - 하나도 점수를 얻지 못함
        assertThat(iteration.getScore()).isEqualTo(0);
        assertThat(iteration.getBadges()).isEmpty();
    }

    @Test
    @DisplayName("사용자 통계 가져오기")
    void retrieveStatsForUserTest() {
        // given
        Long userId = 1L;
        int totalScore = 1000;
        BadgeCard badgeCard = new BadgeCard(userId, Badge.SILVER_MULTIPLICATOR);
        given(scoreCardRepository.getTotalScoreForUser(userId))
                .willReturn(totalScore);
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(Collections.singletonList(badgeCard));

        // when
        GameStats stats = gameService.retrieveStatsForUser(userId);

        // then
        assertThat(stats.getScore()).isEqualTo(totalScore);
        assertThat(stats.getBadges()).containsOnly(Badge.SILVER_MULTIPLICATOR);
    }

    private List<ScoreCard> createNScoreCards(int n, Long userId) {
        return IntStream.range(0, n)
                .mapToObj(i -> new ScoreCard(userId, (long) i))
                .toList();
    }
}
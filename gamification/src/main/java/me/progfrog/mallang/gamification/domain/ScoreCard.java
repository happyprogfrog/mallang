package me.progfrog.mallang.gamification.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * 점수와 답안을 연결하는 클래스
 * 사용자와 점수가 등록된 시간의 타임스탬프를 포함
 */
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public class ScoreCard {

    // 명시되지 않은 경우 이 카드에 할당되는 기본 점수
    public static final int DEFAULT_SCORE = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private final Long cardId;

    private final Long userId;

    private final Long attemptId;

    @Column(name = "score_ts")
    private final long scoreTimestamp;

    private final int score;

    public ScoreCard(final Long userId, final Long attemptId) {
        this(null, userId, attemptId, System.currentTimeMillis(), DEFAULT_SCORE);
    }
}

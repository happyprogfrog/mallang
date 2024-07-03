package me.progfrog.mallang.gamification.domain;

import lombok.*;

/**
 * 리더보드 내 위치를 나타내는 객체
 * 사용자와 전체 점수를 연결
 */
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class LeaderBoardRow {

    private final Long userId;
    private final Long totalScore;
}

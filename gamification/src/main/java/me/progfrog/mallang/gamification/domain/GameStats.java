package me.progfrog.mallang.gamification.domain;

import lombok.*;

import java.util.Collections;
import java.util.List;

/**
 * 한 번 혹은 여러 번의 게임 결과를 포함하는 객체
 * {@link ScoreCard} 객체와 {@link BadgeCard}로 이뤄짐
 *
 * 게임 한 번에 변경된 내용 또는 점수와 배지 전체를 나타낼 때 사용됨
 */
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class GameStats {

    private final Long userId;
    private final int score;
    private final List<Badge> badges;

    /**
     * 빈 인스턴스(0점과 배지 없는 상태)를 만들기 위한 팩토리 메서드
     * @param userId 사용자 ID
     * @return {@link GameStats} 객체(0점과 배지 없는 상태)
     */
    public static GameStats emptyStats(final Long userId) {
        return new GameStats(userId, 0, Collections.emptyList());
    }

    /**
     * @return 수정 불가능한 배지 카드 리스트의 뷰
     */
    public List<Badge> getBadges() {
        return Collections.unmodifiableList(badges);
    }
}

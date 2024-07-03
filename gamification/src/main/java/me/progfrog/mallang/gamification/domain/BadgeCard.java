package me.progfrog.mallang.gamification.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * 배지와 사용자를 연결하는 클래스
 * 사용자가 배지를 획득한 순간의 타임스탬프를 포함
 */
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class BadgeCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private final Long badgeId;

    private final Long userId;
    private final long badgeTimestamp;
    private final Badge badge;

    public BadgeCard(final Long userId, final Badge badge) {
        this(null, userId, System.currentTimeMillis(), badge);
    }
}

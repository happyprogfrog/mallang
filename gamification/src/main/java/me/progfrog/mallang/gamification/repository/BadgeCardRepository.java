package me.progfrog.mallang.gamification.repository;

import me.progfrog.mallang.gamification.domain.BadgeCard;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BadgeCardRepository extends CrudRepository<BadgeCard, Long> {

    /**
     * 주어진 사용자의 배지 카드를 모두 조회
     * @param userId BadgeCard를 조회하고자 하는 사용자의 ID
     * @return 최근 획득한 순으로 정렬된 BadgeCard 리스트
     */
    List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(final Long userId);
}

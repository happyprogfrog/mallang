package me.progfrog.mallang.gamification.service;

import lombok.RequiredArgsConstructor;
import me.progfrog.mallang.gamification.repository.BadgeCardRepository;
import me.progfrog.mallang.gamification.repository.ScoreCardRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("test")
@RequiredArgsConstructor
@Service
class AdminServiceImpl implements AdminService {

    private final BadgeCardRepository badgeCardRepository;
    private final ScoreCardRepository scoreCardRepository;

    @Override
    public void deleteDatabaseContents() {
        scoreCardRepository.deleteAll();
        badgeCardRepository.deleteAll();
    }
}
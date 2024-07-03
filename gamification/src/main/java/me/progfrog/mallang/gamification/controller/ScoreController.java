package me.progfrog.mallang.gamification.controller;

import lombok.RequiredArgsConstructor;
import me.progfrog.mallang.gamification.domain.ScoreCard;
import me.progfrog.mallang.gamification.service.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 게임화 사용자 통계 서비스의 REST API를 구현하는 클래스
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/scores")
public class ScoreController {

    private final GameService gameService;

    @GetMapping("/{attemptId}")
    public ScoreCard getScoreForAttempt(@PathVariable("attemptId") final Long attemptId) {
        return gameService.getScoreForAttempt(attemptId);
    }
}

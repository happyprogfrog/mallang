package me.progfrog.mallang.socialmultiplication.controller;

import lombok.extern.slf4j.Slf4j;
import me.progfrog.mallang.socialmultiplication.domain.MultiplicationResultAttempt;
import me.progfrog.mallang.socialmultiplication.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/results")
@Slf4j
final class MultiplicationResultAttemptController {

    private final MultiplicationService multiplicationService;
    private final int serverPort;

    @Autowired
    public MultiplicationResultAttemptController(final MultiplicationService multiplicationService,
                                    @Value("${server.port}") int serverPort) {
        this.multiplicationService = multiplicationService;
        this.serverPort = serverPort;
    }

    @PostMapping
    ResponseEntity<MultiplicationResultAttempt> postResult(@RequestBody MultiplicationResultAttempt attempt) {
        MultiplicationResultAttempt resultAttempt = multiplicationService.checkAttempt(attempt);
        return ResponseEntity.ok(resultAttempt);
    }

    @GetMapping
    ResponseEntity<List<MultiplicationResultAttempt>> getStatistics(@RequestParam("alias") String alias) {
        return ResponseEntity.ok(multiplicationService.getStatsForUser(alias));
    }

    @GetMapping("/{resultId}")
    ResponseEntity<MultiplicationResultAttempt> getResultById(final @PathVariable("resultId") Long resultId) {
        log.info("조회 결과 {} 를 가져온 서버 @ {}", resultId, serverPort);
        return ResponseEntity.ok(multiplicationService.getResultById(resultId));
    }
}

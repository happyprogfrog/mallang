package me.progfrog.mallang.controller;

import lombok.RequiredArgsConstructor;
import me.progfrog.mallang.domain.MultiplicationResultAttempt;
import me.progfrog.mallang.service.MultiplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/results")
final class MultiplicationResultAttemptController {

    private final MultiplicationService multiplicationService;

    @PostMapping
    ResponseEntity<MultiplicationResultAttempt> postResult(@RequestBody MultiplicationResultAttempt attempt) {
        boolean correct = multiplicationService.checkAttempt(attempt);
        MultiplicationResultAttempt attemptCopy = new MultiplicationResultAttempt(
                attempt.getUser(),
                attempt.getMultiplication(),
                attempt.getResultAttempt(),
                correct
        );
        return ResponseEntity.ok(attemptCopy);
    }
}

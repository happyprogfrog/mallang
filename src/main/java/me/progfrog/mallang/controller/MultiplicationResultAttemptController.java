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
    ResponseEntity<ResultResponse> postResult(@RequestBody MultiplicationResultAttempt attempt) {
        return ResponseEntity.ok(new ResultResponse(multiplicationService.checkAttempt(attempt)));
    }

    record ResultResponse(
            boolean correct
    ) {
    }
}

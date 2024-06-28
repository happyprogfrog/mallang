package me.progfrog.mallang.controller;

import lombok.RequiredArgsConstructor;
import me.progfrog.mallang.domain.Multiplication;
import me.progfrog.mallang.service.MultiplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/multiplications")
final class MultiplicationController {

    private final MultiplicationService multiplicationService;

    @GetMapping("/random")
    public Multiplication getRandomMultiplication() {
        return multiplicationService.createRandomMultiplication();
    }
}

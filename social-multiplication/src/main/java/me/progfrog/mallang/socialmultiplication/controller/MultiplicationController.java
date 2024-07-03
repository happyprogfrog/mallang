package me.progfrog.mallang.socialmultiplication.controller;

import lombok.extern.slf4j.Slf4j;
import me.progfrog.mallang.socialmultiplication.domain.Multiplication;
import me.progfrog.mallang.socialmultiplication.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/multiplications")
@Slf4j
final class MultiplicationController {

    private final MultiplicationService multiplicationService;
    private final int serverPort;

    @Autowired
    public MultiplicationController(final MultiplicationService multiplicationService,
                                    @Value("${server.port}") int serverPort) {
        this.multiplicationService = multiplicationService;
        this.serverPort = serverPort;
    }

    @GetMapping("/random")
    public Multiplication getRandomMultiplication() {
        log.info("무작위 곱셈이 생성된 서버 @ {}", serverPort);
        return multiplicationService.createRandomMultiplication();
    }
}

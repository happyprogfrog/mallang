package me.progfrog.mallang.gamification.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import me.progfrog.mallang.gamification.client.dto.MultiplicationResultAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Multiplication 마이크로서비스와 REST로 연결하기 위한
 * MultiplicationResultAttemptClient 인터페이스 구현체
 */
@Component
@Slf4j
public class MultiplicationResultAttemptClientImpl implements MultiplicationResultAttemptClient {

    private final RestTemplate restTemplate;
    private final String multiplicationHost;

    @Autowired
    public MultiplicationResultAttemptClientImpl(
            final RestTemplate restTemplate,
            @Value("${multiplicationHost}") final String multiplicationHost) {
        this.restTemplate = restTemplate;
        this.multiplicationHost = multiplicationHost;
    }

    @CircuitBreaker(name = "retrieveMultiplicationResultAttemptById", fallbackMethod = "defaultResult")
    @Override
    public MultiplicationResultAttempt retrieveMultiplicationResultAttemptById(final Long multiplicationResultAttemptId) {
        return restTemplate.getForObject(
                multiplicationHost + "/results/" + multiplicationResultAttemptId,
                MultiplicationResultAttempt.class);
    }

    private MultiplicationResultAttempt defaultResult(final Long multiplicationResultAttemptId, Throwable throwable) {
        log.info("defaultResult: Fallback method called due to exception: {}", throwable.toString());
        return new MultiplicationResultAttempt("fakeAlias", 10, 10, 100, true);
    }
}

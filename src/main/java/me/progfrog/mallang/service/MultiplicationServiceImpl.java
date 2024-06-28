package me.progfrog.mallang.service;

import lombok.RequiredArgsConstructor;
import me.progfrog.mallang.domain.Multiplication;
import me.progfrog.mallang.domain.MultiplicationResultAttempt;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Service
public class MultiplicationServiceImpl implements MultiplicationService {

    private final RandomGeneratorService randomGeneratorService;

    @Override
    public Multiplication createRandomMultiplication() {
        int factorA = randomGeneratorService.generateRandomFactor();
        int factorB = randomGeneratorService.generateRandomFactor();
        return new Multiplication(factorA, factorB);
    }

    @Override
    public boolean checkAttempt(final MultiplicationResultAttempt attempt) {
        boolean correct = attempt.getResultAttempt()
                == attempt.getMultiplication().getFactorA() * attempt.getMultiplication().getFactorB();

        // 조작된 답안을 방지
        Assert.isTrue(!attempt.isCorrect(), "채점된 상태로 보낼 수 없습니다!");

        return correct;
    }
}

package me.progfrog.mallang.service;

import lombok.RequiredArgsConstructor;
import me.progfrog.mallang.domain.Multiplication;
import org.springframework.stereotype.Service;

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
}

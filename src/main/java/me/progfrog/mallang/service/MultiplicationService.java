package me.progfrog.mallang.service;

import me.progfrog.mallang.domain.Multiplication;
import me.progfrog.mallang.domain.MultiplicationResultAttempt;

public interface MultiplicationService {

    /**
     * @return 무작위 인수를 담은 {@link Multiplication} 객체
     */
    Multiplication createRandomMultiplication();

    /**
     * @return 곱셈 계산 결과가 맞으면 true, 아니면 false
     */
    boolean checkAttempt(final MultiplicationResultAttempt resultAttempt);
}

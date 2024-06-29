package me.progfrog.mallang.service;

import me.progfrog.mallang.domain.Multiplication;
import me.progfrog.mallang.domain.MultiplicationResultAttempt;

import java.util.List;

public interface MultiplicationService {

    /**
     * @return 무작위 인수를 담은 {@link Multiplication} 객체
     */
    Multiplication createRandomMultiplication();

    /**
     * @return 곱셈 계산 결과가 맞으면 true, 아니면 false
     */
    boolean checkAttempt(final MultiplicationResultAttempt resultAttempt);

    /**
     * 해당 사용자의 통계 정보를 조회
     *
     * @param userAlias 해당 사용자의 닉네임
     * @return 해당 사용자가 전에 제출한 답안 객체 {@link MultiplicationResultAttempt}의 리스트
     */
    List<MultiplicationResultAttempt> getStatsForUser(String userAlias);
}

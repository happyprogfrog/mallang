package me.progfrog.mallang.domain;

import lombok.Getter;

@Getter
public class Multiplication {

    // 인수
    private int factorA;
    private int factorB;

    // A * B의 결과
    private int result;

    public Multiplication(int factorA, int factorB) {
        this.factorA = factorA;
        this.factorB = factorB;
        this.result = factorA * factorB;
    }

    @Override
    public String toString() {
        return "Multiplication{factorA=%d, factorB=%d, result(A*B)=%d}".formatted(factorA, factorB, result);
    }
}

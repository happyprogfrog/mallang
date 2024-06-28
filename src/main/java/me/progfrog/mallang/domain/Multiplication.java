package me.progfrog.mallang.domain;

import lombok.*;

/**
 * 애플리케이션에서 곱셈을 나타내는 클래스(a * b)
 */
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class Multiplication {

    // 인수
    private final int factorA;
    private final int factorB;
}

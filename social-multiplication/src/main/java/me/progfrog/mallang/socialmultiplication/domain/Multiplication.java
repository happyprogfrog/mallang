package me.progfrog.mallang.socialmultiplication.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * 애플리케이션에서 곱셈을 나타내는 클래스(a * b)
 */
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "multiplication", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"factorA", "factorB"})
})
public final class Multiplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "multiplication_id")
    private Long id;

    // 인수
    @Column(nullable = false)
    private final int factorA;

    @Column(nullable = false)
    private final int factorB;
}

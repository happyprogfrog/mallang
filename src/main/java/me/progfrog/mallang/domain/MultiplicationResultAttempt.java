package me.progfrog.mallang.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * {@link User}가 {@link Multiplication}을 계산한 답안을 정의한 클래스
 */
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "multiplication_result_attempt")
public final class MultiplicationResultAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = false)
    private final User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "multiplication_id", nullable = false)
    private final Multiplication multiplication;

    @Column(nullable = false)
    private final int resultAttempt;

    @Column(nullable = false)
    private final boolean correct;
}

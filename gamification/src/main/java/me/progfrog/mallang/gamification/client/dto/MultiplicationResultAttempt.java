package me.progfrog.mallang.gamification.client.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import me.progfrog.mallang.gamification.client.MultiplicationResultAttemptDeserializer;

/**
 * User가 곱셈을 푼 답안을 정의한 클래스
 */
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@JsonDeserialize(using = MultiplicationResultAttemptDeserializer.class)
public final class MultiplicationResultAttempt {

    private final String userAlias;
    private final int multiplicationFactorA;
    private final int multiplicationFactorB;
    private final int resultAttempt;
    private final boolean correct;
}

package me.progfrog.mallang.domain;

import lombok.*;

/**
 * 사용자 정보를 저장하는 클래스
 */
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class User {

    private final String alias;
}

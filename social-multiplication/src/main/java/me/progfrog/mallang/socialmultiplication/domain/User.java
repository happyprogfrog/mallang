package me.progfrog.mallang.socialmultiplication.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * 사용자 정보를 저장하는 클래스
 */
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "users")
public final class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private final String alias;
}

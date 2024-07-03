package me.progfrog.mallang.socialmultiplication.controller;

import lombok.RequiredArgsConstructor;
import me.progfrog.mallang.socialmultiplication.domain.User;
import me.progfrog.mallang.socialmultiplication.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable("userId") final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("요청한 userId [%d] 는 존재하지 않습니다.".formatted(userId)));
    }
}

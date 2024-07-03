package me.progfrog.mallang.socialmultiplication.service;

import lombok.RequiredArgsConstructor;
import me.progfrog.mallang.socialmultiplication.repository.MultiplicationRepository;
import me.progfrog.mallang.socialmultiplication.repository.MultiplicationResultAttemptRepository;
import me.progfrog.mallang.socialmultiplication.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("test")
@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final MultiplicationRepository multiplicationRepository;
    private final MultiplicationResultAttemptRepository attemptRepository;
    private final UserRepository userRepository;

    @Override
    public void deleteDatabaseContents() {
        attemptRepository.deleteAll();
        multiplicationRepository.deleteAll();
        userRepository.deleteAll();
    }
}
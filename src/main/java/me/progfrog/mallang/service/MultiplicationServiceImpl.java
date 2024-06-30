package me.progfrog.mallang.service;

import lombok.RequiredArgsConstructor;
import me.progfrog.mallang.domain.Multiplication;
import me.progfrog.mallang.domain.MultiplicationResultAttempt;
import me.progfrog.mallang.domain.User;
import me.progfrog.mallang.event.EventDispatcher;
import me.progfrog.mallang.event.MultiplicationSolvedEvent;
import me.progfrog.mallang.repository.MultiplicationRepository;
import me.progfrog.mallang.repository.MultiplicationResultAttemptRepository;
import me.progfrog.mallang.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MultiplicationServiceImpl implements MultiplicationService {

    private final RandomGeneratorService randomGeneratorService;
    private final MultiplicationRepository multiplicationRepository;
    private final MultiplicationResultAttemptRepository attemptRepository;
    private final UserRepository userRepository;
    private final EventDispatcher eventDispatcher;

    @Override
    public Multiplication createRandomMultiplication() {
        int factorA = randomGeneratorService.generateRandomFactor();
        int factorB = randomGeneratorService.generateRandomFactor();
        return new Multiplication(factorA, factorB);
    }

    @Transactional
    @Override
    public boolean checkAttempt(final MultiplicationResultAttempt attempt) {
        // 조작된 답안을 방지
        Assert.isTrue(!attempt.isCorrect(), "채점된 상태로 보낼 수 없습니다!");

        // 해당 닉네임의 사용자가 존재하는 지 확인
        Optional<User> user = userRepository.findByAlias(attempt.getUser().getAlias());

        // 이미 존재하는 문제인지 확인
        Multiplication multiplication = findOrCreateMultiplication(
                attempt.getMultiplication().getFactorA(), attempt.getMultiplication().getFactorB());

        // 답안을 채점
        boolean correct = attempt.getResultAttempt()
                == multiplication.getFactorA() * multiplication.getFactorB();

        MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(
                user.orElse(attempt.getUser()),
                multiplication,
                attempt.getResultAttempt(),
                correct
        );

        // 답안을 저장
        attemptRepository.save(checkedAttempt);

        // 이벤트로 결과를 전송
        eventDispatcher.send(new MultiplicationSolvedEvent(
                checkedAttempt.getId(),
                checkedAttempt.getUser().getId(),
                checkedAttempt.isCorrect()
        ));

        return correct;
    }

    @Transactional(readOnly = true)
    @Override
    public List<MultiplicationResultAttempt> getStatsForUser(String userAlias) {
        return attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
    }

    private Multiplication findOrCreateMultiplication(int factorA, int factorB) {
        Optional<Multiplication> multiplication = multiplicationRepository.findByFactorAAndFactorB(factorA, factorB);
        return multiplication.orElseGet(() -> multiplicationRepository.save(new Multiplication(factorA, factorB)));
    }
}

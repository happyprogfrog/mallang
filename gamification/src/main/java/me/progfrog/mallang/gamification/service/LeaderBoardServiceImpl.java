package me.progfrog.mallang.gamification.service;

import lombok.RequiredArgsConstructor;
import me.progfrog.mallang.gamification.domain.LeaderBoardRow;
import me.progfrog.mallang.gamification.repository.ScoreCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LeaderBoardServiceImpl implements LeaderBoardService {

    private final ScoreCardRepository scoreCardRepository;


    @Override
    public List<LeaderBoardRow> getCurrentLeaderBoard() {
        return scoreCardRepository.findFirst10();
    }
}

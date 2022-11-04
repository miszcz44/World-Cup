package pl.WorldCup.WorldCup.Match;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MatchServiceTest {

    private final MatchRepository matchRepository;
    private final MatchService matchService;

    @Autowired
    MatchServiceTest(MatchRepository matchRepository, MatchService matchService) {
        this.matchRepository = matchRepository;
        this.matchService = matchService;
    }


}
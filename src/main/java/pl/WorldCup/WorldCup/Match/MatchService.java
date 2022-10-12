package pl.WorldCup.WorldCup.Match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<Match> getMatches() {
        List<Match> matches = matchRepository.findAll();
        return matches;
    }

    public Match checkIfSuchMatchIsAlreadyInBaseByCountryNames(String teamCountry1, String teamCountry2) {
        List<Match> matches = getMatches();
        for(Match match: matches) {
            if((match.getTeam1Country().compareTo(teamCountry1) == 0 && match.getTeam2Country().compareTo(teamCountry2) == 0) ||
                    (match.getTeam1Country().compareTo(teamCountry2) == 0 && match.getTeam2Country().compareTo(teamCountry1) == 0)) {
                return match;
            }
        }
        return null;
    }

    public void addNewMatch(Match match) {
        matchRepository.save(match);
    }

    @Transactional
    public void updateTheResultOfTheMatch(Match match, Integer goalsScoredByTeam1, Integer goalsScoredByTeam2) {
        match.setGoalsScoredByTeam1(goalsScoredByTeam1);
        match.setGoalsScoredByTeam2(goalsScoredByTeam2);
    }
}

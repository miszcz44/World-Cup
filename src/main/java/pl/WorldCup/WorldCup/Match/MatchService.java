package pl.WorldCup.WorldCup.Match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.WorldCup.WorldCup.User.User;
import pl.WorldCup.WorldCup.User.UserRepository;
import pl.WorldCup.WorldCup.User.UserService;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final UserService userService;

    @Autowired
    public MatchService(MatchRepository matchRepository, UserService userService) {
        this.matchRepository = matchRepository;
        this.userService = userService;
    }

    public List<Match> getMatches() {
        List<Match> matches = matchRepository.findAllUserMatches(userService.getCurrentUserId());
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
    public void updateTheResultOfTheMatch(Match match, Integer goalsScoredByTeam1, Integer goalsScoredByTeam2, Integer resultOfTheMatch) {
        match.setGoalsScoredByTeam1(goalsScoredByTeam1);
        match.setGoalsScoredByTeam2(goalsScoredByTeam2);
        match.setResultOfTheMatch(resultOfTheMatch);
    }

    public Match getMatchByTeamsCountries(String teamCountry1, String teamCountry2) {
        return matchRepository.getMatchByTeamsCountries(teamCountry1, teamCountry2, userService.getCurrentUserId());
    }

    public Integer getMatchDayOfGivenMatch(String teamCountry1, String teamCountry2) {
        return matchRepository.getMatchDayOfGivenMatch(teamCountry1, teamCountry2, userService.getCurrentUserId());
    }

    public Integer getResultOfTheMatch(Integer team1Goals, Integer team2Goals) {
        if(team1Goals > team2Goals) {
            return 1;
        }
        else if(team1Goals == team2Goals){
            return 0;
        }
        return 2;
    }

    @Transactional
    public void updateUserPoints() {
        List<User> users = userService.getAllUsers();
        for(User user: users) {
            List<Match> userMatches = matchRepository.findAllUserMatchesSorted(user.getId());
            List<Match> adminMatches = matchRepository.findAllUserMatches(userService.getAdminId());
            for(int i=0; i<userMatches.size(); i++) {
                Match userMatch = userMatches.get(i);
                Match adminMatch = adminMatches.get(i);
                if(userMatch.getGoalsScoredByTeam1() == adminMatch.getGoalsScoredByTeam1() &&
                userMatch.getGoalsScoredByTeam2() == adminMatch.getGoalsScoredByTeam2()) {
                    matchRepository.updateUserPoints(userMatch.getId(), 3);
                }
                else if(userMatch.getResultOfTheMatch() == adminMatch.getResultOfTheMatch()) {
                    matchRepository.updateUserPoints(userMatch.getId(), 1);
                }
            }
            if(!userMatches.isEmpty()) {
                Integer totalPoints = matchRepository.getSumOfUserPoints(user.getId());
                userService.setTotalUserPoints(user.getId(), totalPoints);
            }
        }
    }

}

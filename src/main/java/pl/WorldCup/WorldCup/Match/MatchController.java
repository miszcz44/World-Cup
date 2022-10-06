package pl.WorldCup.WorldCup.Match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Controller
@RestController
@RequestMapping
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping("/asd")
    public void getMatchInfoAndAddItAsANewMatch(HttpServletRequest request) {
        String teamMatch = request.getParameter("teamMatch");
        String goalsScoredByTeam1 = request.getParameter("goalsScoredByTeam1");
        String team1 = request.getParameter("team1");
        String goalsScoredByTeam2 = request.getParameter("goalsScoredByTeam2");
        String team2 = request.getParameter("team2");
        Match match = new Match(team1, team2, Integer.valueOf(goalsScoredByTeam1), Integer.valueOf(goalsScoredByTeam2));
        matchService.addNewMatch(match);
    }
}

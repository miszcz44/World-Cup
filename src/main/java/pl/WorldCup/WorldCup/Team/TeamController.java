package pl.WorldCup.WorldCup.Team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.WorldCup.WorldCup.Match.Match;
import pl.WorldCup.WorldCup.Match.MatchService;
import pl.WorldCup.WorldCup.User.User;
import pl.WorldCup.WorldCup.User.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Controller
@RestController
@RequestMapping
public class TeamController {

    private final TeamService teamService;
    private final MatchService matchService;
    private final UserRepository userRepository;
    @GetMapping({"/list", "/index"})
    public ModelAndView getAllTeams(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username);
        Long userId = user.getId();
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("groupATeams", teamService.getTeamsFromGivenGroup("a", userId));
        mav.addObject("groupBTeams", teamService.getTeamsFromGivenGroup("b", userId));
        mav.addObject("groupCTeams", teamService.getTeamsFromGivenGroup("c", userId));
        mav.addObject("groupDTeams", teamService.getTeamsFromGivenGroup("d", userId));
        mav.addObject("groupETeams", teamService.getTeamsFromGivenGroup("e", userId));
        mav.addObject("groupFTeams", teamService.getTeamsFromGivenGroup("f", userId));
        mav.addObject("groupGTeams", teamService.getTeamsFromGivenGroup("g", userId));
        mav.addObject("groupHTeams", teamService.getTeamsFromGivenGroup("h", userId));
        return mav;
    }

    @Autowired
    public TeamController(TeamService teamService, MatchService matchService, UserRepository userRepository) {
        this.teamService = teamService;
        this.matchService = matchService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public void addNewTeam(@RequestBody List<Team> teams) {
        teamService.addNewTeams(teams);
    }

    @PostMapping("/index")
    public void getMatchInfoAndUpdateFieldsBasedOnIt(HttpServletRequest request) {
        Integer teamMatch = Integer.valueOf(request.getParameter("teamMatch"));
        Integer goalsScoredByTeam1 = Integer.valueOf(request.getParameter("goalsScoredByTeam1"));
        String teamCountry1 = request.getParameter("team1");
        Integer goalsScoredByTeam2 = Integer.valueOf(request.getParameter("goalsScoredByTeam2"));
        String teamCountry2 = request.getParameter("team2");
        Team team1 = teamService.findTeamByCountry(teamCountry1);
        Team team2 = teamService.findTeamByCountry(teamCountry2);
        teamService.updateTheTeamPointsFieldBasedOnTheOutcomeOfTheGame(team1, team2, goalsScoredByTeam1, goalsScoredByTeam2, teamMatch);
        teamService.updateGoalsScoredByATeamInGivenMatchByCountry(team1, teamMatch, goalsScoredByTeam1);
        teamService.updateGoalsScoredByATeamInGivenMatchByCountry(team2, teamMatch, goalsScoredByTeam2);
        teamService.updateGoalsSufferedByATeamInGivenMatchByCountry(team1, teamMatch, goalsScoredByTeam2);
        teamService.updateGoalsSufferedByATeamInGivenMatchByCountry(team2, teamMatch, goalsScoredByTeam1);
        teamService.updateTeamPoints(team1);
        teamService.updateTeamPoints(team2);
        teamService.updateTeamGoalsScored(team1);
        teamService.updateTeamGoalsScored(team2);
        teamService.updateTeamGoalsSuffered(team1);
        teamService.updateTeamGoalsSuffered(team2);
        if(matchService.checkIfSuchMatchIsAlreadyInBaseByCountryNames(teamCountry1, teamCountry2) == null){
            Match match = new Match(teamCountry1, teamCountry2, teamMatch, goalsScoredByTeam1, goalsScoredByTeam2);
            matchService.addNewMatch(match);
            teamService.setMatchInProperOrder(team1, match, teamMatch);
            teamService.setMatchInProperOrder(team2, match, teamMatch);
        }
        else{
            Match match = matchService.checkIfSuchMatchIsAlreadyInBaseByCountryNames(teamCountry1, teamCountry2);
            matchService.updateTheResultOfTheMatch(match, goalsScoredByTeam1, goalsScoredByTeam2);
            teamService.setMatchInProperOrder(team1, match, teamMatch);
            teamService.setMatchInProperOrder(team2, match, teamMatch);
        }

    }
}

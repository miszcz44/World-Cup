package pl.WorldCup.WorldCup.Team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.WorldCup.WorldCup.Match.Match;
import pl.WorldCup.WorldCup.Match.MatchService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Controller
@RestController
@RequestMapping
public class TeamController {

    private final TeamService teamService;
    private final MatchService matchService;
    @GetMapping({"/list", "/index"})
    public ModelAndView getAllTeams(){
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("groupATeams", teamService.getTeamsFromGivenGroup("a"));
        mav.addObject("groupBTeams", teamService.getTeamsFromGivenGroup("b"));
        mav.addObject("groupCTeams", teamService.getTeamsFromGivenGroup("c"));
        mav.addObject("groupDTeams", teamService.getTeamsFromGivenGroup("d"));
        mav.addObject("groupETeams", teamService.getTeamsFromGivenGroup("e"));
        mav.addObject("groupFTeams", teamService.getTeamsFromGivenGroup("f"));
        mav.addObject("groupGTeams", teamService.getTeamsFromGivenGroup("g"));
        mav.addObject("groupHTeams", teamService.getTeamsFromGivenGroup("h"));
        return mav;
    }

    @Autowired
    public TeamController(TeamService teamService, MatchService matchService, MatchService matchService1) {
        this.teamService = teamService;
        this.matchService = matchService1;
    }

    @GetMapping
    public Integer getNumberOfTeams() {
        return teamService.getNumberOfTeams();
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
        Match match = new Match(team1, team2, goalsScoredByTeam1, goalsScoredByTeam2);
        matchService.addNewMatch(match);
        teamService.setMatchInProperOrder(team1, match, teamMatch);
        teamService.setMatchInProperOrder(team2, match, teamMatch);
    }
}

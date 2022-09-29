package pl.WorldCup.WorldCup.Team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@Controller
@RestController
@RequestMapping
public class TeamController {

    private final TeamService teamService;
    @GetMapping({"/list", "/"})
    public ModelAndView getAllTeams(){
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("teams", teamService.getTeamsFromGivenGroup("a"));
        return mav;
    }

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public Integer getNumberOfTeams() {
        return teamService.getNumberOfTeams();
    }

    @PostMapping
    public void addNewTeam(@RequestBody List<Team> teams) {
        teamService.addNewTeams(teams);
    }
    @PutMapping
    public void updateTeamPoints(String teamCountry, @RequestParam(required = false) Integer teamPoints) {
        teamService.updateTeamPoints(teamCountry, teamPoints);
    }

}

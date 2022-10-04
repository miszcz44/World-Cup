package pl.WorldCup.WorldCup.Team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Controller
@RestController
@RequestMapping
public class TeamController {

    private final TeamService teamService;
    @GetMapping({"/list", "/"})
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

    @PostMapping("/")
    public void dummy(HttpServletRequest request) {
        String dsaf = request.getParameter("goal");
        String team1 = request.getParameter("team1");
        System.out.println(dsaf);
        System.out.println(team1);
    }

}

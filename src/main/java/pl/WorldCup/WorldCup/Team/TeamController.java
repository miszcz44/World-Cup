package pl.WorldCup.WorldCup.Team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public Integer getNumberOfTeams() {
        return teamService.getNumberOfTeams();
    }

    @PostMapping
    public void addNewTeam(@RequestBody Team team) {
        teamService.addNewTeam(team);
    }

}

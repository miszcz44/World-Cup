package pl.WorldCup.WorldCup.Team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Integer getNumberOfTeams() {
        return 32;
    }

    public void addNewTeam(Team team) {
        System.out.println(team);
    }


}

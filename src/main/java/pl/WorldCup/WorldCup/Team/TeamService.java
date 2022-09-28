package pl.WorldCup.WorldCup.Team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.WorldCup.WorldCup.Group.GroupPhase;

import javax.transaction.Transactional;
import java.util.List;

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

    public void addNewTeams(List<Team> teams) {
        GroupPhase group = new GroupPhase();
        for(Team team: teams){
            team.setGroup(group);
        }
        teamRepository.saveAll(teams);
    }

    public Team findTeamByCountry(String country) {
        Team team = teamRepository.findTeamByTeamCountry(country);
        return team;
    }

    @Transactional
    public void updateTeamPoints(String country, Integer teamPoints) {
        Team team = findTeamByCountry(country);
        team.setTeamPoints(team.getTeamPoints() + teamPoints);
    }


}

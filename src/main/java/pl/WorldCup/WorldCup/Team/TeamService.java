package pl.WorldCup.WorldCup.Team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
        teamRepository.save(team);
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

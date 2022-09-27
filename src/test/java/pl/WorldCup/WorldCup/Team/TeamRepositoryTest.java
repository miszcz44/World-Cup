package pl.WorldCup.WorldCup.Team;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeamRepositoryTest {
    @Autowired
    private TeamRepository teamRepository;
    @Test
    public void printTeamByCountry() {
        Team team = teamRepository.findTeamByTeamCountry("Mexico");
        System.out.println(team.getTeamPoints());
    }

}
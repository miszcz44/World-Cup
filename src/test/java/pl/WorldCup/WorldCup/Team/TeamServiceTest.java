package pl.WorldCup.WorldCup.Team;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeamServiceTest {
    @Autowired
    private TeamService teamService;

    @Test
    public void getAllStudentsTest() {
        System.out.println(teamService.getTeams());
    }

    @Test
    void getNumberOfTeams() {
    }

    @Test
    void findDuplicates() {
    }

    @Test
    void updateTeamGoalsSuffered() {
    }

    @Test
    void updateGoalsSufferedByATeamInGivenMatchByCountry() {
    }

    @Test
    void updateTheTeamPointsFromAGivenGameField() {
    }

    @Test
    void updateTheTeamPointsFieldBasedOnTheOutcomeOfTheGame() {
    }

    @Test
    void setMatchInProperOrder() {
    }
}
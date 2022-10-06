package pl.WorldCup.WorldCup.Team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.WorldCup.WorldCup.Group.GroupPhase;
import pl.WorldCup.WorldCup.Group.GroupService;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final GroupService groupService;

    @Autowired
    public TeamService(TeamRepository teamRepository, GroupService groupService) {
        this.teamRepository = teamRepository;
        this.groupService = groupService;
    }

    public Integer getNumberOfTeams() {
        return 32;
    }

    public List<Team> getTeams() {
        return teamRepository.findAll();
    }

    public void addNewTeams(List<Team> teams) {
        teamRepository.saveAll(teams);
    }

    public Team findTeamByCountry(String country) {
        Team team = teamRepository.findTeamByTeamCountry(country);
        return team;
    }

    public List<Long> getTeamIdsByGroupId(Long groupId) {
        List<Long> teamIds = teamRepository.getTeamIdsByGroupId(groupId);
        return teamIds;
    }

    public Team getTeamById(Long teamId) {
        Team team = teamRepository.findTeamByTeamId(teamId);
        return team;
    }

    public List<Team> getTeamsFromGivenGroup(String groupName) {
        GroupPhase group = groupService.findGroupByGroupName(groupName);
        List<Long> teamIds = teamRepository.getTeamIdsByGroupId(group.getGroupId());
        List<Team> teams = new ArrayList<>();
        for(Long id : teamIds) {
            teams.add(teamRepository.findTeamByTeamId(id));
        }
        return teams;
    }

    @Transactional
    public void updateTeamPoints(Team team) {
        team.setTeamPoints(team.getFirstMatchPointsEarned() + team.getSecondMatchPointsEarned() + team.getThirdMatchPointsEarned());
    }

    @Transactional
    public void updateGoalsScoredByATeamInGivenMatchByCountry(Team team, Integer gameInOrder, Integer goalsScored) {
        if(gameInOrder == 1){
            team.setFirstMatchTeamGoalsScored(goalsScored);
        }
        else if(gameInOrder == 2){
            team.setSecondMatchTeamGoalsScored(goalsScored);
        }
        else if(gameInOrder == 3){
            team.setThirdMatchTeamGoalsScored(goalsScored);
        }
    }

    @Transactional
    public void updateGoalsSufferedByATeamInGivenMatchByCountry(Team team, Integer gameInOrder, Integer goalsSuffered) {
        if(gameInOrder == 1) {
            team.setFirstMatchTeamGoalsSuffered(goalsSuffered);
        }
        else if(gameInOrder == 2) {
            team.setSecondMatchTeamGoalsSuffered(goalsSuffered);
        }
        else if(gameInOrder == 3) {
            team.setThirdMatchTeamGoalsSuffered(goalsSuffered);
        }
    }

    @Transactional
    public void updateTheTeamPointsFromAGivenGameField(Team team, Integer gameInOrder, Integer points) {
        if(gameInOrder == 1) {
            team.setFirstMatchPointsEarned(points);
        }
        else if(gameInOrder == 2) {
            team.setSecondMatchPointsEarned(points);
        }
        else if(gameInOrder == 3) {
            team.setThirdMatchPointsEarned(points);
        }
    }

    @Transactional
    public void updateTheTeamPointsFieldBasedOnTheOutcomeOfTheGame(Team team1, Team team2, Integer team1GoalsScored, Integer team2GoalsScored, Integer gameInOrder) {
        if(team1GoalsScored > team2GoalsScored) {
            updateTheTeamPointsFromAGivenGameField(team1, gameInOrder, 3);
            updateTheTeamPointsFromAGivenGameField(team2, gameInOrder, 0);

        }
        else if(team2GoalsScored > team1GoalsScored) {
            updateTheTeamPointsFromAGivenGameField(team1, gameInOrder, 0);
            updateTheTeamPointsFromAGivenGameField(team2, gameInOrder, 3);
        }
        else {
            updateTheTeamPointsFromAGivenGameField(team1, gameInOrder, 1);
            updateTheTeamPointsFromAGivenGameField(team2, gameInOrder, 1);
        }
    }



}

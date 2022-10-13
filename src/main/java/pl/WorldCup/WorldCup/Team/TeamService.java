package pl.WorldCup.WorldCup.Team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.WorldCup.WorldCup.Group.GroupPhase;
import pl.WorldCup.WorldCup.Group.GroupService;
import pl.WorldCup.WorldCup.Match.Match;
import pl.WorldCup.WorldCup.Match.MatchService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final GroupService groupService;
    private final MatchService matchService;

    @Autowired
    public TeamService(TeamRepository teamRepository, GroupService groupService, MatchService matchService) {
        this.teamRepository = teamRepository;
        this.groupService = groupService;
        this.matchService = matchService;
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
        Integer numberOfTeamsWithSameAmountOfPoints = getNumberOfTeamsWithTheSameAmountOfPoints(teams);
        List<Long> sortedTeamIds = teamRepository.sortTeamsWithTeamIds(teamIds);
        for(Long id : sortedTeamIds) {
            teams.add(teamRepository.findTeamByTeamId(id));
        }
        if(numberOfTeamsWithSameAmountOfPoints == 2) {
            teams = sortTeamsWhenThereAre2TeamsWithSameAmountOfPoints(teams);
        }
        return teams;
    }

    public Integer getNumberOfTeamsWithTheSameAmountOfPoints(List<Team> teams) {
        List<Integer> teamsPoints = getAllTeamsPoints(teams);
        Set<Integer> uniqueTeamPoints = findDuplicates(teamsPoints);
        return 4 - uniqueTeamPoints.size();
    }

    public List<Integer> getAllTeamsPoints(List<Team> teams) {
        List<Integer> teamsPoints = new ArrayList<>();
        for (Team team : teams) {
            teamsPoints.add(team.getTeamPoints());
        }
        return teamsPoints;
    }

    public Set<Integer> findDuplicates(List<Integer> listContainingDuplicates) {
        final Set<Integer> setToReturn = new HashSet<>();
        final Set<Integer> set1 = new HashSet<>();

        for (Integer yourInt : listContainingDuplicates) {
            if (!set1.add(yourInt)) {
                setToReturn.add(yourInt);
            }
        }
        return setToReturn;
    }

    public List<Team> sortTeamsWhenThereAre2TeamsWithSameAmountOfPoints(List<Team> teams) {
        List<Team> sortedTeams = new ArrayList<>();
        Team team1 = teams.get(0);
        Team team2 = teams.get(1);
        Team team3 = teams.get(2);
        Team team4 = teams.get(3);
        if(team1.getTeamPoints() == team2.getTeamPoints()) {
            Team winner = getWinnerOfTheMatchByTeamCountries(team1.getTeamCountry(), team2.getTeamCountry());
            if(winner == team1){

            }
        }
        return sortedTeams;
    }

    public Team getWinnerOfTheMatch(Match match) {
        if(match.getGoalsScoredByTeam1() > match.getGoalsScoredByTeam2()) {
            return teamRepository.findTeamByTeamCountry(match.getTeam1Country());
        }
        else if(match.getGoalsScoredByTeam1() < match.getGoalsScoredByTeam2()) {
            return teamRepository.findTeamByTeamCountry(match.getTeam2Country());
        }
        return null;
    }
    public Team getWinnerOfTheMatchByTeamCountries(String teamCountry1, String teamCountry2) {
        Team team = matchService.getMatchByTeamsCountries(teamCountry1, teamCountry2);
        return getWinnerOfTheMatch(match);
    }
    @Transactional
    public void updateTeamPoints(Team team) {
        team.setTeamPoints(team.getFirstMatchPointsEarned() + team.getSecondMatchPointsEarned() + team.getThirdMatchPointsEarned());
    }

    @Transactional
    public void updateTeamGoalsScored(Team team) {
        team.setTeamGoalsScored(team.getFirstMatchTeamGoalsScored() + team.getSecondMatchTeamGoalsScored() + team.getThirdMatchTeamGoalsScored());
    }

    @Transactional
    public void updateTeamGoalsSuffered(Team team) {
        team.setTeamGoalsSuffered(team.getThirdMatchTeamGoalsSuffered() + team.getFirstMatchTeamGoalsSuffered() + team.getSecondMatchTeamGoalsSuffered());
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

    @Transactional
    public void setMatchInProperOrder(Team team, Match match, Integer gameInOrder) {
        if(gameInOrder == 1) {
            team.setFirstMatchOfTheGroupStage(match);
        }
        else if(gameInOrder == 2) {
            team.setSecondMatchOfTheGroupStage(match);
        }
        else {
            team.setThirdMatchOfTheGroupStage(match);
        }
    }



}

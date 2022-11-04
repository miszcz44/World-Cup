package pl.WorldCup.WorldCup.Team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.WorldCup.WorldCup.Group.GroupPhase;
import pl.WorldCup.WorldCup.Group.GroupService;
import pl.WorldCup.WorldCup.Match.Match;
import pl.WorldCup.WorldCup.Match.MatchService;
import pl.WorldCup.WorldCup.User.UserRepository;
import pl.WorldCup.WorldCup.User.UserService;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final GroupService groupService;
    private final MatchService matchService;
    private final UserService userService;

    @Autowired
    public TeamService(TeamRepository teamRepository, GroupService groupService, MatchService matchService, UserService userService) {
        this.teamRepository = teamRepository;
        this.groupService = groupService;
        this.matchService = matchService;
        this.userService = userService;
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

    public Team findTeamByCountry(String country, Long userId) {
        Team team = teamRepository.findTeamByTeamCountry(country, userId);
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

    public List<Long> sortTeamsWithTeamIds(List<Long> teamIds, Long userId) {
        return teamRepository.sortTeamsWithTeamIds(userId, teamIds);
    }

    public List<Long> sortTeamsWithTeamIdsWithoutUser(List<Long> teamIds) {
        return teamRepository.sortTeamsWithTeamIdsWithoutUser(teamIds);
    }

    public List<Team> getTeamsFromGivenGroup(String groupName, Long userId) {
        GroupPhase group = groupService.findGroupByGroupName(groupName);
        List<Long> teamIds = teamRepository.getTeamIdsByGroupId(group.getGroupId());
        List<Team> teams = new ArrayList<>();
        List<Long> sortedTeamIds = sortTeamsWithTeamIds(teamIds, userId);
        for(Long id : sortedTeamIds) {
            teams.add(teamRepository.findTeamByTeamId(id));
        }
        Integer numberOfUniqueAmountsOfPoints = getNumberOfTeamsWithTheSameAmountOfPoints(teams);
        Integer numberOfOccurencesOfTheSameAmountOfPoints = Collections.frequency(getAllTeamsPoints(teams), teams.get(1).getTeamPoints());
        if(numberOfOccurencesOfTheSameAmountOfPoints == 3) {
            List<Team> updatedTeams = sortTeamsWhenThereIsAThreeWayTie(teams);
            return updatedTeams;
        }
        else if(numberOfUniqueAmountsOfPoints == 2 || numberOfUniqueAmountsOfPoints == 3) {
            List<Team> updatedTeams = sortTeamsWhenThereAre2TeamsWithSameAmountOfPoints(teams);
            return updatedTeams;
        }
        else {
            return teams;
        }
    }

    public List<Team> sortTeamsWhenThereIsAThreeWayTie(List<Team> teams) {
        List<Team> sortedTeams = new ArrayList<>();
        Team team1 = teams.get(0);
        Team team2 = teams.get(1);
        Team team3 = teams.get(2);
        Team team4 = teams.get(3);
        if(getATeamThatDoesNotParticipateInAThreeWayTie(teams) == team1){
            sortedTeams.add(team1);
            List<Team> sortedThreeWayTieTeams = getSortedThreeWayTieTeams(team1, team2, team3, team4);
            sortedTeams.add(sortedThreeWayTieTeams.get(0));
            sortedTeams.add(sortedThreeWayTieTeams.get(1));
            sortedTeams.add(sortedThreeWayTieTeams.get(2));
        }
        else{
            List<Team> sortedThreeWayTieTeams = getSortedThreeWayTieTeams(team4, team1, team2, team3);
            sortedTeams.add(sortedThreeWayTieTeams.get(0));
            sortedTeams.add(sortedThreeWayTieTeams.get(1));
            sortedTeams.add(sortedThreeWayTieTeams.get(2));
            sortedTeams.add(team4);
        }
        return sortedTeams;
    }

    public Team getATeamThatDoesNotParticipateInAThreeWayTie(List<Team> teams) {
        if(teams.get(0).getTeamPoints() == teams.get(1).getTeamPoints()) {
            return teams.get(3);
        }
        else{
            return teams.get(0);
        }
    }

    public List<Team> getSortedThreeWayTieTeams(Team teamNotParticipating, Team team1, Team team2, Team team3) {
        Integer team1MatchDay = matchService.getMatchDayOfGivenMatch(teamNotParticipating.getTeamCountry(), team1.getTeamCountry());
        Integer team2MatchDay = matchService.getMatchDayOfGivenMatch(teamNotParticipating.getTeamCountry(), team2.getTeamCountry());
        Integer team3MatchDay = matchService.getMatchDayOfGivenMatch(teamNotParticipating.getTeamCountry(), team3.getTeamCountry());
        setGoalsScoredAndSufferedToZeroInGivenMatchday(team1, team1MatchDay);
        setGoalsScoredAndSufferedToZeroInGivenMatchday(team2, team2MatchDay);
        setGoalsScoredAndSufferedToZeroInGivenMatchday(team3, team3MatchDay);
        List<Long> teamIds = getThreeWayTieTeamIds(team1, team2, team3);
        List<Team> sortedThreeWayTieTeams = new ArrayList<>();
        List<Long> sortedThreeWayTieTeamIds = sortTeamsWithTeamIdsWithoutUser(teamIds);
        for(Long id : sortedThreeWayTieTeamIds) {
            sortedThreeWayTieTeams.add(teamRepository.findTeamByTeamId(id));
        }
        setBackGoalsScoredAndSufferedInGivenMatchday(team1, team1MatchDay, teamNotParticipating);
        setBackGoalsScoredAndSufferedInGivenMatchday(team2, team2MatchDay, teamNotParticipating);
        setBackGoalsScoredAndSufferedInGivenMatchday(team3, team3MatchDay, teamNotParticipating);
        return sortedThreeWayTieTeams;
    }

    @Transactional
    public void setGoalsScoredAndSufferedToZeroInGivenMatchday(Team team, Integer matchDay) {
        if(matchDay == 1){
            teamRepository.updateGoalsScoredAndSufferedByATeam(team.getTeamId(),
                    team.getTeamGoalsScored() - team.getFirstMatchTeamGoalsScored(),
                    team.getTeamGoalsSuffered() - team.getFirstMatchTeamGoalsSuffered());
        }
        else if(matchDay == 2){
            teamRepository.updateGoalsScoredAndSufferedByATeam(team.getTeamId(),
                    team.getTeamGoalsScored() - team.getSecondMatchTeamGoalsScored(),
                    team.getTeamGoalsSuffered() - team.getSecondMatchTeamGoalsSuffered());
        }
        else if(matchDay == 3){
            teamRepository.updateGoalsScoredAndSufferedByATeam(team.getTeamId(),
                    team.getTeamGoalsScored() - team.getThirdMatchTeamGoalsScored(),
                    team.getTeamGoalsSuffered() - team.getThirdMatchTeamGoalsSuffered());
        }
    }

    @Transactional
    public void setBackGoalsScoredAndSufferedInGivenMatchday(Team team, Integer matchDay, Team teamNotParticipating) {
        if(matchDay == 1){
            teamRepository.updateGoalsScoredAndSufferedByATeam(team.getTeamId(),
                    team.getTeamGoalsScored() + teamNotParticipating.getFirstMatchTeamGoalsSuffered(),
                    team.getTeamGoalsSuffered() + teamNotParticipating.getFirstMatchTeamGoalsScored());
        }
        else if(matchDay == 2){
            teamRepository.updateGoalsScoredAndSufferedByATeam(team.getTeamId(),
                    team.getTeamGoalsScored() + teamNotParticipating.getSecondMatchTeamGoalsSuffered(),
                    team.getTeamGoalsSuffered() + teamNotParticipating.getSecondMatchTeamGoalsScored());
        }
        else if(matchDay == 3){
            teamRepository.updateGoalsScoredAndSufferedByATeam(team.getTeamId(),
                    team.getTeamGoalsScored() + teamNotParticipating.getThirdMatchTeamGoalsSuffered(),
                    team.getTeamGoalsSuffered() + teamNotParticipating.getThirdMatchTeamGoalsScored());
        }
    }
    public List<Long> getThreeWayTieTeamIds(Team team1, Team team2, Team team3) {
        List<Long> threeWayTieTeamIds = new ArrayList<>();
        threeWayTieTeamIds.add(team1.getTeamId());
        threeWayTieTeamIds.add(team2.getTeamId());
        threeWayTieTeamIds.add(team3.getTeamId());
        return threeWayTieTeamIds;
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
            List<Team> tmpTeams = addTeamsInCorrectOrder(team1, team2);
            sortedTeams.add(tmpTeams.get(0));
            sortedTeams.add(tmpTeams.get(1));
        }
        else if(team2.getTeamPoints() == team3.getTeamPoints()) {
            sortedTeams.add(team1);
            List<Team> tmpTeams = addTeamsInCorrectOrder(team2, team3);
            sortedTeams.add(tmpTeams.get(0));
            sortedTeams.add(tmpTeams.get(1));
            sortedTeams.add(team4);
            return sortedTeams;
        }
        if(team3.getTeamPoints() == team4.getTeamPoints()) {
            if(sortedTeams.size() == 0) {
                sortedTeams.add(team1);
                sortedTeams.add(team2);
            }
            List<Team> tmpTeams = addTeamsInCorrectOrder(team3, team4);
            sortedTeams.add(tmpTeams.get(0));
            sortedTeams.add(tmpTeams.get(1));
        }
        else{
            sortedTeams.add(team3);
            sortedTeams.add(team4);
        }
            return sortedTeams;
    }

    public List<Team> addTeamsInCorrectOrder(Team team1, Team team2) {
        List<Team> teams = new ArrayList<>();
        Team winner = getWinnerOfTheMatchByTeamCountries(team1.getTeamCountry(), team2.getTeamCountry());
        if(winner == team2){
            teams.add(team2);
            teams.add(team1);
        }
        else {
            teams.add(team1);
            teams.add(team2);
        }
        return teams;
    }

    public Team getWinnerOfTheMatch(Match match) {
        if(match != null) {
            if (match.getGoalsScoredByTeam1() > match.getGoalsScoredByTeam2()) {
                return teamRepository.findTeamByTeamCountry(match.getTeam1Country(), userService.getCurrentUserId());
            } else if (match.getGoalsScoredByTeam1() < match.getGoalsScoredByTeam2()) {
                return teamRepository.findTeamByTeamCountry(match.getTeam2Country(), userService.getCurrentUserId());
            }
        }
        return null;
    }
    public Team getWinnerOfTheMatchByTeamCountries(String teamCountry1, String teamCountry2) {
        Match match = matchService.getMatchByTeamsCountries(teamCountry1, teamCountry2);
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

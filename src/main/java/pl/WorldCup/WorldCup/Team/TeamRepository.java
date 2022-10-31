package pl.WorldCup.WorldCup.Team;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.WorldCup.WorldCup.User.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    public Team findTeamByTeamCountry(String country);



    @Query(value = "SELECT team_id FROM group_team_mapping WHERE group_id = ?1",
            nativeQuery = true
    )
    public List<Long> getTeamIdsByGroupId(Long groupId);

    @Query(value = "SELECT team_id FROM team " +
            "WHERE team_id IN :teamIds AND user_id = :userId " +
            "ORDER BY team_points DESC, " +
            "(team_goals_scored - team_goals_suffered) DESC, " +
            "team_goals_scored DESC",
            nativeQuery = true
    )
    public List<Long> sortTeamsWithTeamIds(@Param("userId") Long userId, @Param("teamIds") List<Long> teamIds);

    @Query(value = "SELECT team_id FROM team " +
            "WHERE team_id IN :teamIds " +
            "ORDER BY team_points DESC, " +
            "(team_goals_scored - team_goals_suffered) DESC," +
            "team_goals_scored DESC",
            nativeQuery = true
    )
    public List<Long> sortTeamsWithTeamIdsWithoutUser(@Param("teamIds") List<Long> teamIds);

    public Team findTeamByTeamId(Long teamId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE team " +
            "SET team_goals_scored = ?2, " +
            "team_goals_suffered = ?3 " +
            "WHERE team_id = ?1",
            nativeQuery = true
    )
    public void updateGoalsScoredAndSufferedByATeam(Long teamId, Integer goalsScored, Integer goalsSuffered);
}

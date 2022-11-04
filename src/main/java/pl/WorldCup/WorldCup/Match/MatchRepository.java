package pl.WorldCup.WorldCup.Match;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query(value = "SELECT * FROM match" +
            " WHERE ((team1country = ?1 AND team2country = ?2) OR (team1country =?2 AND team2country = ?1)) AND user_id = ?3",
            nativeQuery = true
    )
    public Match getMatchByTeamsCountries(String teamCountry1, String teamCountry2, Long userId);

    @Query(value = "SELECT match_day FROM match " +
            "WHERE ((team1country = ?1 AND team2country = ?2) OR (team1country =?2 AND team2country = ?1)) AND user_id = ?3",
            nativeQuery = true
    )
    public Integer getMatchDayOfGivenMatch(String teamCountry1, String teamCountry2, Long userId);

    @Query(value = "SELECT * FROM match WHERE user_id = ?1",
            nativeQuery = true)
    public List<Match> findAllUserMatches(Long userId);

    @Query(value = "SELECT * FROM match WHERE user_id = ?1 ORDER BY match_number",
            nativeQuery = true)
    public List<Match> findAllUserMatchesSorted(Long userId);

    @Query(value = "UPDATE match SET user_points = ?2 WHERE match_id = ?1",
            nativeQuery = true)
    @Modifying
    @Transactional
    public void updateUserPoints(Long matchId, Integer points);

    @Query(value = "SELECT SUM(user_points) from match WHERE user_id = ?1",
            nativeQuery = true)
    public Integer getSumOfUserPoints(Long userId);
}

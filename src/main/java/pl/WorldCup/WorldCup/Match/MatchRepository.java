package pl.WorldCup.WorldCup.Match;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query(value = "SELECT * FROM match" +
            " WHERE (team1country = ?1 AND team2country = ?2) OR (team1country =?2 AND team2country = ?1)",
            nativeQuery = true
    )
    public Match getMatchByTeamsCountries(String teamCountry1, String teamCountry2);

    @Query(value = "SELECT match_day FROM match " +
            "WHERE (team1country = ?1 AND team2country = ?2) OR (team1country =?2 AND team2country = ?1)",
            nativeQuery = true
    )
    public Integer getMatchDayOfGivenMatch(String teamCountry1, String teamCountry2);
}

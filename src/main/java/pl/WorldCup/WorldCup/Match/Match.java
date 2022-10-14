package pl.WorldCup.WorldCup.Match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.WorldCup.WorldCup.Team.Team;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Match")
@Table(name = "match")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    @Id
    @SequenceGenerator(
            name = "match_sequence",
            sequenceName = "match_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "match_sequence"
    )
    @Column(
            name = "match_id",
            updatable = false
    )
    private Long id;

    private String team1Country;
    private String team2Country;

    @Column(
            name = "match_day",
            columnDefinition = "integer"
    )
    private Integer matchDay;
    @Column(
            name = "team1_goals_scored",
            nullable = false,
            columnDefinition = "integer default 0"
    )
    private Integer goalsScoredByTeam1;
    @Column(
            name = "team2_goals_scored",
            nullable = false,
            columnDefinition = "integer default 0"
    )
    private Integer goalsScoredByTeam2;

    public Match(String team1Country, String team2Country, Integer matchDay, Integer goalsScoredByTeam1, Integer goalsScoredByTeam2) {
        this.team1Country = team1Country;
        this.team2Country = team2Country;
        this.matchDay = matchDay;
        this.goalsScoredByTeam1 = goalsScoredByTeam1;
        this.goalsScoredByTeam2 = goalsScoredByTeam2;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", team1Country='" + team1Country + '\'' +
                ", team2Country='" + team2Country + '\'' +
                ", goalsScoredByTeam1=" + goalsScoredByTeam1 +
                ", goalsScoredByTeam2=" + goalsScoredByTeam2 +
                '}';
    }
}

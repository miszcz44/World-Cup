package pl.WorldCup.WorldCup.Match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(
            name = "team1_country",
            nullable = false,
            columnDefinition = "text"
    )
    private String team1;
    @Column(
            name = "team2_country",
            nullable = false,
            columnDefinition = "text"
    )
    private String team2;
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

    public Match(String team1, String team2, Integer valueOf, Integer valueOf1) {
        this.team1 = team1;
        this.team2 = team2;
        this.goalsScoredByTeam1 = valueOf;
        this.goalsScoredByTeam2 = valueOf1;
    }
}

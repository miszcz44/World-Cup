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
    @OneToOne(
            cascade = CascadeType.ALL,
            optional = false
    )
    @JoinColumn(
            name = "team1_id"
    )
    private Team team1;
    @OneToOne(
            cascade = CascadeType.ALL,
            optional = false
    )
    @JoinColumn(
            name = "team2_id"
    )
    private Team team2;
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

    public Match(Team team1, Team team2, Integer goalsScoredByTeam1, Integer goalsScoredByTeam2) {
        this.team1 = team1;
        this.team2 = team2;
        this.goalsScoredByTeam1 = goalsScoredByTeam1;
        this.goalsScoredByTeam2 = goalsScoredByTeam2;
    }
}

package pl.WorldCup.WorldCup.Match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.WorldCup.WorldCup.Team.Team;
import pl.WorldCup.WorldCup.User.User;

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
    private Integer goalsScoredByTeam1 = 0;
    @Column(
            name = "team2_goals_scored",
            nullable = false,
            columnDefinition = "integer default 0"
    )
    private Integer goalsScoredByTeam2 = 0;

    @Column(
            name = "result_of_the_match"
    )
    private Integer resultOfTheMatch;

    @Column(
            name = "match_number",
            nullable = false
    )
    private Integer matchNumber;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(
            name = "user_points"
    )
    private Integer userPoints = 0;

    public Match(String team1Country, String team2Country, Integer matchDay, Integer goalsScoredByTeam1, Integer goalsScoredByTeam2, Integer resultOfTheMatch, Integer matchNumber, User user) {
        this.team1Country = team1Country;
        this.team2Country = team2Country;
        this.matchDay = matchDay;
        this.goalsScoredByTeam1 = goalsScoredByTeam1;
        this.goalsScoredByTeam2 = goalsScoredByTeam2;
        this.resultOfTheMatch = resultOfTheMatch;
        this.matchNumber = matchNumber;
        this.user = user;
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

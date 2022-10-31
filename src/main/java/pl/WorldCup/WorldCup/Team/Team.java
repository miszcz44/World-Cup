package pl.WorldCup.WorldCup.Team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.WorldCup.WorldCup.Group.GroupPhase;
import pl.WorldCup.WorldCup.Match.Match;
import pl.WorldCup.WorldCup.User.User;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Team")
@Table(name = "team")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {



    @Id
    @SequenceGenerator(
            name = "team_sequence",
            sequenceName = "team_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "team_sequence"
    )
    @Column(
            name = "team_id",
            updatable = false
    )
    private Long teamId;
    @Column(
            name = "team_country",
            nullable = false,
            columnDefinition = "text"
    )
    private String teamCountry;
    @Column(
            name = "team_points",
            nullable = false,
            columnDefinition = "integer default 0"
    )
    private Integer teamPoints = 0;
    @Column(
            name = "team_goals_scored",
            nullable = false,
            columnDefinition = "integer default 0"
    )

    private Integer teamGoalsScored = 0;
    @Column(
            name = "team_goals_suffered",
            nullable = false,
            columnDefinition = "integer default 0"
    )
    private Integer teamGoalsSuffered = 0;
    @Column(
            name = "first_match_goals_scored",
            nullable = false,
            columnDefinition = "integer default 0"
    )
    private Integer firstMatchTeamGoalsScored = 0;
    @Column(
            name = "first_match_goals_suffered",
            nullable = false,
            columnDefinition = "integer default 0"
    )
    private Integer firstMatchTeamGoalsSuffered = 0;
    @Column(
            name = "second_match_goals_scored",
            nullable = false,
            columnDefinition = "integer default 0"
    )
    private Integer secondMatchTeamGoalsScored = 0;
    @Column(
            name = "second_match_goals_suffered",
            nullable = false,
            columnDefinition = "integer default 0"
    )
    private Integer secondMatchTeamGoalsSuffered = 0;
    @Column(
            name = "third_match_goals_scored",
            nullable = false,
            columnDefinition = "integer default 0"
    )
    private Integer thirdMatchTeamGoalsScored = 0;
    @Column(
            name = "third_match_goals_suffered",
            nullable = false,
            columnDefinition = "integer default 0"
    )
    private Integer thirdMatchTeamGoalsSuffered = 0;
    @Column(
            name = "first_match_team_points_earned",
            nullable = false,
            columnDefinition = "integer default 0"
    )
    private Integer firstMatchPointsEarned = 0;
    @Column(
            name = "second_match_team_points_earned",
            nullable = false,
            columnDefinition = "integer default 0"
    )
    private Integer secondMatchPointsEarned = 0;
    @Column(
            name = "third_match_team_points_earned",
            nullable = false,
            columnDefinition = "integer default 0"
    )
    private Integer thirdMatchPointsEarned = 0;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "first_match")
    private Match firstMatchOfTheGroupStage;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "second_match")
    private Match secondMatchOfTheGroupStage;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "third_match")
    private Match thirdMatchOfTheGroupStage;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

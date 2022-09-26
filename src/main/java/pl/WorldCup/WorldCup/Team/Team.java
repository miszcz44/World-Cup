package pl.WorldCup.WorldCup.Team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.WorldCup.WorldCup.Group.GroupPhase;

import javax.persistence.*;

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
    private Integer teamPoints;
    @Column(
            name = "team_goals_scored",
            nullable = false,
            columnDefinition = "integer default 0"
    )
    private Integer teamGoalsScored;
    @Column(
            name = "team_goals_suffered",
            nullable = false,
            columnDefinition = "integer default 0"
    )
    private Integer teamGoalsSuffered;
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "group_id",
            referencedColumnName = "groupId"
    )
    private GroupPhase group;
}

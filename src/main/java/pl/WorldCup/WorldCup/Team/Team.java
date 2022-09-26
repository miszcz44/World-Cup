package pl.WorldCup.WorldCup.Team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.WorldCup.WorldCup.Group.GroupPhase;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table
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
    private Long id;
    private String country;
    private Integer points;
    private Integer goalsScored;
    private Integer goalsSuffered;
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "group_id",
            referencedColumnName = "id"
    )
    private GroupPhase group;
}

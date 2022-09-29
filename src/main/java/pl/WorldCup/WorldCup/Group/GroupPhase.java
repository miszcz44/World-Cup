package pl.WorldCup.WorldCup.Group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.WorldCup.WorldCup.Team.Team;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupPhase {

    @Id
    @SequenceGenerator(
            name = "group_sequence",
            sequenceName = "group_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "group_sequence"
    )
    private Long groupId;
    @Column(
            name = "group_name",
            columnDefinition = "text"
    )
    private String groupName;
    @ManyToMany(
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "group_team_mapping",
            joinColumns = @JoinColumn(
                    name = "group_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "team_id"
            )
    )
    private List<Team> groupTeams = new ArrayList<>();
}

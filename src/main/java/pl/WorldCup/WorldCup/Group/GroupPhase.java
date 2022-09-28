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
            nullable = false,
            columnDefinition = "text"
    )
    private String groupName;
    @OneToMany(
            mappedBy = "group",
            cascade = CascadeType.ALL
    )
    private List<Team> groupTeams = new ArrayList<>();

    public GroupPhase(String a) {
        this.groupName = a;
    }
}

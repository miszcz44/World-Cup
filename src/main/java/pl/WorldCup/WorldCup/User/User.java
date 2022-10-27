package pl.WorldCup.WorldCup.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.UsesSunHttpServer;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    @Column(
            nullable = false,
            unique = true
    )
    private String email;
    @Column(
            nullable = false,
            length = 20
    )
    private String username;
    @Column(
            nullable = false,
            length = 64
    )
    private String password;

    @Column(
            columnDefinition = "integer default 0",
            nullable = false
    )
    private Integer points;

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.points = 0;
    }
}

package pl.WorldCup.WorldCup.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT s FROM User s WHERE s.username = ?1")
    User findByUsername(String username);

    @Query("SELECT s FROM User s WHERE s.id = ?1")
    User findUserById(Long id);

    @Query(value = "SELECT * FROM users WHERE username != 'SUPERUSERADMIN' ORDER BY points DESC",
            nativeQuery = true)
    List<User> getSortedUsers();

    @Query(value = "SELECT email FROM users",
            nativeQuery = true)
    List<String> getEmails();

    @Query(value = "SELECT username FROM users",
            nativeQuery = true)
    List<String> getUsernames();

    @Query(value = "SELECT * FROM users WHERE username != 'SUPERUSERADMIN'",
            nativeQuery = true)
    List<User> getAllUsers();

    @Query(value = "SELECT id FROM users WHERE username = 'SUPERUSERADMIN'",
            nativeQuery = true)
    Long getAdminId();

    @Query(value = "UPDATE users SET points = ?2 WHERE id = ?1",
            nativeQuery = true)
    @Modifying
    @Transactional
    void setUserTotalPoints(Long userId, Integer totalPoints);
}

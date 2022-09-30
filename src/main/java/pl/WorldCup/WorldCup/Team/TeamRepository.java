package pl.WorldCup.WorldCup.Team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    public Team findTeamByTeamCountry(String country);

    @Query(value = "SELECT team_id FROM group_team_mapping WHERE group_id = ?1",
            nativeQuery = true
    )
    public List<Long> getTeamIdsByGroupId(Long groupId);

    public Team findTeamByTeamId(Long teamId);
}

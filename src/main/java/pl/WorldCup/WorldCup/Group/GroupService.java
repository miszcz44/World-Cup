package pl.WorldCup.WorldCup.Group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.WorldCup.WorldCup.Team.Team;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }
    public void addNewGroup(GroupPhase group) {
        groupRepository.save(group);
    }

    public GroupPhase findGroupByGroupName(String groupName) {
        GroupPhase groupPhase = groupRepository.findGroupByGroupName(groupName);
        return groupPhase;
    }

    @Transactional
    public void addTeamToGroup(Team team, String groupName) {
        GroupPhase group = findGroupByGroupName(groupName);
        List<Team> teams = group.getGroupTeams();
        teams.add(team);
        group.setGroupTeams(teams);
    }

    public Long getGroupIdByGroupName(String groupName) {
        GroupPhase group = groupRepository.findGroupByGroupName(groupName);
        return group.getGroupId();
    }

}

package pl.WorldCup.WorldCup.Group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.WorldCup.WorldCup.Team.Team;

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

}

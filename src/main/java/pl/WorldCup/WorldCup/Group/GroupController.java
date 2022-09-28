package pl.WorldCup.WorldCup.Group;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.WorldCup.WorldCup.Team.Team;

import java.util.concurrent.Phaser;

@RestController
@RequestMapping(path = "/group")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }
    @PostMapping
    public void addNewGroup(@RequestBody GroupPhase group) {
        groupService.addNewGroup(group);
    }
}

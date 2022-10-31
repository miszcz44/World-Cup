package pl.WorldCup.WorldCup.Registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.WorldCup.WorldCup.Group.GroupRepository;
import pl.WorldCup.WorldCup.Group.GroupService;
import pl.WorldCup.WorldCup.Team.Team;
import pl.WorldCup.WorldCup.Team.TeamRepository;
import pl.WorldCup.WorldCup.Team.TeamService;
import pl.WorldCup.WorldCup.User.User;
import pl.WorldCup.WorldCup.User.UserRepository;

@Controller
public class RegistrationController {

    static String[] teamCountries={"Katar","Ekwador","Holandia","Senegal","USA","Anglia","Walia","Iran","Argentyna",
            "Polska","Meksyk","Arabia Saudyjska","Francja","Australia","Dania","Tunezja","Hiszpania","Kostaryka","Niemcy",
            "Japonia","Belgia","Kanada","Maroko","Chorwacja","Brazylia","Serbia","Szwajcaria","Kamerun","Portugalia","Ghana",
            "Urugwaj","Korea Południowa"};
    static String[] groups = {"a","b","c","d","e","f","g","h"};

    @Autowired
    private final UserRepository repository;
    @Autowired
    private final TeamRepository teamRepository;
    @Autowired
    private final TeamService teamService;
    @Autowired
    private final GroupService groupService;
    public RegistrationController(UserRepository repository, TeamRepository teamRepository, TeamService teamService, GroupService groupService) {
        this.repository = repository;
        this.teamRepository = teamRepository;
        this.teamService = teamService;
        this.groupService = groupService;
    }


    @GetMapping("")
    public String viewHomePage() {
        return "HomePage";
    }

    @GetMapping("/register")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping("process_register")
    public String processRegistration(User user) {
        Integer counter = 0;
        Integer groupCounter = 0;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        repository.save(user);
        for(String teamCountry : teamCountries) {
            Team team = new Team();
            team.setTeamCountry(teamCountry);
            team.setUser(user);
            teamRepository.save(team);
            counter++;
            if(counter == 4){
                counter = 0;
                groupCounter++;
            }
            groupService.addTeamToGroup(team, groups[groupCounter]);
        }
        return "register_success";
    }
}

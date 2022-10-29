package pl.WorldCup.WorldCup.Registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.WorldCup.WorldCup.User.User;
import pl.WorldCup.WorldCup.User.UserRepository;

import javax.persistence.GeneratedValue;

@Controller
public class RegistrationController {

    @Autowired
    UserRepository repository;


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
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        repository.save(user);
        return "register_success";
    }
}

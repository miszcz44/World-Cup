package pl.WorldCup.WorldCup.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.WorldCup.WorldCup.appuser.AppUser;
import pl.WorldCup.WorldCup.appuser.AppUserRole;
import pl.WorldCup.WorldCup.appuser.AppUserService;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final AppUserService appUserService;

    public String register(RegistrationRequest request) {
        boolean isEmailValid = emailValidator.test(request.getEmail());
        if(!isEmailValid) {
            throw new IllegalStateException("email not valid mordo");
        }
        return appUserService.signUpUser(
                new AppUser(
                        request.getUsername(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }
}

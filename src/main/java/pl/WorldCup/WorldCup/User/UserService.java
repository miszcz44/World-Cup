package pl.WorldCup.WorldCup.User;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long getCurrentUserId(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username);
        return user.getId();
    }

    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public Long getAdminId() {
        return userRepository.getAdminId();
    }

    public void setTotalUserPoints(Long userId, Integer totalPoints) {
        userRepository.setUserTotalPoints(userId, totalPoints);
    }
}

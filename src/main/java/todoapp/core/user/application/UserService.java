package todoapp.core.user.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import todoapp.core.user.domain.ProfilePicture;
import todoapp.core.user.domain.User;
import todoapp.core.user.domain.UserEntityNotFoundException;
import todoapp.core.user.domain.UserRepository;

@Service
public class UserService implements UserPasswordVerifier, UserJoinder, ProfilePictureChanger {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User verify(String username, String rawPassword) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserEntityNotFoundException(username))
                .verifyPassword(rawPassword);
    }

    public User join(String username, String rawPassword) {
        return userRepository.findByUsername(username).orElseGet(() -> {
            User user = userRepository.save(new User(username, rawPassword));
            log.info("new user joining: {}", user);

            return user;
        });
    }

    @Override
    public User change(String username, ProfilePicture profilePicture) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserEntityNotFoundException(username))
                .changeProfilePicture(profilePicture);
    }

}

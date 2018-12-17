package todoapp.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;
import todoapp.core.user.application.ProfilePictureChanger;
import todoapp.core.user.domain.ProfilePicture;
import todoapp.core.user.domain.User;
import todoapp.security.UserSession;
import todoapp.web.model.UserProfile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@RestController
public class UserRestController {
    private final ProfilePictureChanger profilePictureChanger;

    public UserRestController(ProfilePictureChanger profilePictureChanger) {
        this.profilePictureChanger = profilePictureChanger;
    }

    @GetMapping(value = "/api/user/profile")
    public ResponseEntity<UserProfile> getUserProfile(UserSession userSession) {

        return Objects.nonNull(userSession) ? ResponseEntity.ok(new UserProfile(userSession.getUser()))
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/api/user/profile-picture")
    public UserProfile profilePicture(UserSession session, MultipartFile profilePicture) throws IOException {

        // TODO 추상화 예를 들어 ProfilePictureStore..
        Path path = Paths.get("./files/user-profile-picture");
        if (!path.toFile().exists()) {
            path.toFile().mkdirs();
        }

        Path filePath = path.resolve(UUID.randomUUID().toString());
        profilePicture.transferTo(filePath);

        User savedUser = profilePictureChanger.change(session.getUser().getUsername(),
                new ProfilePicture(filePath.toUri()));

        RequestContextHolder.currentRequestAttributes()
                .setAttribute(User.class.toString(), savedUser, RequestAttributes.SCOPE_SESSION);

        return new UserProfile(savedUser);
    }
}

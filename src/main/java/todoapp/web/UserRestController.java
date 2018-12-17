package todoapp.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import todoapp.security.UserSession;
import todoapp.web.model.UserProfile;

import java.util.Objects;

@RestController
public class UserRestController {
    @GetMapping(value = "/api/user/profile")
    public ResponseEntity<UserProfile> getUserProfile(UserSession userSession) {

        return Objects.nonNull(userSession) ? ResponseEntity.ok(new UserProfile(userSession.getUser()))
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

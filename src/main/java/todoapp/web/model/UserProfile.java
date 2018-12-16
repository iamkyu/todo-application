package todoapp.web.model;

import todoapp.core.user.domain.User;

import java.util.Date;
import java.util.Objects;

/**
 * 사용자 프로필 모델
 */
public class UserProfile {

    private User user;

    public UserProfile(User user) {
        this.user = user;
    }

    public String getName() {
        return user.getUsername();
    }

    public String getProfilePictureUrl() {
        if (Objects.isNull(user.getProfilePicture())) {
            return "/assets/img/profile-picture.png";
        }
        return "/user/profile-picture?lastModifiedDate=" + new Date().getTime();
    }

}

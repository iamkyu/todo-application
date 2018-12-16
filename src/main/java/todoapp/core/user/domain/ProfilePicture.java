package todoapp.core.user.domain;

import java.net.URI;

/**
 * 사용자 프로필 이미지 값 개체
 */
public class ProfilePicture {

    private URI uri;

    public ProfilePicture(URI uri) {
        this.uri = uri;
    }

    public URI getUri() {
        return uri;
    }

    private void setUri(URI uri) {
        this.uri = uri;
    }

}

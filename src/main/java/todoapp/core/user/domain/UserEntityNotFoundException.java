package todoapp.core.user.domain;

/**
 * 사용자 저장소에서 사용자 엔티티를 찾을 수 없을 때 발생 가능한 예외 클래스
 */
public class UserEntityNotFoundException extends UserEntityException {

    private String username;

    public UserEntityNotFoundException(String username) {
        super(String.format("데이터베이스에서 엔티티를 찾을 수 없습니다. (username: %s)", username));
        this.username = username;
    }

    @Override
    public Object[] getArguments() {
        return new Object[]{String.valueOf(username)};
    }

}

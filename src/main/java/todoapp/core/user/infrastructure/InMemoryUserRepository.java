package todoapp.core.user.infrastructure;

import org.springframework.stereotype.Repository;
import todoapp.core.user.domain.User;
import todoapp.core.user.domain.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 메모리 기반으로 사용자 저장소 구현
 */
@Repository
public class InMemoryUserRepository implements UserRepository {

    private final List<User> users = new CopyOnWriteArrayList<>();

    @Override
    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(user -> Objects.equals(user.getUsername(), username))
                .findAny();
    }

    @Override
    public User save(User user) {
        users.add(user);
        return user;
    }

}

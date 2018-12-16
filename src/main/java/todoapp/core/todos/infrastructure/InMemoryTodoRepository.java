package todoapp.core.todos.infrastructure;

import org.springframework.stereotype.Repository;
import todoapp.core.todos.domain.Todo;
import todoapp.core.todos.domain.TodoRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class InMemoryTodoRepository implements TodoRepository {
    private List<Todo> todos = new CopyOnWriteArrayList<>();

    @Override
    public Iterable<Todo> findAll() {
        return Collections.unmodifiableList(this.todos);
    }

    @Override
    public Iterable<Todo> findByUsername(String username) {
        return null;
    }

    @Override
    public Optional<Todo> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Todo save(Todo todo) {
        this.todos.add(todo);
        return todo;
    }

    @Override
    public void delete(Todo todo) {

    }
}

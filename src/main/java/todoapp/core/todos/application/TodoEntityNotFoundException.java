package todoapp.core.todos.application;

public class TodoEntityNotFoundException extends RuntimeException {
    public TodoEntityNotFoundException(Long id) {
        super(String.format("존재하지 않는 Todo 입니다. (id = {%d})", id));
    }
}

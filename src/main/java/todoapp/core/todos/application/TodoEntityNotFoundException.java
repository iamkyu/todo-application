package todoapp.core.todos.application;

import todoapp.commons.SystemException;

public class TodoEntityNotFoundException extends SystemException {
    private final Long id;

    public TodoEntityNotFoundException(Long id) {
        super(String.format("존재하지 않는 Todo 입니다. (id = {%d})", id));
        this.id = id;
    }

    @Override
    public Object[] getArguments() {
        return new Object[]{String.valueOf(id)};
    }
}

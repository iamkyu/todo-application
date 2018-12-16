package todoapp.core.todos.application;

import org.springframework.stereotype.Service;
import todoapp.commons.util.StreamUtils;
import todoapp.core.todos.domain.Todo;
import todoapp.core.todos.domain.TodoRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class TodoManage implements TodoFinder, TodoEditor {
    private final TodoRepository todoRepository;

    public TodoManage(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<Todo> getAll() {
        return StreamUtils
                .createStreamFromIterator(todoRepository.findAll().iterator())
                .collect(toList());
    }

    @Override
    public Todo create(String title) {
        Todo todo = Todo.create(title);
        return todoRepository.save(todo);
    }

    @Override
    public Todo update(Long id, String title, boolean completed) {
        return todoRepository.findById(id)
                .map(item -> item.update(title, completed))
                .orElseThrow(() -> new TodoEntityNotFoundException(id));
    }

    @Override
    public Todo delete(Long id) {
        Todo found = todoRepository.findById(id)
                .orElseThrow(() -> new TodoEntityNotFoundException(id));
        todoRepository.delete(found);

        return found;
    }
}

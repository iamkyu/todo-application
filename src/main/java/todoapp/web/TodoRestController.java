package todoapp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import todoapp.core.todos.application.TodoEditor;
import todoapp.core.todos.application.TodoFinder;
import todoapp.core.todos.application.WriteTodoCommand;
import todoapp.core.todos.domain.Todo;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TodoRestController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TodoFinder todoFinder;
    private final TodoEditor todoEditor;

    public TodoRestController(TodoFinder todoFinder, TodoEditor todoEditor) {
        this.todoFinder = todoFinder;
        this.todoEditor = todoEditor;
    }

    @GetMapping(value = "/api/todos")
    public List<Todo> getTodos() {
        return todoFinder.getAll();
    }

    @PostMapping(value = "/api/todos")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid WriteTodoCommand command) {
        logger.debug("request body: {}", command);
        todoEditor.create(command.getTitle());
    }
}

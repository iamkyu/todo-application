package todoapp.core.todos.application;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TodoCreationException extends RuntimeException {
    public TodoCreationException(String message) {
        super(message);
    }
}

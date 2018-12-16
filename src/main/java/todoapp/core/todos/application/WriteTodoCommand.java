package todoapp.core.todos.application;

import javax.validation.constraints.Size;

public class WriteTodoCommand {
    @Size(min = 4)
    private String title;

    private WriteTodoCommand() {
    }

    public WriteTodoCommand(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

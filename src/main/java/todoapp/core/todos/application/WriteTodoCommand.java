package todoapp.core.todos.application;

import javax.validation.constraints.Size;

public class WriteTodoCommand {
    @Size(min = 4)
    private String title;
    private boolean completed;

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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

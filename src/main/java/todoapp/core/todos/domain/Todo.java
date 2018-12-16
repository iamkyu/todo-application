package todoapp.core.todos.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 할 일 엔티티(Entity)
 */
public class Todo {

    private Long id;
    private String title;
    private TodoState state = TodoState.ACTIVE;

    private String username;

    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime lastModifiedDate = createdDate;

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public TodoState getState() {
        return state;
    }

    private void setState(TodoState state) {
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    private void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    private void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public boolean isCompleted() {
        return state == TodoState.COMPLETED;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return Objects.equals(getId(), todo.getId());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Todo{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }


    public static Todo create(String title) {
        Todo todo = new Todo();
        todo.setId(System.nanoTime());
        todo.setTitle(title);
        return todo;
    }

    public static Todo create(String title, String username) {
        Todo todo = new Todo();
        todo.setId(System.nanoTime());
        todo.setTitle(title);
        todo.setUsername(username);
        return todo;
    }

}

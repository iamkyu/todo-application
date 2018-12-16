package todoapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import todoapp.commons.domain.Spreadsheet;
import todoapp.core.todos.application.TodoFinder;
import todoapp.core.todos.domain.Todo;
import todoapp.web.model.SiteProperties;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TodoController {
    private final SiteProperties siteProperties;
    private final TodoFinder todoFinder;

    public TodoController(SiteProperties siteProperties, TodoFinder todoFinder) {
        this.siteProperties = siteProperties;
        this.todoFinder = todoFinder;
    }

    @GetMapping("/todos")
    public void getTodos() {
    }

    @GetMapping(value = "/todos", produces = "text/csv")
    public Spreadsheet downloadTodos() {
        List<Todo> todos = todoFinder.getAll();

        Spreadsheet.Row header = new Spreadsheet.Row()
                .addCell("id")
                .addCell("title")
                .addCell("completed");
        List<Spreadsheet.Row> rows = todos.stream().map(todo -> new Spreadsheet.Row()
                .addCell(todo.getId())
                .addCell(todo.getTitle())
                .addCell(todo.isCompleted()))
                .collect(Collectors.toList());

        return new Spreadsheet("todos", header, rows);
    }

    @ModelAttribute("site")
    public SiteProperties getSiteProperties() {
        return siteProperties;
    }
}

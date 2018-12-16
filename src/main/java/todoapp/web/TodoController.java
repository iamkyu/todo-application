package todoapp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.view.AbstractView;
import todoapp.core.todos.application.TodoFinder;
import todoapp.core.todos.domain.Todo;
import todoapp.web.model.SiteProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class TodoController {
    private final SiteProperties siteProperties;
    private final TodoFinder todoFinder;

    public TodoController(SiteProperties siteProperties, TodoFinder todoFinder) {
        this.siteProperties = siteProperties;
        this.todoFinder = todoFinder;
    }

    @GetMapping("/todos")
    public void getTodos(Model model) {
        model.addAttribute("todos", todoFinder.getAll());
    }

    @ModelAttribute("site")
    public SiteProperties getSiteProperties() {
        return siteProperties;
    }

    @Component("todos")
    public static class TodoCsvView extends AbstractView {
        private final Logger log = LoggerFactory.getLogger(this.getClass());

        public TodoCsvView() {
            setContentType("text/csv");
        }

        @Override
        protected boolean generatesDownloadContent() {
            return true;
        }

        @Override
        protected void renderMergedOutputModel(
                Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
            log.debug("CSV 컨텐츠 출력");

            String fileName = "attachment; filename=\"todos.csv\";";
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, fileName);

            String header = "id, title, completed";
            response.getWriter().println(header);

            List<Todo> todos = (List<Todo>) model.getOrDefault("todos", Collections.emptyList());
            for (Todo item : todos) {
                String line = String.format("%d,%s,%s", item.getId(), item.getTitle(), item.getState().getLiteral());
                response.getWriter().println(line);
            }

            response.flushBuffer();
        }
    }
}

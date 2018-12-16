package todoapp.web;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import todoapp.web.model.SiteProperties;

@Controller
public class TodoController {

    private final Environment environment;

    public TodoController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/todos")
    public void getTodos(Model model) {
        SiteProperties site = new SiteProperties();
        site.setAuthor(environment.getProperty("site.author"));
        model.addAttribute("site", site);
    }
}

package todoapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import todoapp.web.model.SiteProperties;

@Controller
public class TodoController {
    private final SiteProperties siteProperties;

    public TodoController(SiteProperties siteProperties) {
        this.siteProperties = siteProperties;
    }

    @GetMapping("/todos")
    public void getTodos(Model model) {
        model.addAttribute("site", siteProperties);
    }
}

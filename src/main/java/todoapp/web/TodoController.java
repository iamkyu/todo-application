package todoapp.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import todoapp.web.model.SiteProperties;

@Controller
public class TodoController {

    @Value("${site.author}")
    private String author;

    @GetMapping("/todos")
    public void getTodos(Model model) {
        SiteProperties site = new SiteProperties();
        site.setAuthor(author);
        model.addAttribute("site", site);
    }
}

package todoapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import todoapp.web.model.SiteProperties;

@Controller
public class TodoController {

    @GetMapping("/todos")
    public void getTodos(Model model) {
        SiteProperties site = new SiteProperties();
        model.addAttribute("site", site);
    }
}

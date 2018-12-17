package todoapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import todoapp.web.model.SiteProperties;

@Controller
public class UserController {
    private final SiteProperties siteProperties;

    public UserController(SiteProperties siteProperties) {
        this.siteProperties = siteProperties;
    }

    @GetMapping("/login")
    public void getLoginPage() {
    }


    @ModelAttribute("site")
    public SiteProperties getSiteProperties() {
        return siteProperties;
    }
}

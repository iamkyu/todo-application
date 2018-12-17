package todoapp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@Controller
@RequestMapping(value = "/login")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public void getLoginPage() {
    }

    @PostMapping
    public void doLogin(@Valid LoginCommand command) {
        log.debug("command: {}", command);
    }

    private static class LoginCommand {
        @Size(min = 4, max = 20)
        private String username;
        @Size(min = 8)
        private String password;

        private LoginCommand() {
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        // FIXME password mask
        @Override
        public String toString() {
            return "LoginCommand{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}

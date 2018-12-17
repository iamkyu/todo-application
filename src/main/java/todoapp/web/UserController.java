package todoapp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import todoapp.core.user.application.UserJoinder;
import todoapp.core.user.application.UserPasswordVerifier;
import todoapp.core.user.domain.User;
import todoapp.core.user.domain.UserEntityNotFoundException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Size;

@Controller
@RequestMapping(value = "/login")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final UserPasswordVerifier userPasswordVerifier;
    private final UserJoinder userJoinder;

    public UserController(UserPasswordVerifier userPasswordVerifier, UserJoinder userJoinder) {
        this.userPasswordVerifier = userPasswordVerifier;
        this.userJoinder = userJoinder;
    }

    @GetMapping
    public void getLoginPage() {
    }

    @PostMapping
    public String doLogin(HttpSession httpSession, @Valid LoginCommand command) {
        log.debug("command: {}", command);

        // TODO 서버가 두 대 이상이라면?
        try {
            httpSession.setAttribute(User.class.toString(),
                    userPasswordVerifier.verify(command.getUsername(), command.getPassword()));
        } catch (UserEntityNotFoundException e) {
            httpSession.setAttribute(User.class.toString(),
                    userJoinder.join(command.getUsername(), command.getPassword()));
        }

        return "redirect:/todos";
    }

    @ExceptionHandler(BindException.class)
    public void handleBindException(BindException exception, Model model) {
        //TODO 메세지 하드코딩하지 않고 messageSource로부터 얻어 처리
        //TODO Error.{ExceptionClassName} 이라는 규칙이 일관되게 적용되려면?
        model.addAttribute("bindingResult", exception.getBindingResult());
        model.addAttribute("message", "사용자 이름 또는 비밀번호가 올바르지 않습니다.");
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

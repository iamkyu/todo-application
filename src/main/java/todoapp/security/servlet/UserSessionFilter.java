package todoapp.security.servlet;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import todoapp.core.user.domain.User;
import todoapp.security.UserSession;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Objects;

@Component
public class UserSessionFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new UserSessionHttpServletRequestWrapper(request), response);
    }

    private class UserSessionHttpServletRequestWrapper extends HttpServletRequestWrapper {

        public UserSessionHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public boolean isUserInRole(String role) {
            UserSession session = getUserSession();
            if (Objects.nonNull(session)) {
                return session.hasRole(role);
            }
            return false;
        }

        @Override
        public Principal getUserPrincipal() {
            return getUserSession();
        }

        private UserSession getUserSession() {
            User user = (User) RequestContextHolder.getRequestAttributes()
                    .getAttribute(User.class.toString(), RequestAttributes.SCOPE_SESSION);
            if (Objects.nonNull(user)) {
                return new UserSession(user);
            }
            return null;
        }
    }

}

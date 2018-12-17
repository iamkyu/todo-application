package todoapp.security.web.method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import todoapp.security.AccessDeniedException;
import todoapp.security.UnauthorizedAccessException;
import todoapp.security.support.RolesAllowedSupport;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class RolesVerifyInterceptor implements HandlerInterceptor, RolesAllowedSupport {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RolesAllowed annotation = getRolesAllowed(handler);
        if (Objects.nonNull(annotation)) {
            if (Objects.isNull(request.getUserPrincipal())) {
                throw new UnauthorizedAccessException();
            }

            for (String role : annotation.value()) {
                if (request.isUserInRole(role)) {
                    return true;
                }
            }
            throw new AccessDeniedException();


            // request.getUserPrincipal();
            // request.isUserInRole(role);

   /*
   User logindUser = (User) request.getSession().getAttribute(User.class.toString());
   if (Objects.isNull(logindUser)) {
    throw new UnauthorizedAccessException();
   }

   UserSession session = new UserSession(logindUser);
   for(String role : annotation.value()) {
    if (session.hasRole(role)) {
     return true;
    }
   }
   throw new AccessDeniedException();
   */
        }
        return true;
    }

}

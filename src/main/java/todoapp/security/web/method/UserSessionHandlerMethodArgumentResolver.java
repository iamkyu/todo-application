package todoapp.security.web.method;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import todoapp.core.user.domain.User;
import todoapp.security.UserSession;

import java.util.Objects;

public class UserSessionHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return UserSession.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        User loggedInUser = (User) webRequest.getAttribute(User.class.toString(), RequestAttributes.SCOPE_SESSION);
        if (Objects.nonNull(loggedInUser)) {
            return new UserSession(loggedInUser);
        }
        return null;
    }
}

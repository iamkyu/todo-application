package todoapp.commons.web.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import todoapp.commons.util.ThrowableUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ReadableErrorAttributes implements ErrorAttributes, HandlerExceptionResolver, Ordered {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private DefaultErrorAttributes errorAttributes;
    private MessageSource messageSource;

    public ReadableErrorAttributes(MessageSource messageSource) {
        this.errorAttributes = new DefaultErrorAttributes();
        this.messageSource = messageSource;
    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(webRequest, includeStackTrace);
        Throwable error = getError(webRequest);
        if (Objects.nonNull(error)) {
            String message;
            if (ClassUtils.isAssignableValue(MessageSourceResolvable.class, error)) {
                message = messageSource.getMessage((MessageSourceResolvable) error, webRequest.getLocale());
            } else {
                String errorCode = String.format("Error.%s", error.getClass().getSimpleName());
                message = messageSource.getMessage(errorCode, new Object[0], null, webRequest.getLocale());
            }

            if (StringUtils.hasText(message)) {
                errorAttributes.put("message", message);
            }

            BindingResult bindingResult = ThrowableUtils.extractBindingResult(error);
            if (Objects.nonNull(bindingResult)) {
                errorAttributes.put("errors", bindingResult.getAllErrors()
                        .stream()
                        .map(code -> messageSource.getMessage(code, webRequest.getLocale()))
                        .collect(Collectors.toList()));
            }
        }

        return errorAttributes;
    }

    @Override
    public Throwable getError(WebRequest webRequest) {
        return errorAttributes.getError(webRequest);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public ModelAndView resolveException(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        return errorAttributes.resolveException(request, response, handler, ex);
    }
}

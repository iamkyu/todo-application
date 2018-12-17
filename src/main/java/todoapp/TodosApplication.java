package todoapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import todoapp.security.web.method.RolesVerifyInterceptor;
import todoapp.security.web.method.UserSessionHandlerMethodArgumentResolver;
import todoapp.web.view.CsvView;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TodosApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodosApplication.class, args);
    }


    @Autowired
    public void configContentNegotiation(ContentNegotiatingViewResolver viewResolver) {
        List<View> defaultViews = new ArrayList<>(viewResolver.getDefaultViews());
        defaultViews.add(new CsvView());
        viewResolver.setDefaultViews(defaultViews);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
                resolvers.add(new UserSessionHandlerMethodArgumentResolver());
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new RolesVerifyInterceptor());
            }
        };
    }
}


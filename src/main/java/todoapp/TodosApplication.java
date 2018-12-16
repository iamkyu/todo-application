package todoapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
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
}


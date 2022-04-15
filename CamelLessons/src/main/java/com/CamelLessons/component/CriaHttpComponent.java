package com.CamelLessons.component;

import org.apache.camel.component.http.HttpComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CriaHttpComponent {

    @Bean({"http", "https"})
    public HttpComponent httpComponent() {
        return new HttpComponent();
    }
}

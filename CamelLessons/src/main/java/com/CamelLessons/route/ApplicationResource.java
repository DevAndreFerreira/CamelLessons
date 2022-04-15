package com.CamelLessons.route;

import com.CamelLessons.component.CriaHttpComponent;
import com.CamelLessons.model.Employee;
import com.CamelLessons.processor.CreateEmployeeProcessor;
import com.CamelLessons.processor.MyProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class ApplicationResource extends RouteBuilder {

    @Autowired
    private CriaHttpComponent httpComponent;

    @Override
    public void configure() throws Exception {

        restConfiguration().component("servlet").port(9090).host("localhost").bindingMode(RestBindingMode.json);

        rest().get("/hello-world").produces(MediaType.APPLICATION_JSON_VALUE).route()
                .setBody(constant("Deu bom"))
                .process(exchange -> {
                    System.out.println("");
                }).endRest();

        rest().get("/teste").produces(MediaType.APPLICATION_JSON_VALUE).route()
                .setHeader(Exchange.HTTP_METHOD, simple("GET"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader(Exchange.HTTP_URI, constant("http://localhost:8080/employee?id=5"))
                .process(exchange -> {
                    System.out.println();
                })
                //.unmarshal().json(JsonLibrary.Jackson)
                .process(new MyProcessor());

        rest().post("/teste").type(Employee.class)
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE).route()
                .marshal().json(JsonLibrary.Jackson)
                .setHeader(Exchange.HTTP_METHOD, simple("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader(Exchange.HTTP_URI, constant("http://localhost:8080/employee"))
                .process(exchange -> {
                    System.out.println();
                })
                .unmarshal().json(JsonLibrary.Jackson)
                .process(new MyProcessor())
                .removeHeader("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, simple("GET"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader(Exchange.HTTP_URI, constant("http://localhost:8080/employee?id=5"))
                .process(exchange -> {
                    System.out.println();
                });
    }
}

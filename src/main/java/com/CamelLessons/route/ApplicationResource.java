package com.CamelLessons.route;

import com.CamelLessons.model.Employee;
import com.CamelLessons.processor.DeuRuimProcessor;
import com.CamelLessons.processor.MyProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class ApplicationResource extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration().component("servlet").port(9090).host("localhost").bindingMode(RestBindingMode.json);

        rest().get("/hello-world").produces(MediaType.APPLICATION_JSON_VALUE).route()
                .setBody(constant("Deu bom"))
                .process(exchange -> {
                    System.out.println("");
                }).endRest();

        rest().post("/teste").type(Employee.class)
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE).route()
                .marshal().json(JsonLibrary.Jackson)
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .streamCaching()
                .to("http://localhost:8080/employee?bridgeEndpoint=true")
                .choice().when().simple("${header.CamelHttpResponseCode} == '500'")
                .process(exchange -> {
                    System.out.println("");
                })
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .to("http://localhost:8080/employee?bridgeEndpoint=true&id=5")
                .process(exchange -> {
                    System.out.println();
                })
                .unmarshal().json(JsonLibrary.Jackson)
                .process(new MyProcessor())
                .otherwise()
                .process(new DeuRuimProcessor());

    }
}

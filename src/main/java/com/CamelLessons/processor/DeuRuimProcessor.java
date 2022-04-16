package com.CamelLessons.processor;

import org.apache.camel.Exchange;

public class DeuRuimProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("Deu ruim");
    }
}

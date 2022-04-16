package com.CamelLessons.processor;

import com.CamelLessons.dto.ErroExceptionDTO;
import org.apache.camel.Exchange;

public class DeuRuimProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setBody(new ErroExceptionDTO("Deu Ruim"));
    }
}

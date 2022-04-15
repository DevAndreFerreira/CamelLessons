package com.CamelLessons.processor;


import com.CamelLessons.model.Employee;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class CreateEmployeeProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Employee emp = new Employee();
        emp.setName("camel-employee");
        emp.setDesignation("camel-manager");
        emp.setEmpId(111);
        emp.setSalary(30000);
        exchange.getIn().setBody(emp);
    }
}


package com.CamelLessons.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErroExceptionDTO {

    private String deuRuim;
    public ErroExceptionDTO(String deuRuim) {
        this.deuRuim = deuRuim;
    }
}

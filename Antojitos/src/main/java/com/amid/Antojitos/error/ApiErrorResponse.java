package com.amid.Antojitos.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiErrorResponse {

    private int error;
    private String mensaje;
}

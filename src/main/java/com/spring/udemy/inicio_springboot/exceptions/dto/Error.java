package com.spring.udemy.inicio_springboot.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Error {
    private String mensaje;
    private LocalDate fecha;
    private int status;

}

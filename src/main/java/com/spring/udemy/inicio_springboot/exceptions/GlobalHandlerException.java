package com.spring.udemy.inicio_springboot.exceptions;

import com.spring.udemy.inicio_springboot.exceptions.dto.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Error> manejoDeRuta(NoHandlerFoundException ex){
        Error errorDTO = new Error(
                "Ruta no encontrada" + ex.getRequestURL(),
                LocalDate.now(),
                HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> errorGlobal(Exception ex){
        Error error = new Error(ex.getMessage(), LocalDate.now(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

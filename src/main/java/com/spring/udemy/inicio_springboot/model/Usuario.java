package com.spring.udemy.inicio_springboot.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor // -> constructor con todos los atributos
@NoArgsConstructor // -> constructor sin atributos - vacio
public class Usuario {
    private int id;
    private String nombre;
    private String apellido;
}

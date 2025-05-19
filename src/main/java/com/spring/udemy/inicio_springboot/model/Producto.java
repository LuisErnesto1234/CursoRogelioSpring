package com.spring.udemy.inicio_springboot.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String descripcion;
    @Column(name = "url_imagen")
    private String imagen;
    private Double precio;
    private Integer cantidad;
    private Double total;
}

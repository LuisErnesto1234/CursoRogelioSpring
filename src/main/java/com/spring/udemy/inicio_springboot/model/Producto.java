package com.spring.udemy.inicio_springboot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 3, message = "El nombre debe tener 3 caracteres como minimo")
    private String nombre;
    @NotBlank(message = "La descripcion no puede estar vacia")
    private String descripcion;
    @Column(name = "url_imagen")
    private String imagen;
    @Min(value = 1, message = "El precio debe ser mayor a 1")
    @NotNull(message = "El precio no puede estar vacio")
    private Double precio;
    @Min(value = 1, message = "La cantidad debe ser mayor a 1")
    private Integer cantidad;
    private Double total;
}

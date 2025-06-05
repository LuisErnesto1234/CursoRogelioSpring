package com.spring.udemy.inicio_springboot.model;

import com.spring.udemy.inicio_springboot.model.enums.EstadoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<FacturaItem> items = new ArrayList<>();

    private LocalDateTime fechaFactura;
    private Double total;
    @Enumerated(EnumType.STRING)
    private EstadoEnum estadoEnum; // "PENDIENTE", "COMPLETADO", etc.
}

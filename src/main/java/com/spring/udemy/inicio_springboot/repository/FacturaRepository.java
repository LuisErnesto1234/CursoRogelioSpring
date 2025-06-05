package com.spring.udemy.inicio_springboot.repository;

import com.spring.udemy.inicio_springboot.model.Factura;
import com.spring.udemy.inicio_springboot.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer> {
    List<Factura> findByUsuario(Usuario usuario);
}

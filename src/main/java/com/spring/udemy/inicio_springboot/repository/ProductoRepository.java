package com.spring.udemy.inicio_springboot.repository;

import com.spring.udemy.inicio_springboot.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    Page<Producto> findAll(Pageable pageable);
    List<Producto> findAllByNombreContainsIgnoreCase(String nombre);
    List<Producto> findAllByCategoria_Id(Integer id);
}
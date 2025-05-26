package com.spring.udemy.inicio_springboot.repository;

import com.spring.udemy.inicio_springboot.model.Categoria;
import com.spring.udemy.inicio_springboot.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}

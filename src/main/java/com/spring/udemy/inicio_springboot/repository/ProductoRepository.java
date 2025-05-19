package com.spring.udemy.inicio_springboot.repository;

import com.spring.udemy.inicio_springboot.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
package com.spring.udemy.inicio_springboot.repository;

import com.spring.udemy.inicio_springboot.model.CarritoCompras;
import com.spring.udemy.inicio_springboot.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarritoComprasRepository extends JpaRepository<CarritoCompras, Integer> {

    Optional<CarritoCompras> findByUsuario(Usuario usuario);
}

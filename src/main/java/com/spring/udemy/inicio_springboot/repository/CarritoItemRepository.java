package com.spring.udemy.inicio_springboot.repository;

import com.spring.udemy.inicio_springboot.model.CarritoCompras;
import com.spring.udemy.inicio_springboot.model.CarritoItem;
import com.spring.udemy.inicio_springboot.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarritoItemRepository extends JpaRepository<CarritoItem, Integer> {
    List<CarritoItem> findByCarritoCompras(CarritoCompras carritoCompras);
    Optional<CarritoItem> findByCarritoComprasAndProducto(CarritoCompras cart, Producto producto);
    void deleteAllByCarritoCompras(CarritoCompras cart);
}

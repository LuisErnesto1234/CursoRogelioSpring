package com.spring.udemy.inicio_springboot.service;

import com.spring.udemy.inicio_springboot.model.Producto;
import com.spring.udemy.inicio_springboot.model.Usuario;
import com.spring.udemy.inicio_springboot.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

@Service
public class ProductoService{

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> listarProductos(){
        return productoRepository.findAll();
    }

    public void eliminarProducto(Integer id){
        productoRepository.deleteById(id);
    }

    public Producto guardarProducto(Producto producto){
        producto.setTotal(producto.getCantidad() * producto.getPrecio());
        return productoRepository.save(producto);
    }

    public Optional<Producto> buscarProductoPorId(Integer id){
        return productoRepository.findById(id);
    }
}

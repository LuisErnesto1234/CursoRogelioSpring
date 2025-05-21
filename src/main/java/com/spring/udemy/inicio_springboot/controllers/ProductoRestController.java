package com.spring.udemy.inicio_springboot.controllers;

import com.spring.udemy.inicio_springboot.model.Producto;
import com.spring.udemy.inicio_springboot.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
public class ProductoRestController {

    private final ProductoService productoService;

    public ProductoRestController(ProductoService productoService) {
        this.productoService = productoService;
    }

//    @GetMapping
//    public ResponseEntity<List<Producto>> listarProductos(){
//        return ResponseEntity.ok(productoService.listarProductos());
//    }

    @PostMapping
    public ResponseEntity<Producto> guardarProducto(@RequestBody Producto producto){
        productoService.guardarProducto(producto);
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id){
        if (productoService.buscarProductoPorId(id).isPresent()){
            productoService.eliminarProducto(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Integer id, @RequestBody Producto producto){
        if (productoService.buscarProductoPorId(id).isPresent()){
            return ResponseEntity.ok(productoService.guardarProducto(producto));
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}

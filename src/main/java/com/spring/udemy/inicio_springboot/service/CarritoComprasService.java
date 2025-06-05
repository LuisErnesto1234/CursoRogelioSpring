package com.spring.udemy.inicio_springboot.service;

import com.spring.udemy.inicio_springboot.model.CarritoCompras;
import com.spring.udemy.inicio_springboot.model.CarritoItem;
import com.spring.udemy.inicio_springboot.model.Producto;
import com.spring.udemy.inicio_springboot.model.Usuario;
import com.spring.udemy.inicio_springboot.repository.CarritoComprasRepository;
import com.spring.udemy.inicio_springboot.repository.CarritoItemRepository;
import com.spring.udemy.inicio_springboot.repository.ProductoRepository;
import com.spring.udemy.inicio_springboot.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CarritoComprasService {

    private final CarritoComprasRepository carritoComprasRepository;
    private final CarritoItemRepository carritoItemRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    public CarritoComprasService(CarritoComprasRepository carritoComprasRepository, CarritoItemRepository carritoItemRepository, ProductoRepository productoRepository, UsuarioRepository usuarioRepository) {
        this.carritoComprasRepository = carritoComprasRepository;
        this.carritoItemRepository = carritoItemRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public CarritoCompras carritoComprasActual() {
        Usuario usuario = usuarioRepository.findById(1).orElseThrow();
        CarritoCompras carritoCompras = carritoComprasRepository.findByUsuario(usuario).orElse(null);

        if (carritoCompras == null) {
            carritoCompras = new CarritoCompras();
            carritoCompras.setUsuario(usuario);
            carritoComprasRepository.save(carritoCompras);
        }

        return carritoCompras;
    }

    public void agregarProductoCarrito(Integer productoId, int cantidad) {
        CarritoCompras carritoCompras = carritoComprasActual();
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        CarritoItem carritoItem = carritoItemRepository.findByCarritoComprasAndProducto(carritoCompras, producto).orElse(null);

        if (carritoItem != null) {
            carritoItem.setCantidad(carritoItem.getCantidad() + cantidad);
        } else {
            carritoItem = new CarritoItem();
            carritoItem.setCarritoCompras(carritoCompras);
            carritoItem.setProducto(producto);
            carritoItem.setCantidad(cantidad);
        }

        carritoItemRepository.save(carritoItem);
    }

    public void actualizarCantidad(Integer productId, int cantidad) {
        CarritoCompras cart = carritoComprasActual();
        Producto product = productoRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        CarritoItem cartItem = carritoItemRepository.findByCarritoComprasAndProducto(cart, product).orElse(null);

        if (cartItem != null) {
            if (cantidad <= 0) {
                carritoItemRepository.delete(cartItem);
            } else {
                cartItem.setCantidad(cantidad);
                carritoItemRepository.save(cartItem);
            }
        }
    }

    public void eliminarProducto(Integer productId) {
        CarritoCompras cart = carritoComprasActual();
        Producto product = productoRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        CarritoItem cartItem = carritoItemRepository.findByCarritoComprasAndProducto(cart, product).orElse(null);

        if (cartItem != null) {
            carritoItemRepository.delete(cartItem);
        }
    }

    public void limpiarCarrito() {
        CarritoCompras cart = carritoComprasActual();
        carritoItemRepository.deleteAllByCarritoCompras(cart);
    }

    public double getTotal() {
        CarritoCompras cart = carritoComprasActual();
        List<CarritoItem> items = carritoItemRepository.findByCarritoCompras(cart);

        return items.stream()
                .mapToDouble(item -> item.getProducto().getPrecio() * item.getCantidad())
                .sum();
    }
}

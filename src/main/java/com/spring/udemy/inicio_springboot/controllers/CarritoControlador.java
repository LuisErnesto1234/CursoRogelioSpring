package com.spring.udemy.inicio_springboot.controllers;

import com.spring.udemy.inicio_springboot.model.CarritoCompras;
import com.spring.udemy.inicio_springboot.model.CarritoItem;
import com.spring.udemy.inicio_springboot.repository.ProductoRepository;
import com.spring.udemy.inicio_springboot.service.CarritoComprasService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// CartController.java
@Controller
@RequestMapping("/carrito")
public class CarritoControlador {

    private final CarritoComprasService cartService;
    private final ProductoRepository productRepository;

    public CarritoControlador(CarritoComprasService cartService, ProductoRepository productRepository) {
        this.cartService = cartService;
        this.productRepository = productRepository;
    }

    @GetMapping
    public String viewCart(Model model) {
        CarritoCompras cart = cartService.carritoComprasActual();
        List<CarritoItem> items = cart.getItems();
        double total = cartService.getTotal();

        model.addAttribute("cartItems", items);
        model.addAttribute("total", total);

        return "carrito/vista";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Integer productId,
                            @RequestParam(defaultValue = "1") int quantity) {
        cartService.agregarProductoCarrito(productId, quantity);
        return "redirect:/carrito";
    }

    @PostMapping("/update")
    public String updateCartItem(@RequestParam Integer productId,
                                 @RequestParam int quantity) {
        cartService.actualizarCantidad(productId, quantity);
        return "redirect:/carrito";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Integer productId) {
        cartService.eliminarProducto(productId);
        return "redirect:/carrito";
    }

    @PostMapping("/clear")
    public String clearCart() {
        cartService.limpiarCarrito();
        return "redirect:/carrito";
    }
}

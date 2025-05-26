package com.spring.udemy.inicio_springboot.controllers;

import com.spring.udemy.inicio_springboot.model.Producto;
import com.spring.udemy.inicio_springboot.service.CategoriaService;
import com.spring.udemy.inicio_springboot.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public ProductoController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listarProductos(Model model,
                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "5") int size){
        Pageable paginable = PageRequest.of(page, size, Sort.by("nombre").ascending());
        Page<Producto> productoPage = productoService.listarProductos(paginable);
        model.addAttribute("lista", productoPage);
        model.addAttribute("paginaActual", page);
        return "producto";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@Valid @ModelAttribute Producto producto, BindingResult result, @RequestParam(name = "file") MultipartFile file,
                                  RedirectAttributes attr) throws IOException {
        
        if (result.hasErrors()){
            return "producto-formulario";
        }

        productoService.tratamientoImagen(producto, file);

        attr.addFlashAttribute("bien", "El producto se guardo con exito!");
        productoService.guardarProducto(producto);
        return "redirect:/producto";
    }

    @GetMapping("/nuevo")
    public String nuevoProducto(Model model){
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.listarCategorias());
        return "producto-formulario";
    }

    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Integer id, Model model){
        productoService.buscarProductoPorId(id).ifPresent(producto -> model.addAttribute("producto", producto));
        model.addAttribute("categorias", categoriaService.listarCategorias());
        return "producto-formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id){
        productoService.eliminarImagen(id);
        return "redirect:/producto";
    }

    @GetMapping("/ver/{id}")
    public String verProducto(@PathVariable Integer id, Model model){
        productoService.buscarProductoPorId(id).ifPresent(producto -> model.addAttribute("producto", producto));
        return "ver-producto";
    }

    @PostMapping("/buscar")
    public String buscarProductoPorNombre(@RequestParam(name = "nombre") String nombre, Model model){
        model.addAttribute("listaP", productoService.productoListPorNombre(nombre));
        return "index";
    }

    @GetMapping("/buscar-categoria/{id}")
    public String buscarPorCategoria(@PathVariable Integer id, Model model){
        model.addAttribute("categoria", categoriaService.buscarCategoriaPorId(id).orElseThrow());
        model.addAttribute("lista", productoService.buscarPorCategoriaProducto(id));
        return "buscar-categoria/buscar-categoria";
    }
}

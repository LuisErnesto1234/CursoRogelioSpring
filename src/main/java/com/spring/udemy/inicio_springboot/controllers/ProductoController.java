package com.spring.udemy.inicio_springboot.controllers;

import com.spring.udemy.inicio_springboot.model.Producto;
import com.spring.udemy.inicio_springboot.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public String listarProductos(Model model){
        model.addAttribute("lista", productoService.listarProductos());
        return "producto";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@Valid @ModelAttribute Producto producto, BindingResult result, @RequestParam(name = "file") MultipartFile file, Model model) throws IOException {
        
        if (result.hasErrors()){
            return "producto-formulario";
        }

        if (file.isEmpty() && producto.getId() != null){
            Producto productoEncontrado = productoService.buscarProductoPorId(producto.getId()).orElse(null);
            //Cuando el usuario edita, cualquier campo pero no toca la imagen
            //mantiene la imagen actual
            if (productoEncontrado != null){
                producto.setImagen(productoEncontrado.getImagen());
            }
        }
        else if (!file.isEmpty()) {
            //Capturamos la ruta base del proyecto :V
            String rutaBase = System.getProperty("user.dir");

            //TODO Ruta donde se va a guardar la imagen
            String rutaImagen = rutaBase + File.separator + "uploads" + File.separator + "producto";

            File carpetaGuardado = new File(rutaImagen);
            if (!carpetaGuardado.exists()){
                boolean crearCarpeta = carpetaGuardado.mkdirs();
                if (!crearCarpeta){
                    throw new IOException("No se pudo crear la carpeta de imagenes!!");
                }
            }

            //TODO Generamos un nombreSeguroImagen seguro para la imagen
            String nombreSeguroImagen = System.currentTimeMillis() + "-" + file.getOriginalFilename();

            //TODO: creamos un path = ruta
            Path ruta = Paths.get(rutaImagen, nombreSeguroImagen);

            //TODO: guardamos la imagen a en la ruta Path
            file.transferTo(ruta.toFile());

            //TODO: seteamos la ruta de la imagen en el producto
            producto.setImagen("/uploads/producto/" + nombreSeguroImagen);
        }

        //TODO: Se crea un nuevo producto pero no se sube la imagen
        if (file.isEmpty() && producto.getImagen() == null){
            producto.setImagen("/uploads/producto/default.jpg");
        }
        producto.setTotal(producto.getCantidad() * producto.getPrecio());
        productoService.guardarProducto(producto);
        return "redirect:/producto";
    }

    @GetMapping("/nuevo")
    public String nuevoProducto(Model model){
        model.addAttribute("producto", new Producto());
        return "producto-formulario";
    }

    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Integer id, Model model){
        productoService.buscarProductoPorId(id).ifPresent(producto -> model.addAttribute("producto", producto));
        return "producto-formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id){

        if (id != null){
            Producto productoEncontrado = productoService.buscarProductoPorId(id).orElse(null);
            if (productoEncontrado != null && productoEncontrado.getImagen() != null){
                if (productoEncontrado.getImagen().equals("/uploads/producto/default.jpg")){
                    productoService.eliminarProducto(id);
                }
                else {
                    Path rutaImagen = Paths.get(System.getProperty("user.dir"), productoEncontrado.getImagen());
                    File file = rutaImagen.toFile();

                    if (file.exists()) {
                        boolean eliminar = file.delete();
                        if (!eliminar) {
                            throw new RuntimeException("No se pudo eliminar la imagen!!");
                        }
                    }
                }
            }
            productoService.eliminarProducto(id);
        }

        return "redirect:/producto";
    }

    @GetMapping("/ver/{id}")
    public String verProducto(@PathVariable Integer id, Model model){
        productoService.buscarProductoPorId(id).ifPresent(producto -> model.addAttribute("producto", producto));
        return "ver-producto";
    }

}

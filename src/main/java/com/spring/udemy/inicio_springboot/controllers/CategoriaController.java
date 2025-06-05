package com.spring.udemy.inicio_springboot.controllers;

import com.spring.udemy.inicio_springboot.model.Categoria;
import com.spring.udemy.inicio_springboot.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping({"/categoria", "/c"})
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String mostrarCategorias(Model model){
        model.addAttribute("categorias", categoriaService.listarCategorias());
        return "categoria/lista-categoria";
    }

    @PostMapping
    public String guardarCategoria(@Valid @ModelAttribute Categoria categoria, BindingResult result, @RequestParam(name = "file") MultipartFile file) throws IOException {

        if (result.hasErrors()){
            return "categoria/formulario";
        }

        categoriaService.tratamientoImagen(categoria, file);

        categoriaService.guardarCategoria(categoria);
        return "redirect:/categoria";
    }

    @GetMapping("/nuevo")
    public String nuevaCategoria(Model model){
        model.addAttribute("categoria", new Categoria());
        return "categoria/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editarCategoria(@PathVariable Integer id, Model model){
        categoriaService.buscarCategoriaPorId(id).ifPresent(categoria -> model.addAttribute("categoria", categoria));
        return "categoria/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Integer id, RedirectAttributes attr){
        categoriaService.eliminarImagen(id);
        attr.addFlashAttribute("bien", "La categoria se elimino con exito!");
        return "redirect:/categoria";
    }
}

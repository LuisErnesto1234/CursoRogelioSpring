package com.spring.udemy.inicio_springboot.controllers;

import com.spring.udemy.inicio_springboot.model.Usuario;
import com.spring.udemy.inicio_springboot.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

//    @Autowired
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String mostrarUsuarios(Model model){
        model.addAttribute("lista", usuarioService.findAll());
        return "usuario";
    }

    @PostMapping
    public String guardarUsuario(@ModelAttribute Usuario usuario){
        usuarioService.saveUsuario(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/nuevo")
    public String nuevoUsuario(Model model){
        model.addAttribute("usuario", new Usuario());
        return "formulario";
    }
}

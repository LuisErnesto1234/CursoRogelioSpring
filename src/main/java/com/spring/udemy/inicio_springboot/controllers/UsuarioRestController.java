package com.spring.udemy.inicio_springboot.controllers;

import com.spring.udemy.inicio_springboot.model.Usuario;
import com.spring.udemy.inicio_springboot.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

    private final UsuarioService usuarioService;

    public UsuarioRestController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> usuarios(){
        return usuarioService.findAll();
    }

    @PostMapping
    public Usuario guardarUsuario(@RequestBody Usuario usuario){
        usuarioService.saveUsuario(usuario);
        return usuario;
    }

}

package com.spring.udemy.inicio_springboot.repository;

import com.spring.udemy.inicio_springboot.model.Usuario;

import java.util.List;

public interface UsuarioRepository {
    List<Usuario> findAll();
    void saveUsuario(Usuario usuario);
}

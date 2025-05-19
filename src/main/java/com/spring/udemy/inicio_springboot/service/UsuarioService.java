package com.spring.udemy.inicio_springboot.service;

import com.spring.udemy.inicio_springboot.model.Usuario;
import com.spring.udemy.inicio_springboot.repository.UsuarioRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UsuarioService implements UsuarioRepository{

    List<Usuario> lista = new ArrayList<>();

    @Override
    public List<Usuario> findAll(){
        return lista;
    }

    @Override
    public void saveUsuario(Usuario usuario) {
        lista.add(usuario);
    }
}

package com.spring.udemy.inicio_springboot.service;

import com.spring.udemy.inicio_springboot.model.Categoria;
import com.spring.udemy.inicio_springboot.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarCategorias(){
        return Collections.unmodifiableList(categoriaRepository.findAll());
    }

    public Optional<Categoria> buscarCategoriaPorId(Integer id){
        return categoriaRepository.findById(id);
    }

    public void eliminarCategoria(Integer id){
        categoriaRepository.deleteById(id);
    }

    public Categoria guardarCategoria(Categoria categoria){
        return categoriaRepository.save(categoria);
    }
}

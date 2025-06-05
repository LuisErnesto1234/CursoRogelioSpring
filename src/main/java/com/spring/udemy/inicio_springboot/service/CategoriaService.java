package com.spring.udemy.inicio_springboot.service;

import com.spring.udemy.inicio_springboot.model.Categoria;
import com.spring.udemy.inicio_springboot.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public void tratamientoImagen(Categoria categoria, MultipartFile file) throws IOException {
        if (file.isEmpty() && categoria.getId() != null){
            Categoria categoriaEncontrado = buscarCategoriaPorId(categoria.getId()).orElse(null);
            //Cuando el usuario edita, cualquier campo pero no toca la imagen
            //mantiene la imagen actual
            if (categoriaEncontrado != null){
                categoria.setImagenUrl(categoriaEncontrado.getImagenUrl());
            }
        }
        else if (!file.isEmpty()) {
            //Capturamos la ruta base del proyecto :V
            String rutaBase = System.getProperty("user.dir");

            //TODO Ruta donde se va a guardar la imagen
            String rutaImagen = rutaBase + File.separator + "uploads" + File.separator + "categoria";

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
            categoria.setImagenUrl("/uploads/categoria/" + nombreSeguroImagen);
        }

        //TODO: Se crea un nuevo producto pero no se sube la imagen
        if (file.isEmpty() && categoria.getImagenUrl() == null){
            categoria.setImagenUrl("/uploads/categoria/default.jpg");
        }
    }

    public void eliminarImagen(Integer id){
        if (id != null){
            Categoria categoriaEncontrado = buscarCategoriaPorId(id).orElse(null);
            if (categoriaEncontrado != null && categoriaEncontrado.getImagenUrl() != null){
                if (categoriaEncontrado.getImagenUrl().equals("/uploads/categoria/default.jpg")){
                    eliminarCategoria(id);
                }
                else {
                    Path rutaImagen = Paths.get(System.getProperty("user.dir"), categoriaEncontrado.getImagenUrl());
                    File file = rutaImagen.toFile();

                    if (file.exists()) {
                        boolean eliminar = file.delete();
                        if (!eliminar) {
                            throw new RuntimeException("No se pudo eliminar la imagen!!");
                        }
                    }
                }
            }
            eliminarCategoria(id);
        }
    }
}

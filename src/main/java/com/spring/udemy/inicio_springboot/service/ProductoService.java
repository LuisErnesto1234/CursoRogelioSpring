package com.spring.udemy.inicio_springboot.service;

import com.spring.udemy.inicio_springboot.model.Producto;
import com.spring.udemy.inicio_springboot.repository.ProductoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService{

    private final ProductoRepository productoRepository;
    public final List<Producto> carritoList = new ArrayList<>();

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Page<Producto> listarProductos(Pageable pageable){
        return productoRepository.findAll(pageable);
    }

    public void eliminarProducto(Integer id){
        productoRepository.deleteById(id);
    }

    public Producto guardarProducto(Producto producto){
        producto.setTotal(producto.getCantidad() * producto.getPrecio());
        return productoRepository.save(producto);
    }

    public Optional<Producto> buscarProductoPorId(Integer id){
        return productoRepository.findById(id);
    }

    public void tratamientoImagen(Producto producto, MultipartFile file) throws IOException{
        if (file.isEmpty() && producto.getId() != null){
            Producto productoEncontrado = buscarProductoPorId(producto.getId()).orElse(null);
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
    }

    public void eliminarImagen(Integer id){
        if (id != null){
            Producto productoEncontrado = buscarProductoPorId(id).orElse(null);
            if (productoEncontrado != null && productoEncontrado.getImagen() != null){
                if (productoEncontrado.getImagen().equals("/uploads/producto/default.jpg")){
                    eliminarProducto(id);
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
            eliminarProducto(id);
        }
    }

    public List<Producto> productoListPorNombre(String nombre){
        return productoRepository.findAllByNombreContainsIgnoreCase(nombre);
    }

    public List<Producto> buscarPorCategoriaProducto(Integer id){
        return productoRepository.findAllByCategoria_Id(id);
    }

    public void carrito(Producto producto){
        carritoList.add(producto);
    }
}

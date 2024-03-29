/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vineyarg.demo.controladores;

import com.vineyarg.demo.entidades.Imagenes;
import com.vineyarg.demo.entidades.Producto;
import com.vineyarg.demo.entidades.Productor;
import com.vineyarg.demo.entidades.Usuario;
import com.vineyarg.demo.errores.Excepcion;
import com.vineyarg.demo.repositorios.ImagenesRepositorio;
import com.vineyarg.demo.repositorios.ProductoRepositorio;
import com.vineyarg.demo.repositorios.ProductorRepositorio;
import com.vineyarg.demo.repositorios.UsuarioRepositorio;
import com.vineyarg.demo.servicios.ProductoServicio;
import com.vineyarg.demo.servicios.ProductorServicio;
import com.vineyarg.demo.servicios.UsuarioServicio;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class ImagenesControlador {

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    ProductorServicio productorServicio;

    @Autowired
    private ProductorRepositorio productorRepositorio;

    @Autowired
    private ImagenesRepositorio imagenesRepositorio;

    //EL DE ABAJO MUESTRA SOLO LA PRIMERA IMAGEN DEL PRODUCTO
    @GetMapping("/productoimagen/{idProducto}")
    public ResponseEntity<byte[]> fotoProductoPorIdProducto(@PathVariable String idProducto) {

        try {
            Producto producto = productoRepositorio.getById(idProducto);

            if (producto.getImagenes().isEmpty()) {
                throw new Excepcion("Producto sin imágen");
            } else {

                Set<Imagenes> imagenes = new HashSet();

                imagenes = producto.getImagenes();

                for (Imagenes imagenFor : imagenes) {

                    byte[] imagen = imagenFor.getContenido();
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.IMAGE_JPEG);

                    return new ResponseEntity<>(imagen, headers, HttpStatus.OK);

                }

//               
            }
        } catch (Excepcion e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        return null;
    }

    //ESTE MUESTRA TODAS LAS IMAGENES QUE EL PRODUCTO TIENE CARGADAS    
    @GetMapping("/productoimagen-idImagen/{idImagen}")
    public ResponseEntity<byte[]> fotoProducto(@PathVariable String idImagen) {

        Imagenes imagen = imagenesRepositorio.getById(idImagen);
        byte[] foto = imagen.getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(foto, headers, HttpStatus.OK);

    }

    @GetMapping("/usuarioimagen/{id}")
    public ResponseEntity<byte[]> fotoUsuarioComun(@PathVariable String id) {

        try {
            Usuario usuario = usuarioRepositorio.getById(id);

            if (usuario.getImagen() == null) {
                throw new Excepcion("Usuario sin imágen");
            } else {

                byte[] imagen = usuario.getImagen().getContenido();

                HttpHeaders headers = new HttpHeaders();

                headers.setContentType(MediaType.IMAGE_JPEG);

                return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
            }
        } catch (Excepcion e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

}

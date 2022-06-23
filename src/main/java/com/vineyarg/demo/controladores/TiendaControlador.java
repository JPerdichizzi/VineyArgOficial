package com.vineyarg.demo.controladores;

import com.vineyarg.demo.entidades.Compra;
import com.vineyarg.demo.entidades.Producto;
import com.vineyarg.demo.entidades.Productor;
import com.vineyarg.demo.entidades.Usuario;
import com.vineyarg.demo.repositorios.ProductoRepositorio;
import com.vineyarg.demo.repositorios.ProductorRepositorio;
import com.vineyarg.demo.repositorios.UsuarioRepositorio;
import com.vineyarg.demo.servicios.ProductoServicio;
import com.vineyarg.demo.servicios.ProductorServicio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class TiendaControlador {

    @Autowired
    ProductoServicio productoServicio;

    @Autowired
    ProductoRepositorio productoRepositorio;

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Autowired
    ProductorRepositorio productorRepositorio;

    @Autowired
    ProductorServicio productorServicio;

    @GetMapping("/tienda")
    public String tienda(ModelMap modelo) {

        List<Producto> productos = productoRepositorio.findAll();

        modelo.put("productos", productos);

        return "tienda.html";

    }

    @GetMapping("/mostrarProducto")
    public String mostrarproducto(ModelMap modelo, HttpSession session, String id, @RequestParam String idProducto) {

        if (session != null) {
            Usuario login = (Usuario) session.getAttribute("UsuarioSession");
            if (login == null || !login.getId().equalsIgnoreCase(id)) {
                return "redirect:/index.html";
            }

        }

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Usuario usuario = new Usuario();
            usuario = respuesta.get();

            modelo.put("perfil", usuario);
        }

        Producto producto = productoRepositorio.buscarPorId(idProducto);

        modelo.addAttribute("producto", producto);

        List<Producto> productosSimilares = productoRepositorio.buscarTodosPorVarietal(productoRepositorio.findById(idProducto).get().getVarietal());

        modelo.put("productosSimilares", productosSimilares);

        return "producto";
    }

    
    @GetMapping("/tienda")
    public String mostrarPorRegion(ModelMap modelo, String region) {

        //productor es el identificador que va a viajar al html(debe coincidir con el th)/lo segundo es el objeto de java que quiero enviar
       
        List <Producto> productosPorRegion = new ArrayList();
        modelo.addAttribute("productosPorRegion", productorRepositorio.buscarPorRegion(region));

        return "tienda.html";
    }

}

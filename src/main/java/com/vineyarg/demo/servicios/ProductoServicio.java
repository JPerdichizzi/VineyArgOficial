
package com.vineyarg.demo.servicios;


import com.vineyarg.demo.entidades.Producto;
import com.vineyarg.demo.errores.Excepcion;
import com.vineyarg.demo.entidades.Productor;
import com.vineyarg.demo.repositorios.ProductoRepositorio;
import java.util.List;
import java.util.Optional;
//import com.vineyarg.demo.repositorios.ProductorRepositorio;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServicio {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Autowired
    // private ProductorRepositorio productorRepositorio;

    @Transactional
    public void crearProducto(String nombre, Integer cantidad, Double precio, String descripcion,
            String varietal, Productor productor, String SKU, Double valoraciones) throws Excepcion {
        /*Antes de persistir el objeto tenemos que validar que los atributos lleguen*/
        validar(nombre, cantidad, precio, descripcion,
                varietal, productor, SKU, valoraciones);

        //Creamos un nuevo producto y le seteamos los datos
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setCantidad(cantidad);
        producto.setPrecio(precio);
        producto.setDescripcion(descripcion);
        producto.setVarietal(varietal);
        producto.setProductor(productor);
        producto.setSku(SKU);
        producto.setValoraciones(valoraciones);
        producto.setAlta(true);
        productoRepositorio.save(producto);//el repositorio guarda el objeto creado en la base de datos, lo transforma en una tabla

    }
    
    @Transactional
    public void darDeBaja (String Id)throws Excepcion{
        if (Id==null||Id.isEmpty()){
            throw new Excepcion("Debe ingresar el ID del producto");
        }
        Optional <Producto> respuesta= productoRepositorio.findById(Id);
         if(respuesta.isPresent()){
        Producto producto = respuesta.get();
        producto.setAlta(false);
        productoRepositorio.save(producto);
         }
    }
    public void modificar (String Id, String nombre, Integer cantidad, Double precio, String descripcion,
            String varietal, Productor productor, String SKU, Double valoraciones)throws Excepcion {
        
        Optional <Producto> respuesta= productoRepositorio.findById(Id);
         if(respuesta.isPresent()){
        Producto producto = respuesta.get();
        producto.setNombre(nombre);
        producto.setCantidad(cantidad);
        producto.setPrecio(precio);
        producto.setDescripcion(descripcion);
        producto.setVarietal(varietal);
        producto.setProductor(productor);
        producto.setSku(SKU);
        producto.setValoraciones(valoraciones);
        productoRepositorio.save(producto);
        
         }
    }
    public List <Producto> listarProductos (){
        List<Producto> productos=  productoRepositorio.findAll();
        
            return productos;
        }
    public Producto buscarPorId (String Id) throws Excepcion{
       
        if (Id==null){
            throw new Excepcion ("Debe indicar Id");}
         Producto producto = productoRepositorio.buscarPorId(Id);
            return producto;
    }
    public Producto buscarPorNombre (String nombre) throws Excepcion{
       
        if (nombre==null){
            throw new Excepcion ("Debe indicar nombre");}
         Producto producto = productoRepositorio.buscarPorNombre(nombre);
            return producto;
    }
    public Producto buscarPorProductor (Productor productor) throws Excepcion{
       
        if (productor==null){
            throw new Excepcion ("Debe indicar la bodega o productor que desea buscar");}
         Producto producto = productoRepositorio.buscarPorProductor(productor);
            return producto;
    }
    public Producto buscarPorPrecio (Double precio) throws Excepcion{
       
        if (precio < 0){
            throw new Excepcion ("El precio debe ser mayor a 0");}
         Producto producto = productoRepositorio.buscarPorPrecio(precio);
            return producto;
    }
    public Producto buscarPorVarietal (String varietal) throws Excepcion{
       
        if (varietal==null){
            throw new Excepcion ("Debe indicar el varietal que desea");}
         Producto producto = productoRepositorio.buscarPorVarietal(varietal);
            return producto;
    }
    

    public void validar(String nombre, Integer cantidad, Double precio, String descripcion,
            String varietal, Productor productor, String SKU, Double valoraciones) throws Excepcion {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Excepcion("El nombre no puede estar vacío");
        }
        if (cantidad < 0) {
            throw new Excepcion("La cantidad debe ser mayor a 0");
        }
        if (precio < 0) {
            throw new Excepcion("El precio debe ser mayor a 0");
        }
        if (descripcion == null) {
            throw new Excepcion("La descripcion no puede estar vacía");
        }
        if (varietal == null) {
            throw new Excepcion("El varietal no puede estar vacío");
        }
        if (productor == null) {
            throw new Excepcion("Debe ingresar la bodega");
        }
        if (SKU == null) {
            throw new Excepcion("Debe ingresar SKU del producto");
        }
        if (valoraciones == null || valoraciones.isNaN()) {//PREGUNTAR
            throw new Excepcion("Debe valorar el producto");
        }

    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vineyarg.demo.repositorios;

import com.vineyarg.demo.entidades.Imagenes;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author joaqu
 */
public interface ImagenesRepositorio extends JpaRepository<Imagenes, String> {
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vineyarg.demo.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author joaqu
 */
@Controller
@RequestMapping
public class ControladorPrincipal {
    
    @RequestMapping("/index")
    public String page(Model model) {
        model.addAttribute("attribute", "value");
        return "index.html";
    }
    
    @GetMapping("/terminos-condiciones")
    public String terminosCondiciones() {
        return "terminos-condiciones.html";
    }
    
}

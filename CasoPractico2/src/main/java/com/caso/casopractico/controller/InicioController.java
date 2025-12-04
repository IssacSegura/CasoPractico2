/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.caso.casopractico.controller;

/**
 *
 * @author issac
 */


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {
    
    @GetMapping({"/", "/inicio"})
    public String inicio(Authentication authentication, Model model) {
        model.addAttribute("title", "Inicio");
        
        if (authentication != null && authentication.isAuthenticated()) {
            String authority = authentication.getAuthorities().iterator().next().getAuthority();
            
            switch (authority) {
                case "ROLE_ADMIN":
                    model.addAttribute("mensaje", "Bienvenido Administrador");
                    break;
                case "ROLE_PROFESOR":
                    model.addAttribute("mensaje", "Bienvenido Profesor");
                    break;
                case "ROLE_ESTUDIANTE":
                    model.addAttribute("mensaje", "Bienvenido Estudiante");
                    break;
            }
        }
        
        return "inicio";
    }
    
    @GetMapping("/acceso-denegado")
    public String accesoDenegado(Model model) {
        model.addAttribute("title", "Acceso Denegado");
        return "error/403";
    }
}
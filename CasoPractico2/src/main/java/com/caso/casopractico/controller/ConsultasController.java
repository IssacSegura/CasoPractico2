/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.caso.casopractico.controller;


import com.caso.casopractico.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/consultas")
@PreAuthorize("hasAnyRole('ADMIN', 'PROFESOR')")
public class ConsultasController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public String listarConsultas(Model model) {
        model.addAttribute("title", "Consultas Avanzadas");
        
        // Consulta 1: Estadísticas
        model.addAttribute("activos", usuarioService.contarActivos());
        model.addAttribute("inactivos", usuarioService.contarInactivos());
        
        // Consulta 2: Ordenados por fecha
        model.addAttribute("usuariosOrdenados", usuarioService.obtenerOrdenadosPorFecha());
        
        return "consultas/listado";
    }
}
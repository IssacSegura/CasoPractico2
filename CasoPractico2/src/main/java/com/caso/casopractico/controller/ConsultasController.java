/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.caso.casopractico.controller;

import com.caso.casopractico.service.UsuarioService;
import com.caso.casopractico.service.RolService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/consultas")
public class ConsultasController {

    private final UsuarioService usuarioService;
    private final RolService rolService;

    public ConsultasController(UsuarioService usuarioService, RolService rolService) {
        this.usuarioService = usuarioService;
        this.rolService = rolService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var roles = rolService.obtenerTodos();
        model.addAttribute("roles", roles);
        return "consultas/listado";
    }

    @GetMapping("/por-rol")
    public String consultaPorRol(@RequestParam String nombreRol, Model model) {
        var usuarios = usuarioService.buscarPorNombreRol(nombreRol);
        var roles = rolService.obtenerTodos();
        
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("roles", roles);
        model.addAttribute("consulta", "Usuarios con rol: " + nombreRol);
        
        return "consultas/listado";
    }

    @GetMapping("/por-fechas")
    public String consultaPorFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            Model model) {
        
        var usuarios = usuarioService.buscarPorRangoFechas(fechaInicio, fechaFin);
        var roles = rolService.obtenerTodos();
        
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("roles", roles);
        model.addAttribute("consulta", "Usuarios creados entre " + fechaInicio + " y " + fechaFin);
        
        return "consultas/listado";
    }

    @GetMapping("/buscar")
    public String buscarPorCoincidencia(@RequestParam String texto, Model model) {
        var usuarios = usuarioService.buscarPorCoincidencia(texto);
        var roles = rolService.obtenerTodos();
        
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("roles", roles);
        model.addAttribute("consulta", "Búsqueda por: " + texto);
        
        return "consultas/listado";
    }

    @GetMapping("/estadisticas")
    public String estadisticas(Model model) {
        var estadoUsuarios = usuarioService.contarPorEstado();
        var usuariosPorRol = usuarioService.contarPorRol();
        var usuariosOrdenados = usuarioService.obtenerOrdenadosPorFecha();
        
        model.addAttribute("estadoUsuarios", estadoUsuarios);
        model.addAttribute("usuariosPorRol", usuariosPorRol);
        model.addAttribute("usuarios", usuariosOrdenados);
        
        return "consultas/estadisticas";
    }
}
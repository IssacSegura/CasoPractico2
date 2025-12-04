/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.caso.casopractico.controller;

/**
 *
 * @author issac
 */


import com.caso.casopractico.domain.Usuario;
import com.caso.casopractico.service.UsuarioService;
import com.caso.casopractico.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/usuarios")
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private RolService rolService;
    
    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        model.addAttribute("title", "Gestión de Usuarios");
        return "usuarios/listado";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolService.findAll());
        model.addAttribute("title", "Nuevo Usuario");
        return "usuarios/formulario";
    }
    
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario,
                                 @RequestParam Long rolId,
                                 RedirectAttributes redirectAttributes) {
        try {
            // Validar email único
            if (usuario.getId() == null && usuarioService.existeEmail(usuario.getEmail())) {
                redirectAttributes.addFlashAttribute("error", "El email ya está registrado");
                return "redirect:/usuarios/nuevo";
            }
            
            // Asignar rol
            usuario.setRol(rolService.findById(rolId));
            
            // Guardar SIN encriptar contraseña
            usuarioService.save(usuario);
            redirectAttributes.addFlashAttribute("success", "Usuario guardado exitosamente");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar usuario: " + e.getMessage());
        }
        
        return "redirect:/usuarios";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.findById(id);
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.findAll());
        model.addAttribute("title", "Editar Usuario");
        return "usuarios/formulario";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Usuario eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar usuario: " + e.getMessage());
        }
        return "redirect:/usuarios";
    }
    
    @PostMapping("/buscar")
    public String buscarUsuarios(@RequestParam String criterio,
                                 @RequestParam String valor,
                                 Model model) {
        try {
            List<Usuario> resultados = null;
            
            switch (criterio) {
                case "rol":
                    Long rolId = Long.parseLong(valor);
                    resultados = usuarioService.buscarPorRol(rolId);
                    break;
                    
                case "fecha":
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDateTime fecha = LocalDateTime.parse(valor + "T00:00:00");
                    LocalDateTime inicio = fecha;
                    LocalDateTime fin = fecha.plusDays(1);
                    resultados = usuarioService.buscarPorFechaCreacion(inicio, fin);
                    break;
                    
                case "texto":
                    resultados = usuarioService.buscarPorTexto(valor);
                    break;
            }
            
            model.addAttribute("usuarios", resultados);
            model.addAttribute("title", "Resultados de Búsqueda");
            
        } catch (Exception e) {
            model.addAttribute("error", "Error en la búsqueda: " + e.getMessage());
        }
        
        return "usuarios/listado";
    }
}
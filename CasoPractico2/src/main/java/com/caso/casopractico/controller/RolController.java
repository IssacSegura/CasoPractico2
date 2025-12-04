/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.caso.casopractico.controller;

/**
 *
 * @author issac
 */


import com.caso.casopractico.domain.Rol;
import com.caso.casopractico.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/roles")
@PreAuthorize("hasRole('ADMIN')")
public class RolController {
    
    @Autowired
    private RolService rolService;
    
    @GetMapping
    public String listarRoles(Model model) {
        model.addAttribute("roles", rolService.findAll());
        model.addAttribute("title", "Gestión de Roles");
        return "roles/listado";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("rol", new Rol());
        model.addAttribute("title", "Nuevo Rol");
        return "roles/formulario";
    }
    
    @PostMapping("/guardar")
    public String guardarRol(@ModelAttribute Rol rol, RedirectAttributes redirectAttributes) {
        try {
            rolService.save(rol);
            redirectAttributes.addFlashAttribute("success", "Rol guardado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar rol: " + e.getMessage());
        }
        return "redirect:/roles";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Rol rol = rolService.findById(id);
        model.addAttribute("rol", rol);
        model.addAttribute("title", "Editar Rol");
        return "roles/formulario";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarRol(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            rolService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Rol eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar rol: " + e.getMessage());
        }
        return "redirect:/roles";
    }
}
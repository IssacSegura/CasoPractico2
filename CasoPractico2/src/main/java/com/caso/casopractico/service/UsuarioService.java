/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.caso.casopractico.service;

/**
 *
 * @author issac
 */


import com.caso.casopractico.domain.Usuario;
import com.caso.casopractico.domain.Rol;
import com.caso.casopractico.repository.UsuarioRepository;
import com.caso.casopractico.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private RolRepository rolRepository;
    
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
    
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
    
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    public Usuario save(Usuario usuario) {
        // SIN encriptación - guarda contraseña en texto plano
        return usuarioRepository.save(usuario);
    }
    
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }
    
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    
    // CONSULTAS REQUERIDAS
    public List<Usuario> buscarPorRol(Long rolId) {
        Rol rol = rolRepository.findById(rolId).orElse(null);
        return rol != null ? usuarioRepository.findByRol(rol) : new ArrayList<>();
    }
    
    public List<Usuario> buscarPorFechaCreacion(LocalDateTime inicio, LocalDateTime fin) {
        return usuarioRepository.findByFechaCreacionBetween(inicio, fin);
    }
    
    public List<Usuario> buscarPorTexto(String texto) {
        return usuarioRepository.buscarPorTexto(texto);
    }
    
    public Long contarActivos() {
        return usuarioRepository.countByActivoTrue();
    }
    
    public Long contarInactivos() {
        return usuarioRepository.countByActivoFalse();
    }
    
    public List<Usuario> obtenerOrdenadosPorFecha() {
        return usuarioRepository.findAllByOrderByFechaCreacionDesc();
    }
}
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepo, PasswordEncoder passwordEncoder) {
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<Usuario> obtenerTodos() {
        return usuarioRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<Usuario> obtenerActivos() {
        return usuarioRepo.findByActivoTrue();
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepo.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepo.findByEmail(email);
    }

    @Transactional
    public Usuario guardar(Usuario usuario) {
        // Si es un usuario nuevo, encriptar la contraseña
        if (usuario.getId() == null) {
            if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
                throw new IllegalArgumentException("La contraseña es obligatoria");
            }
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        } else {
            // Si es actualización y la contraseña está vacía, mantener la anterior
            if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
                Usuario usuarioExistente = usuarioRepo.findById(usuario.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
                usuario.setPassword(usuarioExistente.getPassword());
            } else {
                // Si hay nueva contraseña, encriptarla
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }
        }
        
        return usuarioRepo.save(usuario);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!usuarioRepo.existsById(id)) {
            throw new IllegalArgumentException("El usuario con ID " + id + " no existe");
        }
        try {
            usuarioRepo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el usuario. Tiene datos asociados", e);
        }
    }

    // Consultas avanzadas
    @Transactional(readOnly = true)
    public List<Usuario> buscarPorRol(Rol rol) {
        return usuarioRepo.findByRol(rol);
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return usuarioRepo.findByFechaCreacionBetween(inicio, fin);
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarPorCoincidencia(String texto) {
        return usuarioRepo.findByNombreContainingIgnoreCaseOrEmailContainingIgnoreCase(texto, texto);
    }

    @Transactional(readOnly = true)
    public List<Object[]> contarPorEstado() {
        return usuarioRepo.contarUsuariosPorEstado();
    }

    @Transactional(readOnly = true)
    public List<Usuario> obtenerOrdenadosPorFecha() {
        return usuarioRepo.findAllOrderByFechaCreacionDesc();
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarPorNombreRol(String nombreRol) {
        return usuarioRepo.buscarPorNombreRol(nombreRol);
    }

    @Transactional(readOnly = true)
    public List<Object[]> contarPorRol() {
        return usuarioRepo.contarUsuariosPorRol();
    }
}
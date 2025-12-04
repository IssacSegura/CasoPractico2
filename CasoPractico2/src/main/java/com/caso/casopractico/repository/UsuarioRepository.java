/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.caso.casopractico.repository;

/**
 *
 * @author issac
 */

import com.caso.casopractico.domain.Usuario;
import com.caso.casopractico.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Consultas derivadas requeridas
    List<Usuario> findByRol(Rol rol);
    List<Usuario> findByFechaCreacionBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Usuario> findByEmailContainingOrNombreContaining(String email, String nombre);
    Long countByActivoTrue();
    Long countByActivoFalse();
    List<Usuario> findAllByOrderByFechaCreacionDesc();
    
    // Consultas adicionales
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    
    // Consulta personalizada 1: Búsqueda por texto
    @Query("SELECT u FROM Usuario u WHERE " +
           "LOWER(u.nombre) LIKE LOWER(CONCAT('%', :texto, '%')) OR " +
           "LOWER(u.apellido) LIKE LOWER(CONCAT('%', :texto, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<Usuario> buscarPorTexto(@Param("texto") String texto);
    
    // Consulta personalizada 2: Conteo por rol
    @Query("SELECT u.rol.nombre, COUNT(u) FROM Usuario u GROUP BY u.rol.nombre")
    List<Object[]> contarUsuariosPorRol();
    
    // Consulta personalizada 3: Usuarios por nombre de rol
    @Query("SELECT u FROM Usuario u WHERE u.rol.nombre = :nombreRol")
    List<Usuario> buscarPorNombreRol(@Param("nombreRol") String nombreRol);
}
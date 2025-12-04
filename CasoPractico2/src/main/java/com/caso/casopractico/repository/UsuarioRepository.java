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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Consulta derivada: Buscar usuario por email y activo
    Optional<Usuario> findByEmailAndActivoTrue(String email);

    // Consulta derivada: Buscar usuarios por rol
    List<Usuario> findByRol(Rol rol);

    // Consulta derivada: Buscar usuarios activos
    List<Usuario> findByActivoTrue();

    // Consulta derivada: Buscar usuarios por rango de fechas de creación
    List<Usuario> findByFechaCreacionBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    // Consulta derivada: Buscar usuarios por coincidencia parcial en nombre o email
    List<Usuario> findByNombreContainingIgnoreCaseOrEmailContainingIgnoreCase(String nombre, String email);

    // Consulta personalizada: Contar usuarios activos vs inactivos
    @Query("SELECT u.activo, COUNT(u) FROM Usuario u GROUP BY u.activo")
    List<Object[]> contarUsuariosPorEstado();

    // Consulta personalizada: Obtener usuarios ordenados por fecha de creación
    @Query("SELECT u FROM Usuario u ORDER BY u.fechaCreacion DESC")
    List<Usuario> findAllOrderByFechaCreacionDesc();

    // Consulta personalizada: Buscar usuarios por rol (nombre del rol)
    @Query("SELECT u FROM Usuario u WHERE u.rol.nombre = :nombreRol")
    List<Usuario> buscarPorNombreRol(@Param("nombreRol") String nombreRol);

    // Consulta personalizada: Contar usuarios por rol
    @Query("SELECT u.rol.nombre, COUNT(u) FROM Usuario u GROUP BY u.rol.nombre")
    List<Object[]> contarUsuariosPorRol();

    // Consulta nativa: Búsqueda avanzada
    @Query(nativeQuery = true, 
           value = "SELECT * FROM usuario WHERE activo = :activo AND rol_id = :rolId")
    List<Usuario> busquedaAvanzada(@Param("activo") Boolean activo, @Param("rolId") Long rolId);

    // Verificar si existe un email
    boolean existsByEmail(String email);

    // Buscar por email
    Optional<Usuario> findByEmail(String email);
}
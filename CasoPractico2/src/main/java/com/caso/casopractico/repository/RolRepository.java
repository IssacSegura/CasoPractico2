/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.caso.casopractico.repository;

/**
 *
 * @author issac
 */
import com.caso.casopractico.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    
    // Buscar rol por nombre
    Optional<Rol> findByNombre(String nombre);
    
    // Verificar si existe un rol por nombre
    boolean existsByNombre(String nombre);
}
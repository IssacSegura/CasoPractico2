/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.caso.casopractico.service;

/**
 *
 * @author issac
 */

import com.caso.casopractico.domain.Rol;
import com.caso.casopractico.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RolService {
    
    @Autowired
    private RolRepository rolRepository;
    
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }
    
    public Rol findById(Long id) {
        return rolRepository.findById(id).orElse(null);
    }
    
    public Optional<Rol> findByNombre(String nombre) {
        return rolRepository.findByNombre(nombre);
    }
    
    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }
    
    public void deleteById(Long id) {
        rolRepository.deleteById(id);
    }
}
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    private final RolRepository rolRepo;

    public RolService(RolRepository rolRepo) {
        this.rolRepo = rolRepo;
    }

    @Transactional(readOnly = true)
    public List<Rol> obtenerTodos() {
        return rolRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Rol> obtenerPorId(Long id) {
        return rolRepo.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Rol> obtenerPorNombre(String nombre) {
        return rolRepo.findByNombre(nombre);
    }

    @Transactional
    public Rol guardar(Rol rol) {
        return rolRepo.save(rol);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!rolRepo.existsById(id)) {
            throw new IllegalArgumentException("El rol con ID " + id + " no existe");
        }
        try {
            rolRepo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el rol. Tiene usuarios asociados", e);
        }
    }
}
package com.api.libreria.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.libreria.exceptions.RecursoNoEncontradoException;
import com.api.libreria.models.PersonaModel;
import com.api.libreria.repositories.IPersonaRepository;

@Service
public class PersonaService {

    @Autowired
    IPersonaRepository personaRepository;

    public ArrayList<PersonaModel> obtenerPersonas() {
        return (ArrayList<PersonaModel>) personaRepository.findAll();
    }

    public PersonaModel guardarPersona(PersonaModel persona) {
        aplicarDatosDeSocio(persona);
        return personaRepository.save(persona);
    }

    public Optional<PersonaModel> obtenerPorId(Long id) {
        return personaRepository.findById(id);
    }

    public PersonaModel actualizarPersona(PersonaModel request, Long id) {
        PersonaModel persona = personaRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("No existe un usuario con ID: " + id));
        persona.setNombre(request.getNombre());
        persona.setApellido(request.getApellido());
        persona.setFechaNacimiento(request.getFechaNacimiento());
        persona.setCorreo(request.getCorreo());
        persona.setTelefono(request.getTelefono());
        persona.setDireccion(request.getDireccion());
        if (request.getContrasena() != null && !request.getContrasena().isBlank()) {
            persona.setContrasena(request.getContrasena());
        }
        persona.setFechaAlta(request.getFechaAlta());
        persona.setEstado(request.getEstado());
        aplicarDatosDeSocio(persona);
        return personaRepository.save(persona);
    }

    public void eliminarPersona(Long id) {
        if (!personaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No existe un usuario con ID: " + id);
        }
        personaRepository.deleteById(id);
    }

    private void aplicarDatosDeSocio(PersonaModel persona) {
        if (persona.getFechaAlta() == null) {
            persona.setFechaAlta(LocalDate.now());
        }
        if (persona.getEstado() == null || persona.getEstado().isBlank()) {
            persona.setEstado("Activo");
        }
    }
}

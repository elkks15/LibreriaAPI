package com.api.libreria.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.libreria.exceptions.RecursoNoEncontradoException;
import com.api.libreria.models.PersonaModel;
import com.api.libreria.services.PersonaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping
    public ArrayList<PersonaModel> obtenerPersonas() {
        return this.personaService.obtenerPersonas();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonaModel guardarPersona(@Valid @RequestBody PersonaModel persona) {
        return this.personaService.guardarPersona(persona);
    }

    @GetMapping(path = "/{id}")
    public PersonaModel obtenerPorId(@PathVariable("id") Long id) {
        return this.personaService.obtenerPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un usuario con ID: " + id));
    }

    @PutMapping(path = "/{id}")
    public PersonaModel actualizarPersona(@Valid @RequestBody PersonaModel request, @PathVariable("id") Long id) {
        return this.personaService.actualizarPersona(request, id);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> eliminarPersona(@PathVariable("id") Long id) {
        this.personaService.eliminarPersona(id);
        return ResponseEntity.noContent().build();
    }
}

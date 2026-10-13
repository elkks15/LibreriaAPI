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
import com.api.libreria.models.PrestamoModel;
import com.api.libreria.services.PrestamoService;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping
    public ArrayList<PrestamoModel> obtenerPrestamos() {
        return this.prestamoService.obtenerPrestamos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PrestamoModel guardarPrestamo(@RequestBody PrestamoModel prestamo) {
        return this.prestamoService.guardarPrestamo(prestamo);
    }

    @GetMapping(path = "/{id}")
    public PrestamoModel obtenerPorId(@PathVariable("id") Long id) {
        return this.prestamoService.obtenerPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un prestamo con ID: " + id));
    }

    @PutMapping(path = "/{id}")
    public PrestamoModel actualizarPrestamo(@RequestBody PrestamoModel request, @PathVariable("id") Long id) {
        return this.prestamoService.actualizarPrestamo(request, id);
    }

    @PutMapping(path = "/{id}/devolver")
    public PrestamoModel devolverPrestamo(@PathVariable("id") Long id) {
        return this.prestamoService.devolverPrestamo(id);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> eliminarPrestamo(@PathVariable("id") Long id) {
        this.prestamoService.eliminarPrestamo(id);
        return ResponseEntity.noContent().build();
    }
}

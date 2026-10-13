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
import com.api.libreria.models.LibroModel;
import com.api.libreria.services.LibroService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    public ArrayList<LibroModel> obtenerLibros() {
        return this.libroService.obtenerLibros();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LibroModel guardarLibro(@Valid @RequestBody LibroModel libro) {
        return this.libroService.guardarLibro(libro);
    }

    @GetMapping(path = "/{isbn}")
    public LibroModel obtenerPorId(@PathVariable("isbn") Long isbn) {
        return this.libroService.obtenerPorId(isbn)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un libro con ISBN: " + isbn));
    }

    @PutMapping(path = "/{isbn}")
    public LibroModel actualizarLibro(@Valid @RequestBody LibroModel request, @PathVariable("isbn") Long isbn) {
        return this.libroService.actualizarLibro(request, isbn);
    }

    @DeleteMapping(path = "/{isbn}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable("isbn") Long isbn) {
        this.libroService.eliminarLibro(isbn);
        return ResponseEntity.noContent().build();
    }
}

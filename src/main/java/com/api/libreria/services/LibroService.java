package com.api.libreria.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.libreria.exceptions.RecursoNoEncontradoException;
import com.api.libreria.models.LibroModel;
import com.api.libreria.repositories.ILibroRepository;

@Service
public class LibroService {

    @Autowired
    ILibroRepository libroRepository;

    public ArrayList<LibroModel> obtenerLibros() {
        return (ArrayList<LibroModel>) libroRepository.findAll();
    }

    public LibroModel guardarLibro(LibroModel libro) {
        return libroRepository.save(libro);
    }

    public Optional<LibroModel> obtenerPorId(Long isbn) {
        return libroRepository.findById(isbn);
    }

    public LibroModel actualizarLibro(LibroModel request, Long isbn) {
        LibroModel libro = libroRepository.findById(isbn)
            .orElseThrow(() -> new RecursoNoEncontradoException("No existe un libro con ISBN: " + isbn));
        libro.setTitulo(request.getTitulo());
        libro.setAutor(request.getAutor());
        libro.setEditorial(request.getEditorial());
        libro.setaPublicacion(request.getaPublicacion());
        if (request.getNoEjemplares() < 0) {
            throw new IllegalArgumentException("El numero de ejemplares no puede ser negativo.");
        }
        libro.setNoEjemplares(request.getNoEjemplares());

        return libroRepository.save(libro);
    }

    public void eliminarLibro(Long isbn) {
        if (!libroRepository.existsById(isbn)) {
            throw new RecursoNoEncontradoException("No existe un libro con ISBN: " + isbn);
        }
        libroRepository.deleteById(isbn);
    }
}

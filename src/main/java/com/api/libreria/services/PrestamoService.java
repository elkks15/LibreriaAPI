package com.api.libreria.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.libreria.exceptions.RecursoNoEncontradoException;
import com.api.libreria.models.LibroModel;
import com.api.libreria.models.PersonaModel;
import com.api.libreria.models.PrestamoModel;
import com.api.libreria.repositories.ILibroRepository;
import com.api.libreria.repositories.IPersonaRepository;
import com.api.libreria.repositories.IPrestamoRepository;

@Service
public class PrestamoService {

    @Autowired
    IPrestamoRepository prestamoRepository;

    @Autowired
    IPersonaRepository personaRepository;

    @Autowired
    ILibroRepository libroRepository;

    public ArrayList<PrestamoModel> obtenerPrestamos() {
        return (ArrayList<PrestamoModel>) prestamoRepository.findAll();
    }

    public Optional<PrestamoModel> obtenerPorId(Long id) {
        return prestamoRepository.findById(id);
    }

    @Transactional
    public PrestamoModel guardarPrestamo(PrestamoModel prestamo) {
        Long personaId = prestamo.getPersona() != null ? prestamo.getPersona().getId() : null;
        Long libroIsbn = prestamo.getLibro() != null ? prestamo.getLibro().getIsbn() : null;
        if (personaId == null) {
            throw new IllegalArgumentException("Selecciona un usuario.");
        }
        if (libroIsbn == null) {
            throw new IllegalArgumentException("Selecciona un libro.");
        }

        PersonaModel persona = personaRepository.findById(personaId)
            .orElseThrow(() -> new RecursoNoEncontradoException("No existe un usuario con ID: " + personaId));
        LibroModel libro = libroRepository.findById(libroIsbn)
            .orElseThrow(() -> new RecursoNoEncontradoException("No existe un libro con ISBN: " + libroIsbn));

        if (!"Activo".equalsIgnoreCase(persona.getEstado())) {
            throw new IllegalArgumentException("El usuario no esta activo y no puede pedir prestamos.");
        }
        if (libro.getNoEjemplares() < 1) {
            throw new IllegalArgumentException("No hay ejemplares disponibles de ese libro.");
        }

        libro.setNoEjemplares(libro.getNoEjemplares() - 1);
        libroRepository.save(libro);

        prestamo.setPersona(persona);
        prestamo.setLibro(libro);
        if (prestamo.getFechaPrestamo() == null) {
            prestamo.setFechaPrestamo(LocalDate.now());
        }
        if (prestamo.getFechaLimite() == null) {
            prestamo.setFechaLimite(prestamo.getFechaPrestamo().plusDays(14));
        }
        prestamo.setFechaDevolucion(null);
        prestamo.setEstado("Prestado");
        return prestamoRepository.save(prestamo);
    }

    public PrestamoModel actualizarPrestamo(PrestamoModel request, Long id) {
        PrestamoModel prestamo = prestamoRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("No existe un prestamo con ID: " + id));
        prestamo.setFechaPrestamo(request.getFechaPrestamo());
        prestamo.setFechaLimite(request.getFechaLimite());
        if (prestamo.getFechaPrestamo() == null) {
            prestamo.setFechaPrestamo(LocalDate.now());
        }
        if (prestamo.getFechaLimite() == null) {
            prestamo.setFechaLimite(prestamo.getFechaPrestamo().plusDays(14));
        }
        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public PrestamoModel devolverPrestamo(Long id) {
        PrestamoModel prestamo = prestamoRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("No existe un prestamo con ID: " + id));
        if ("Devuelto".equalsIgnoreCase(prestamo.getEstado())) {
            throw new IllegalArgumentException("Ese prestamo ya fue devuelto.");
        }

        LibroModel libro = prestamo.getLibro();
        libro.setNoEjemplares(libro.getNoEjemplares() + 1);
        libroRepository.save(libro);

        prestamo.setFechaDevolucion(LocalDate.now());
        prestamo.setEstado("Devuelto");
        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public void eliminarPrestamo(Long id) {
        PrestamoModel prestamo = prestamoRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("No existe un prestamo con ID: " + id));
        if ("Prestado".equalsIgnoreCase(prestamo.getEstado())) {
            devolverPrestamo(id);
        }
        prestamoRepository.deleteById(id);
    }
}

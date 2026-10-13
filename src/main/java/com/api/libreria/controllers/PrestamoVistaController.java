package com.api.libreria.controllers;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.api.libreria.models.LibroModel;
import com.api.libreria.models.PersonaModel;
import com.api.libreria.models.PrestamoModel;
import com.api.libreria.services.LibroService;
import com.api.libreria.services.PersonaService;
import com.api.libreria.services.PrestamoService;

@Controller
public class PrestamoVistaController {

    private final PrestamoService prestamoService;
    private final PersonaService personaService;
    private final LibroService libroService;

    public PrestamoVistaController(PrestamoService prestamoService, PersonaService personaService,
            LibroService libroService) {
        this.prestamoService = prestamoService;
        this.personaService = personaService;
        this.libroService = libroService;
    }

    @GetMapping("/vista/prestamos")
    public String mostrarPrestamos(Model model) {
        model.addAttribute("prestamos", prestamoService.obtenerPrestamos());
        model.addAttribute("personas", personaService.obtenerPersonas());
        model.addAttribute("libros", libroService.obtenerLibros());
        if (!model.containsAttribute("prestamo")) {
            PrestamoModel prestamo = new PrestamoModel();
            prestamo.setPersona(new PersonaModel());
            prestamo.setLibro(new LibroModel());
            prestamo.setFechaPrestamo(LocalDate.now());
            prestamo.setFechaLimite(LocalDate.now().plusDays(14));
            model.addAttribute("prestamo", prestamo);
        }
        return "prestamos";
    }

    @PostMapping("/vista/prestamos/guardar")
    public String guardarPrestamo(@ModelAttribute PrestamoModel prestamo, RedirectAttributes atributos) {
        try {
            prestamoService.guardarPrestamo(prestamo);
            atributos.addFlashAttribute("mensaje", "Prestamo registrado correctamente.");
        } catch (IllegalArgumentException exception) {
            atributos.addFlashAttribute("error", exception.getMessage());
        }
        return "redirect:/vista/prestamos";
    }

    @PostMapping("/vista/prestamos/{id}/actualizar")
    public String actualizarPrestamo(@PathVariable Long id, @ModelAttribute PrestamoModel prestamo,
            RedirectAttributes atributos) {
        try {
            prestamoService.actualizarPrestamo(prestamo, id);
            atributos.addFlashAttribute("mensaje", "Prestamo actualizado correctamente.");
        } catch (IllegalArgumentException exception) {
            atributos.addFlashAttribute("error", exception.getMessage());
        }
        return "redirect:/vista/prestamos";
    }

    @PostMapping("/vista/prestamos/{id}/devolver")
    public String devolverPrestamo(@PathVariable Long id, RedirectAttributes atributos) {
        try {
            prestamoService.devolverPrestamo(id);
            atributos.addFlashAttribute("mensaje", "Libro devuelto correctamente.");
        } catch (IllegalArgumentException exception) {
            atributos.addFlashAttribute("error", exception.getMessage());
        }
        return "redirect:/vista/prestamos";
    }

    @PostMapping("/vista/prestamos/{id}/eliminar")
    public String eliminarPrestamo(@PathVariable Long id, RedirectAttributes atributos) {
        try {
            prestamoService.eliminarPrestamo(id);
            atributos.addFlashAttribute("mensaje", "Prestamo eliminado correctamente.");
        } catch (IllegalArgumentException exception) {
            atributos.addFlashAttribute("error", exception.getMessage());
        }
        return "redirect:/vista/prestamos";
    }
}

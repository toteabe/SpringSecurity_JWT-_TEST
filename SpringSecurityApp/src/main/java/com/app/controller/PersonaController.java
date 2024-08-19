package com.app.controller;

import com.app.config.model.Persona;
import com.app.service.PersonaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping()
    @PreAuthorize("permitAll()")
    public String list(Model model) {
        model.addAttribute("personas", personaService.listAll());
        return "persona-list";
    }

    //    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'DEVELOPER')")
    @PreAuthorize("authenticated")
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("persona", new Persona());
        return "persona-form";
    }

    //    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'DEVELOPER')")
    @PreAuthorize("authenticated")
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Persona persona, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("persona", persona);
            return "persona-form";
        }
        // Verificar si es una actualización y el correo ya existe y pertenece a la misma persona
        if (persona.getId() != null && personaService.existsByEmailExceptId(persona.getEmail(), persona.getId())) {
            model.addAttribute("persona", persona);
            model.addAttribute("errorMessage", "Ya existe una persona con este email ingresado");
            return "persona-form";
        } else if (persona.getId() == null && personaService.ExistsByEmail(persona.getEmail())) {
            model.addAttribute("persona", persona);
            model.addAttribute("errorMessage", "Ya existe una persona con este email ingresado");
            return "persona-form";
        }
        personaService.save(persona);
        return "redirect:/personas";
    }

    //    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'DEVELOPER')")
    @PreAuthorize("authenticated")
    @GetMapping("/actualizar/{id}")
    public String mostrarFormularioActualizar(@PathVariable long id, Model model) {
        Persona persona = personaService.get(id);
        model.addAttribute("persona", persona);
        return "persona-form";
    }

    //    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'DEVELOPER')")
    @PreAuthorize("hasAnyRole('INVITED')")
    @GetMapping("/eliminar/{id}")
    public String eliminarPersona(@PathVariable long id) {
        personaService.delete(id);
        return "redirect:/personas";
    }
}

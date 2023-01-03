package com.aplicacionesjc.agenda_contactos_appv2.controller;

import com.aplicacionesjc.agenda_contactos_appv2.model.Contacto;
import com.aplicacionesjc.agenda_contactos_appv2.repository.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ContactoController {
    @Autowired
    private ContactoRepository contactoRepository;

    @GetMapping({"/", ""})
    public String mostrarPaginaDeInicio(Model model){
        List<Contacto> contactoList = contactoRepository.findAll();
        model.addAttribute("contactoList", contactoList);
        return "index";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeRegistro(Model model){
        model.addAttribute("contacto", new Contacto());
        return "nuevo";
    }

    @PostMapping("/nuevo")
    public String guardarContacto(@Validated Contacto contacto, BindingResult br,RedirectAttributes ra, Model model){
        if(br.hasErrors()){
            model.addAttribute("contacto", contacto);
            return "nuevo";
        }
        contacto.setFechaRegistro(LocalDateTime.now());
        contactoRepository.save(contacto);
        ra.addFlashAttribute("msgSuccess", "El contacto ha sido registrado con éxito.");
        return "redirect:/";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioDeEditarContacto(@PathVariable Integer id, Model model){
        Contacto contacto = contactoRepository.getById(id);
        model.addAttribute("contacto", contacto);
        return "nuevo";
    }

    @PostMapping("/{id}/editar")
    public String actualizarContacto(@PathVariable Integer id, @Validated Contacto contacto, BindingResult br,RedirectAttributes ra, Model model){
        if(br.hasErrors()){
            model.addAttribute("contacto", contacto);
            return "nuevo";
        }
        Contacto contactoAActualizar = contactoRepository.getById(id);
        contactoAActualizar.setNombre(contacto.getNombre());
        contactoAActualizar.setCelular(contacto.getCelular());
        contactoAActualizar.setEmail(contacto.getEmail());
        contactoAActualizar.setFechaNacimiento(contacto.getFechaNacimiento());
        contactoAActualizar.setFechaRegistro(LocalDateTime.now());
        contactoRepository.save(contactoAActualizar);
        ra.addFlashAttribute("msgSuccess", "El contacto ha sido actualizado con éxito.");
        return "redirect:/";
    }
}

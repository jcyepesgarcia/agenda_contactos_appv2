package com.aplicacionesjc.agenda_contactos_appv2.controller;

import com.aplicacionesjc.agenda_contactos_appv2.model.Contacto;
import com.aplicacionesjc.agenda_contactos_appv2.repository.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        model.addAttribute("nuevoContacto", new Contacto());
        return "nuevo";
    }

    @PostMapping("/nuevo")
    public String guardarContacto(Contacto contacto, RedirectAttributes redirect){
        contacto.setFechaRegistro(LocalDateTime.now());
        contactoRepository.save(contacto);
        redirect.addFlashAttribute("msgSuccess", "El contacto ha sido registrado con éxito.");
        return "redirect:/";
    }
}

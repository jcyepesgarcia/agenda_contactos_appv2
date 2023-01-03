package com.aplicacionesjc.agenda_contactos_appv2.controller;

import com.aplicacionesjc.agenda_contactos_appv2.model.Contacto;
import com.aplicacionesjc.agenda_contactos_appv2.repository.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ContactoController {
    @Autowired
    private ContactoRepository contactoRepository;

    @GetMapping({"/", ""})
    public String mostrarPaginaDeInicio(@PageableDefault(size = 5, sort = "fechaRegistro", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(required = false) String nombre, Model model){
        Page<Contacto> contactoPage;
        if(nombre != null && nombre.trim().length() > 0){
            contactoPage = contactoRepository.findByNombreContaining(nombre, pageable);
        } else {
            contactoPage = contactoRepository.findAll(pageable);
        }
        model.addAttribute("contactoPage", contactoPage);
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

    @PostMapping("/{id}/eliminar")
    public String eliminarContacto(@PathVariable Integer id,RedirectAttributes ra){
        Contacto contactoAEliminar = contactoRepository.getById(id);
        contactoRepository.delete(contactoAEliminar);
        ra.addFlashAttribute("msgSuccess", "El contacto ha sido eliminado con éxito.");
        return "redirect:/";
    }
}

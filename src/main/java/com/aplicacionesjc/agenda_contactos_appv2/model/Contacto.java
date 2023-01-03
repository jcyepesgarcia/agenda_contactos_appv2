package com.aplicacionesjc.agenda_contactos_appv2.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Contacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_contacto")
    private Integer id;

    @NotBlank(message = "Ingrese el nombre del contacto")
    private String nombre;

    @NotBlank(message = "Ingrese el número del contacto")
    @Size(max = 13)
    private String celular;

    @Email(message = "Debe ingresar una dirección de correo electrónico con formato correcto")
    private String email;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "Debe ingresar una fecha pasada")
    @Column(name="fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name="fecha_registro")
    private LocalDateTime fechaRegistro;

}

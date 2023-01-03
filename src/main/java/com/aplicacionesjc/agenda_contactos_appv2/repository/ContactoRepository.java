package com.aplicacionesjc.agenda_contactos_appv2.repository;

import com.aplicacionesjc.agenda_contactos_appv2.model.Contacto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Integer> {
    Page<Contacto> findByNombreContaining(String nombre, Pageable pageable);
}

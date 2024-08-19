package com.app.persistence.repository;



import com.app.config.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot (String email, Long id);
}

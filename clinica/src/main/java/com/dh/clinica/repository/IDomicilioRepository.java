package com.dh.clinica.repository;

import com.dh.clinica.entity.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Notemos que a Jpa repository le pasamos el tipo de dato y el tipo de dato de la clave principal
@Repository
public interface IDomicilioRepository extends JpaRepository<Domicilio,Integer> {
}

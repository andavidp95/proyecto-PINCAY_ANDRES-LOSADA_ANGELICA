package com.dh.clinica.repository;

import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo,Integer> {
    //// Metodos HQL agregados
    List<Odontologo> findByApellidoAndNombre(String apellido, String nombre);

    @Query("Select o from Odontologo o where o.nombre LIKE %:parteNombre% ")
    List<Odontologo> findByNombreLike(String parteNombre);
}

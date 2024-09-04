package com.dh.clinica.repository;

import com.dh.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// JpaRepository ya tiene los metodos que vamos a usar en la interfaz :o!!!!!!!
@Repository
public interface IPacienteRepository extends JpaRepository<Paciente,Integer> {

    //// Metodos HQL agregados
    List<Paciente> findByApellidoAndNombre(String apellido, String nombre);

    @Query("Select p from Paciente p where p.nombre LIKE %:parteNombre% ")
    List<Paciente> findByNombreLike(String parteNombre);


}

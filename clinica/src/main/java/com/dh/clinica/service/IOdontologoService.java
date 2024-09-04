package com.dh.clinica.service;

import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IOdontologoService {
    Odontologo guardarOdontologo(Odontologo odontologo) ;

    Optional<Odontologo> buscarPorId(Integer id) ;

    List<Odontologo> buscarTodos();

    void modificarOdontologo(Odontologo odontologo);

    void eliminarOdontologo(Integer id);

    ////// HQL ////////
    List<Odontologo> buscarPorApellidoyNombre(String apellido, String nombre);
    @Query("Select o from Odontologo o where o.nombre LIKE %:nombre%")
    List<Odontologo> buscarLikeNombre(String nombre);

}

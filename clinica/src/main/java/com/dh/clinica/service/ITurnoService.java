package com.dh.clinica.service;

import com.dh.clinica.dto.request.TurnoModificarDto;
import com.dh.clinica.dto.request.TurnoRequestDto;
import com.dh.clinica.dto.response.TurnoResponseDto;
import com.dh.clinica.entity.Turno;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

//////////// CON EL DTO /////////////////////////
public interface ITurnoService {
    TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto);
    Optional<TurnoResponseDto> buscarPorId(Integer id);
    List<TurnoResponseDto> buscarTodos();
    void modificarTurno(TurnoModificarDto turnoModificarDto); // ver la clase TurnoMoficiarDto
    void eliminarTurno(Integer id);

    ////HQL/////
      List<Turno> buscarTurnoPaciente(String apellidoPaciente);
}


///////////// SIN DTO ///////////////////////////
//public interface ITurnoService {
//    Turno guardarTurno(Turno turno) ;
//
//    Optional<Turno> buscarPorId(Integer id) ;
//
//    List<Turno> buscarTodos() ;
//
//    void modificarTurno(Turno turno) ;
//
//    void eliminarTurno(Integer id) ;
//}

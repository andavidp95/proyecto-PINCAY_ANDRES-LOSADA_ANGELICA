package com.dh.clinica.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


////hacemos esta clase ya que para modificar turno requerimos un id, en el DTO en request
/// no tenemos id de turno, entonces aqui lo creamos
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TurnoModificarDto {
    private Integer id;
    private Integer paciente_id;
    private Integer odontologo_id;
    private String fecha;
}
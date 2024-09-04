package com.dh.clinica.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor // para el jackson
@Entity
@Table(name="turnos")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id incremental, aunque con @id viene por defecto
    private Integer id;
    @ManyToOne
    @JsonBackReference(value = "paciente-turno") // Si queriamos ver desde el lado de paciente
    private Paciente paciente;
    @ManyToOne
    @JsonBackReference(value = "odontologo-turno") // Si queremos ver desde el lado de odonto
    private Odontologo odontologo;
    private LocalDate fecha;

    @Override
    public String toString() {
        return "Turno{" +
                "id=" + id +
                ", paciente=" + paciente +
                ", odontologo=" + odontologo +
                ", fecha=" + fecha +
                '}';
    }
}

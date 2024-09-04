package com.dh.clinica.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor // para el jackson
@Entity
@Table(name="odontologos")
public class Odontologo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String apellido;
    private String nombre;
    private String matricula;

    @OneToMany(mappedBy = "odontologo") // SEGUN la logica de la empresa PODRIAMOS poner un casacde ...
    // .. que cuando elimine un odontologo elimine el turno..
    // @JsonIgnore // esto es para ver desde paciente y odonto en caso que queramos. VER otras entidades
    // si queremos lo anterior debemos descomentar el json back reference en turno
    @JsonManagedReference(value = "odontologo-turno") // Si queremos ver desde turno, va matcheado con lo que se ponga en turno
    private Set<Turno> turnoSet; // Usamos set ya que un set tiene elementos unicos y maneja bien ids

    @Override
    public String toString() {
        return "Odontologo{" +
                "id=" + id +
                ", apellido='" + apellido + '\'' +
                ", nombre='" + nombre + '\'' +
                ", matricula='" + matricula + '\'' +
                '}';
    }
}

package com.dh.clinica.controller;

import com.dh.clinica.dto.request.TurnoModificarDto;
import com.dh.clinica.dto.request.TurnoRequestDto;
import com.dh.clinica.dto.response.TurnoResponseDto;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.entity.Turno;
import com.dh.clinica.service.impl.TurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/////////////////////////////// CON DTO //////////////////////////////
@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarTurno(@RequestBody TurnoRequestDto turnoRequestDto) {
        TurnoResponseDto turnoAGuardar = turnoService.guardarTurno(turnoRequestDto);
        if (turnoAGuardar != null) {
            return ResponseEntity.ok(turnoAGuardar);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El paciente o el odontologo no fueron encontrados");
        }
    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<TurnoResponseDto>> buscarTodos() {
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @PutMapping("/modificar")
    public ResponseEntity<?> modificarTurno(@RequestBody TurnoModificarDto turnoModificarDto){
        turnoService.modificarTurno(turnoModificarDto);
        return ResponseEntity.ok("{\"mensaje\": \"El turno fue modificado\"}");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Integer id){
        Optional<TurnoResponseDto> turnoResponseDto = turnoService.buscarPorId(id);
        if(turnoResponseDto.isPresent()){
            turnoService.eliminarTurno(id);
            String jsonResponse = "{\"mensaje\": \"El turno fue eliminado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //////////////////////////// HQL /////////////////////////////////
    @GetMapping("/buscartodos/{apellido}")
    public ResponseEntity<List<Turno>> buscarTurnoApellidoPaciente(@PathVariable String apellido){
        return ResponseEntity.ok(turnoService.buscarTurnoPaciente(apellido));
    }

}


////////////////// SIN DTO - EN CASO QUE DESEEMOS. TOCARIA AJUSTAR EL FRONT /////////////
//@RestController
//@RequestMapping("/turnos")
//public class TurnoController {
//    private TurnoService turnoService;
//
//    public TurnoController(TurnoService turnoService) {
//        this.turnoService = turnoService;
//    }
//
//    @PostMapping("/guardar")
//    public ResponseEntity<?> guardarTurno(@RequestBody Turno turno){
//        Turno turnoAGuardar = turnoService.guardarTurno(turno);
//        if(turnoAGuardar != null){
//            return ResponseEntity.ok(turnoAGuardar);
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El paciente o el odontologo no fueron encontrados");
//        }
//    }
//
//    @GetMapping("/buscar/{id}")
//    public ResponseEntity<?> buscarPorId(@PathVariable Integer id){
//        Optional<Turno> turno = turnoService.buscarPorId(id);
//        if(turno.isPresent()){
//            return ResponseEntity.ok(turno.get());
//        } else {
//            // ResponseEntity.status(HttpStatus.NOT_FOUND).body("paciente no encontrado");
//            //ResponseEntity.notFound().build();
//            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
//        }
//    }
//
//    @GetMapping("/buscartodos")
//    public ResponseEntity<List<Turno>> buscarTodos(){
//        return ResponseEntity.ok(turnoService.buscarTodos());
//    }
//
//    @PutMapping("/modificar")
//    public ResponseEntity<?> modificarTurno(@RequestBody Turno turno){
//        Optional<Turno> turnoEncontrado = turnoService.buscarPorId(turno.getId());
//        if(turnoEncontrado.isPresent()){
//            turnoService.modificarTurno(turnoEncontrado.get());
//            String jsonResponse = "{\"mensaje\": \"El turno fue modificado\"}";
//            return ResponseEntity.ok(jsonResponse);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/eliminar/{id}")
//    public ResponseEntity<?> eliminarTurno(@PathVariable Integer id){
//        Optional<Turno> turnoEncontrado = turnoService.buscarPorId(id);
//        if(turnoEncontrado.isPresent()){
//            turnoService.eliminarTurno(id);
//            String jsonResponse = "{\"mensaje\": \"El turno fue eliminado\"}";
//            return ResponseEntity.ok(jsonResponse);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//}

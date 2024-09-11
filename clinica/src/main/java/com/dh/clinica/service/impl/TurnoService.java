package com.dh.clinica.service.impl;

import com.dh.clinica.dto.request.TurnoModificarDto;
import com.dh.clinica.dto.request.TurnoRequestDto;
import com.dh.clinica.dto.response.OdontologoResponseDto;
import com.dh.clinica.dto.response.PacienteResponseDto;
import com.dh.clinica.dto.response.TurnoResponseDto;
import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.entity.Turno;
import com.dh.clinica.exception.BadRequestException;
import com.dh.clinica.exception.NotFoundException;
import com.dh.clinica.repository.ITurnoRepository;
import com.dh.clinica.service.ITurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

///// CON DTO ////////////////
@Service
public class TurnoService implements ITurnoService {
    private ITurnoRepository turnoRepository;
    private PacienteService pacienteService;
    private OdontologoService odontologService;

    // En caso de usar Mapper
    // @Autowired
    // private ModelMapper modelMapper;

    public TurnoService(ITurnoRepository turnoRepository, PacienteService pacienteService, OdontologoService odontologService) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologService = odontologService;
    }

    @Override
    public TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologService.buscarPorId(turnoRequestDto.getOdontologo_id());
        if (!paciente.isPresent() || !odontologo.isPresent()) {
            throw new BadRequestException("El paciente o el odontólogo no existen");
        }
        Turno turno = new Turno(); // este es el que va sin id
        Turno turnoDesdeDb = null; // este es el que viene con id
        TurnoResponseDto turnoARetornar = null;
        if (paciente.isPresent() && odontologo.isPresent()) {
            // mapear el turnoRequestDto a turno
            turno.setPaciente(paciente.get());
            turno.setOdontologo(odontologo.get());
            turno.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
            // voy a persistir el turno
            turnoDesdeDb = turnoRepository.save(turno);

            // mapear el turno a turnoResponseDto
            turnoARetornar = convertirTurnoAResponse(turnoDesdeDb);

        }
        return turnoARetornar;
    }

    @Override
    public Optional<TurnoResponseDto> buscarPorId(Integer id) {
        Optional<Turno> turnoDesdeDb = turnoRepository.findById(id);
        TurnoResponseDto turnoResponseDto = null;
        if(turnoDesdeDb.isPresent()){
            turnoResponseDto = convertirTurnoAResponse(turnoDesdeDb.get());
        }
        return Optional.ofNullable(turnoResponseDto); // para no cambiar la firma
    }

    @Override
    public List<TurnoResponseDto> buscarTodos() {
        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoResponseDto> turnosRespuesta = new ArrayList<>();
        for (Turno t: turnos){
            TurnoResponseDto turnoAuxiliar = convertirTurnoAResponse(t);
            turnosRespuesta.add(turnoAuxiliar);
        }
        return turnosRespuesta;
    }

    @Override
    public void modificarTurno(TurnoModificarDto turnoModificarDto) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(turnoModificarDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologService.buscarPorId(turnoModificarDto.getOdontologo_id());
        Turno turno = null;
        if (paciente.isPresent() && odontologo.isPresent()) {
            turno = new Turno(turnoModificarDto.getId(), paciente.get(), odontologo.get(),
                    LocalDate.parse(turnoModificarDto.getFecha()) );
            // voy a persistir el turno
            turnoRepository.save(turno);
        }
    }

    private TurnoResponseDto convertirTurnoAResponse(Turno turnoDesdeDb){
        OdontologoResponseDto odontologoResponseDto = new OdontologoResponseDto(
                turnoDesdeDb.getOdontologo().getId(), turnoDesdeDb.getOdontologo().getMatricula(),
                turnoDesdeDb.getOdontologo().getNombre(), turnoDesdeDb.getOdontologo().getApellido()
        );
        PacienteResponseDto pacienteResponseDto = new PacienteResponseDto(
                turnoDesdeDb.getPaciente().getId(), turnoDesdeDb.getPaciente().getNombre(),
                turnoDesdeDb.getPaciente().getApellido(), turnoDesdeDb.getPaciente().getDni()
        );
        TurnoResponseDto turnoARetornar = new TurnoResponseDto(
                turnoDesdeDb.getId(), pacienteResponseDto, odontologoResponseDto,
                turnoDesdeDb.getFecha().toString()
        );
        return turnoARetornar;
    }


    // Abajo, alternativa usando MAPPER y hacemos  turnoARetornar = mapearATurnoResponse(turnoDesdeDb);
    // en vez deturnoARetornar = convertirTurnoAResponse(turnoDesdeDb); en guardarTurno
    // y ponemos  turnoResponseDto = mapearATurnoResponse(turnoDesdeDb.get()); en buscar porId en vez de..
    // ...  turnoResponseDto = convertirTurnoAResponse(turnoDesdeDb.get());
    // analogamente en buscarTodos pondriamos             TurnoResponseDto turnoAuxiliar = mapearATurnoResponse(t);

    //    private TurnoResponseDto mapearATurnoResponse(Turno turno){
//        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
//        turnoResponseDto.setOdontologoResponseDto(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
//        turnoResponseDto.setPacienteResponseDto(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
//        return turnoResponseDto;
//    }

    @Override
    public void eliminarTurno(Integer id) {
        Optional<Turno> turno = turnoRepository.findById(id);
        if (!turno.isPresent()) {
            throw new NotFoundException("Turno con ID " + id + " no encontrado");
        }
        turnoRepository.deleteById(id);
    }


    /////// HQL ///////
    @Override
    public List<Turno> buscarTurnoPaciente(String apellidoPaciente){
        return turnoRepository.buscarTurnoPorApellidoPaciente(apellidoPaciente);
    }

}




///// SIN DTO ////////////////
//@Service
//public class TurnoService implements ITurnoService {
//    private ITurnoRepository turnoRepository;
//    private PacienteService pacienteService; // mejor mantenerlo asi desacoplado en vez que llamar a los Dao
//    private OdontologoService odontologoService;
//
//    public TurnoService(ITurnoRepository turnoRepository, PacienteService pacienteService, OdontologoService odontologoService) {
//        this.turnoRepository = turnoRepository;
//        this.pacienteService = pacienteService;
//        this.odontologoService = odontologoService;
//    }
//
//    @Override
//    public Turno guardarTurno(Turno turno){
//        Optional <Paciente> paciente = pacienteService.buscarPorId(turno.getPaciente().getId());
//        Optional <Odontologo> odontologo = odontologoService.buscarPorId(turno.getOdontologo().getId());
//        Turno turnoARetornar = null;
//        // isPresent es un metodo que viene en Optional
//        if (paciente.isPresent() && odontologo.isPresent()) {
//            // el .get() es para en vez de obtener el Optional, obtener el Paciente y el Odonto, respectivamente
//            turno.setPaciente(paciente.get());
//            turno.setOdontologo(odontologo.get());
//            // aqui persistimos el turno
//            // aqui de manera automatica ya vendra con el id
//            turnoARetornar = turnoRepository.save(turno);
//        }
//        return turnoARetornar;
//    }
//
//    @Override
//    public Optional<Turno> buscarPorId(Integer id) {
//        return turnoRepository.findById(id);
//    }
//
//    @Override
//    public List<Turno> buscarTodos() {
//        return turnoRepository.findAll();
//    }
//
//    @Override
//    public void modificarTurno(Turno turno) {
//        Optional<Paciente> paciente = pacienteService.buscarPorId(turno.getPaciente().getId());
//        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turno.getOdontologo().getId());
//        if (paciente.isPresent() && odontologo.isPresent()) {
//            turno.setPaciente(paciente.get());
//            turno.setOdontologo(odontologo.get());
//            // voy a persistir el turno
//            turnoRepository.save(turno);
//        }
//
//    }
//
//    @Override
//    public void eliminarTurno(Integer id) {
//        turnoRepository.deleteById(id);
//    }
//
//}

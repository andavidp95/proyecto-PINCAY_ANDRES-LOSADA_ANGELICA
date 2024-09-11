package com.dh.clinica.service.impl;

import com.dh.clinica.entity.Paciente;
import com.dh.clinica.exception.BadRequestException;
import com.dh.clinica.exception.NotFoundException;
import com.dh.clinica.repository.IPacienteRepository;
import com.dh.clinica.service.IPacienteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {
    private IPacienteRepository pacienteRepository;

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente guardarPaciente(Paciente paciente) {
        if (paciente == null || paciente.getNombre() == null || paciente.getDni() == null) {
            throw new BadRequestException("Los campos obligatorios no están completos");
        }
        return pacienteRepository.save(paciente);
    }

    //El optonal te devuelve un paciente o un nulo, es el tipo de dato que bota findById
    @Override
    public Optional<Paciente> buscarPorId(Integer id) {
        return pacienteRepository.findById(id);
    }

    @Override
    public List<Paciente> buscarTodos() {
        return pacienteRepository.findAll();
    }

    //Aqui el save lo que hace es pisar al objeto paciente en la base de datos
    @Override
    public void modificarPaciente(Paciente paciente) {
        pacienteRepository.save(paciente);
    }

    @Override
    public void eliminarPaciente(Integer id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        if (!paciente.isPresent()) {
            throw new NotFoundException("Paciente con ID " + id + " no encontrado");
        }
        pacienteRepository.deleteById(id);
    }

    ///////////////// HQL //////////////////////
    @Override
    public List<Paciente> buscarPorApellidoyNombre(String apellido, String nombre) {
        return pacienteRepository.findByApellidoAndNombre(apellido, nombre);
    }

    @Override
    public List<Paciente> buscarLikeNombre(String nombre) {
        return pacienteRepository.findByNombreLike(nombre);
    }

}
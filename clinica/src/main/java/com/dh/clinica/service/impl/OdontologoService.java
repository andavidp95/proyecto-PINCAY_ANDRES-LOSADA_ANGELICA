package com.dh.clinica.service.impl;

import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.repository.IOdontologoRepository;
import com.dh.clinica.service.IOdontologoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {
    public static final Logger logger = LoggerFactory.getLogger(OdontologoService.class);
    private IOdontologoRepository odontologoRepository;

    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    @Override
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        logger.info("odontologo persistido "+ odontologoRepository.save(odontologo) );
        return odontologoRepository.save(odontologo);
    }

    @Override
    public Optional<Odontologo> buscarPorId(Integer id) {
        logger.info("odontologo encontrado "+ odontologoRepository.findById(id));
        return odontologoRepository.findById(id);
    }

    @Override
    public List<Odontologo> buscarTodos() {
        logger.info("odontologo(s) encontrado(s) "+ odontologoRepository.findAll());
        return odontologoRepository.findAll();
    }

    @Override
    public void modificarOdontologo(Odontologo odontologo) {
        logger.info("odontologo modificado "+  odontologo);
        odontologoRepository.save(odontologo);
    }

    @Override
    public void eliminarOdontologo(Integer id) {
        logger.info("odontologo eliminado "+ odontologoRepository.findById(id));
        odontologoRepository.deleteById(id);
    }

    ///////////////// HQL //////////////////////
    @Override
    public List<Odontologo> buscarPorApellidoyNombre(String apellido, String nombre) {
        return odontologoRepository.findByApellidoAndNombre(apellido, nombre);
    }

    @Override
    public List<Odontologo> buscarLikeNombre(String nombre) {
        return odontologoRepository.findByNombreLike(nombre);
    }

}
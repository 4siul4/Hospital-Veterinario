package com.Hospital.service;

import com.Hospital.dto.IngresoDTO;
import com.Hospital.dto.MascotaDTO;
import com.Hospital.exception.MascotaExistenteException;
import com.Hospital.exception.ResourceNotFoundException;
import com.Hospital.model.Mascota;
import com.Hospital.repository.IngresoRepository;
import com.Hospital.repository.MascotaRepository;
import com.Hospital.util.IngresoMapper;
import com.Hospital.util.MascotaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private IngresoRepository ingresoRepository;

    @Autowired
    private MascotaMapper mascotaMapper;

    @Autowired
    private IngresoMapper ingresoMapper;

    //Devuelve la mascota con el id aportado
    public MascotaDTO getMascotaById(Long id) {
        //Busca la mascota en la base de datos y si no la encuentra devuelve un error
        Mascota mascota = mascotaRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada"));
        return mascotaMapper.toDTO(mascota);
    }

    //Busca todos los ingresos de una mascota con el id aportado
    public List<IngresoDTO> getIngresosByMascotaId(Long id) {
        //Busca la mascota en la base de datos y si no la encuentra devuelve un error
        Mascota mascota = mascotaRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada"));
        //Transforma la lista de entidades ingreso a una lista de DTOs usando la clase mapper
        return mascota.getIngresos().stream().map(ingresoMapper::toDTO).collect(Collectors.toList());
    }

    //Inserta en la base de datos la mascota correspondiente
    public MascotaDTO postMascota(MascotaDTO mascotaDTO) {
        //Comprueba que no haya ya una mascota con ese código de identificación
        if(mascotaRepository.existsByCodigoIdentificacion(mascotaDTO.getCodigoIdentificacion())){
            throw new MascotaExistenteException("Ya existe una mascota con ese código de identificación");
        }
        //Transforma el DTO en entidad y la guarda en la base de datos
        Mascota mascota = mascotaMapper.toEntity(mascotaDTO);
        mascota.setDeleted(Boolean.FALSE);
        mascota.setId(null);
        Mascota savedMascota = mascotaRepository.save(mascota);
        return mascotaMapper.toDTO(savedMascota);
    }

    //Marca el campo deleted de la mascota correspondiente como falso
    public void deleteMascota(Long id) {
        //Busca la mascota y si no la encuentra devuelve un error
        Mascota mascota = mascotaRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada"));
        //Se marca como desactivada en la base de datos sin eliminarla
        mascota.setDeleted(Boolean.TRUE);
        mascotaRepository.save(mascota);
    }
}
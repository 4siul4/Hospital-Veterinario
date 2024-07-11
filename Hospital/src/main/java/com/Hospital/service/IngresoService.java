package com.Hospital.service;

import com.Hospital.dto.IngresoDTO;
import com.Hospital.exception.DniNoCoincideException;
import com.Hospital.exception.FinalizadoSinFechaFinException;
import com.Hospital.exception.IncorrectEstadoException;
import com.Hospital.exception.ResourceNotFoundException;
import com.Hospital.model.Ingreso;
import com.Hospital.model.Mascota;
import com.Hospital.repository.IngresoRepository;
import com.Hospital.repository.MascotaRepository;
import com.Hospital.util.IngresoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngresoService {

    @Autowired
    private IngresoRepository ingresoRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private IngresoMapper ingresoMapper;

    //Obtiene de la base de datos todos los ingresos
    public List<IngresoDTO> getIngresos() {
        List<Ingreso> ingresos = ingresoRepository.findAll();
        //Se convierte la lista de entidades Ingreso a una lista de DTOs usando la clase ingresoMapper
        return ingresos.stream().map(ingresoMapper::toDTO).collect(Collectors.toList());
    }

    //Inserta en la base de datos un nuevo ingreso
    public IngresoDTO postIngreso(IngresoDTO ingresoDTO) {
        //Busca la mascota correspondiente, devolviendo un error si no se encuentra
        Mascota mascota = mascotaRepository.findByIdAndDeletedIsFalse(ingresoDTO.getMascotaId())
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada"));
        //Comprueba que el dni de la persona que registra el ingreso es la misma que el dni del responsable de la mascota
        if(!mascota.getDniResponsable().equals(ingresoDTO.getDniPersonaRegistra())){
            throw new DniNoCoincideException("No puedes registrar el ingreso de una mascota que no sea tuya");
        }
        //Convierte el DTO a entidad para guardar la información
        Ingreso ingreso = ingresoMapper.toEntity(ingresoDTO);
        ingreso.setMascota(mascota);
        ingreso.setEstado("ALTA");
        //Se evita que se reemplace un ingreso ya existente si se envía el ingreso con un id
        ingreso.setId(null);
        Ingreso savedIngreso = ingresoRepository.save(ingreso);
        return ingresoMapper.toDTO(savedIngreso);
    }

    //Actualiza el ingreso correspondiente en la base de datos
    public IngresoDTO putIngreso(Long id, IngresoDTO ingresoDTO) {
        //Comprueba que el estado sea uno de los 4 válidos
        if(!ingresoDTO.getEstado().isEmpty() && !ingresoDTO.getEstado().equals("ALTA")  && !ingresoDTO.getEstado().equals("ANULADO") && !ingresoDTO.getEstado().equals("HOSPITALIZACION")  && !ingresoDTO.getEstado().equals("FINALIZADO")){
            throw new IncorrectEstadoException("El estado no es correcto, solo puede ser uno de los siguientes valores: ALTA, ANULADO, HOSPITALIZACION o FINALIZADO");
        }
        //Comprueba que la fecha de fin no esté vacía si el estado está establecido como FINALIZADO
        if(ingresoDTO.getFechaFin() == null && ingresoDTO.getEstado().equals("FINALIZADO")){
            throw new FinalizadoSinFechaFinException("El ingreso no puede estar en estado finalizado sin una fecha de fin");
        }
        //Busca el ingreso con la id relacionada y si no lo encuentra devuelve un mensaje de error
        Ingreso ingreso = ingresoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingreso no encontrado"));
        //Hace los cambios a la fecha de fin y al estado correspondientes
        ingreso.setEstado(ingresoDTO.getEstado());
        ingreso.setFechaFin(ingresoDTO.getFechaFin());
        Ingreso updatedIngreso = ingresoRepository.save(ingreso);
        return ingresoMapper.toDTO(updatedIngreso);
    }

    //Marca un ingreso como anulado
    public void deleteIngreso(Long id) {
        //Busca el ingreso con la id relacionada y si no lo encuentra devuelve un mensaje de error
        Ingreso ingreso = ingresoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingreso no encontrado"));
        //Establece el estado a ANULADO y guarda el cambio
        ingreso.setEstado("ANULADO");
        ingresoRepository.save(ingreso);
    }
}
package com.Hospital.util;

import com.Hospital.dto.IngresoDTO;
import com.Hospital.model.Ingreso;
import org.springframework.stereotype.Component;

//Clase utilizada para convertir entre entidad y DTO
@Component
public class IngresoMapper {

    public IngresoDTO toDTO(Ingreso ingreso) {
        IngresoDTO ingresoDTO = new IngresoDTO();
        ingresoDTO.setId(ingreso.getId());
        ingresoDTO.setFechaAlta(ingreso.getFechaAlta());
        ingresoDTO.setFechaFin(ingreso.getFechaFin());
        ingresoDTO.setEstado(ingreso.getEstado());
        ingresoDTO.setMascotaId(ingreso.getMascota().getId());
        ingresoDTO.setDniPersonaRegistra(ingreso.getDniPersonaRegistra());
        return ingresoDTO;
    }

    public Ingreso toEntity(IngresoDTO ingresoDTO) {
        Ingreso ingreso = new Ingreso();
        ingreso.setId(ingresoDTO.getId());
        ingreso.setFechaAlta(ingresoDTO.getFechaAlta());
        ingreso.setFechaFin(ingresoDTO.getFechaFin());
        ingreso.setEstado(ingresoDTO.getEstado());
        ingreso.setDniPersonaRegistra(ingresoDTO.getDniPersonaRegistra());
        return ingreso;
    }
}
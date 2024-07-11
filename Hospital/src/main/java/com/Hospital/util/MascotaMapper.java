package com.Hospital.util;

import com.Hospital.dto.MascotaDTO;
import com.Hospital.model.Mascota;
import org.springframework.stereotype.Component;

//Clase utilizada para convertir entre entidad y DTO
@Component
public class MascotaMapper {

    public MascotaDTO toDTO(Mascota mascota) {
        MascotaDTO mascotaDTO = new MascotaDTO();
        mascotaDTO.setId(mascota.getId());
        mascotaDTO.setEspecie(mascota.getEspecie());
        mascotaDTO.setRaza(mascota.getRaza());
        mascotaDTO.setEdad(mascota.getEdad());
        mascotaDTO.setCodigoIdentificacion(mascota.getCodigoIdentificacion());
        mascotaDTO.setDniResponsable(mascota.getDniResponsable());
        return mascotaDTO;
    }

    public Mascota toEntity(MascotaDTO mascotaDTO) {
        Mascota mascota = new Mascota();
        mascota.setId(mascotaDTO.getId());
        mascota.setEspecie(mascotaDTO.getEspecie());
        mascota.setRaza(mascotaDTO.getRaza());
        mascota.setEdad(mascotaDTO.getEdad());
        mascota.setCodigoIdentificacion(mascotaDTO.getCodigoIdentificacion());
        mascota.setDniResponsable(mascotaDTO.getDniResponsable());
        return mascota;
    }
}
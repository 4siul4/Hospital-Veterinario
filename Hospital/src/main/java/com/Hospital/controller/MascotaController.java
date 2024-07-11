package com.Hospital.controller;

import com.Hospital.dto.IngresoDTO;
import com.Hospital.dto.MascotaDTO;
import com.Hospital.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospitalclinicovet-api/mascota")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    //Se recibe la llamada para devolver la mascota con el id aportado
    @GetMapping("/{idMascota}")
    public ResponseEntity<MascotaDTO> getMascotaById(@PathVariable Long idMascota) {
        MascotaDTO mascota = mascotaService.getMascotaById(idMascota);
        return ResponseEntity.ok(mascota);
    }

    //Se recibe la llamada para devolver todos los ingresos de la mascota relacionada
    @GetMapping("/{idMascota}/ingreso")
    public ResponseEntity<List<IngresoDTO>> getIngresosByMascotaId(@PathVariable Long idMascota) {
        List<IngresoDTO> ingresos = mascotaService.getIngresosByMascotaId(idMascota);
        return ResponseEntity.ok(ingresos);
    }

    //Se recibe la llamada para insertar una mascota en la base de datos
    @PostMapping
    public ResponseEntity<MascotaDTO> postMascota(@RequestBody MascotaDTO mascotaDTO) {
        MascotaDTO createdMascota = mascotaService.postMascota(mascotaDTO);
        return ResponseEntity.ok(createdMascota);
    }

    //Se recibe la llamada para eliminar una mascota de la base de datos
    @DeleteMapping("/{idMascota}")
    public ResponseEntity<Void> deleteMascota(@PathVariable Long idMascota) {
        mascotaService.deleteMascota(idMascota);
        return ResponseEntity.noContent().build();
    }
}
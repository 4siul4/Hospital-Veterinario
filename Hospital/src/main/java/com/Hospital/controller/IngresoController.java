package com.Hospital.controller;

import com.Hospital.dto.IngresoDTO;
import com.Hospital.service.IngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/hospitalclinicovet-api/ingreso")
public class IngresoController {

    @Autowired
    private IngresoService ingresoService;

    //Se recibe la llamada para devolver todos los ingresos de la base de datos
    @GetMapping
    public List<IngresoDTO> getIngresos() {
        return ingresoService.getIngresos();
    }

    //Se recibe la llamada para insertar un ingreso en la base de datos
    @PostMapping
    public ResponseEntity<IngresoDTO> postIngreso(@RequestBody IngresoDTO ingresoDTO) {
        IngresoDTO postIngreso = ingresoService.postIngreso(ingresoDTO);
        return ResponseEntity.ok(postIngreso);
    }

    //Se recibe la llamada para actualizar un ingreso en la base de datos
    @PutMapping("/{id}")
    public ResponseEntity<IngresoDTO> putIngreso(@PathVariable Long id, @RequestBody IngresoDTO ingresoDTO) {
        IngresoDTO putIngreso = ingresoService.putIngreso(id, ingresoDTO);
        return ResponseEntity.ok(putIngreso);
    }

    //Se recibe la llamada para eliminar un ingreso en la base de datos
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngreso(@PathVariable Long id) {
        ingresoService.deleteIngreso(id);
        return ResponseEntity.noContent().build();
    }
}
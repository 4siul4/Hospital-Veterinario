package com.Hospital.repository;

import com.Hospital.model.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Clase utilizada para realizar transacciones con la base de datos en la tabla de Ingresos
@Repository
public interface IngresoRepository extends JpaRepository<Ingreso, Long> {
}
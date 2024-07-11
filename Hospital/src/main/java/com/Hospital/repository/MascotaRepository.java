package com.Hospital.repository;

import com.Hospital.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Clase utilizada para realizar transacciones con la base de datos en la tabla de mascotas
@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    //Busca las mascotas con el id aportado que no tengan el campo Deleted a false
    Optional<Mascota> findByIdAndDeletedIsFalse(Long id);
    //Comprueba si existe una mascota con el codigo de identificación aportado
    boolean existsByCodigoIdentificacion(String codigoIdentificacion);
}
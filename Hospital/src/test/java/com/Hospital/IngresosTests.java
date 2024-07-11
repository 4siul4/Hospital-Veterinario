package com.Hospital;

import com.Hospital.model.Ingreso;
import com.Hospital.model.Mascota;
import com.Hospital.repository.IngresoRepository;
import com.Hospital.repository.MascotaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class IngresosTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MascotaRepository mascotaRepository;
    @Autowired
    private IngresoRepository ingresoRepository;
    private TestRestTemplate restTemplate;

    private Mascota mascota;
    private Ingreso ingreso;

    @BeforeEach
    public void setUp() {
        mascota = new Mascota();
        mascota.setId(1L);
        mascota.setDeleted(false);
        mascota.setEdad(10);
        mascota.setEspecie("Perro");
        mascota.setCodigoIdentificacion("TEST12");
        mascota.setDniResponsable("79452348N");
        mascota.setRaza("Bulldog");
        mascota = mascotaRepository.save(mascota);

        ingreso = new Ingreso();
        ingreso.setId(1L);
        ingreso.setFechaAlta(LocalDate.parse("2024-07-09"));
        ingreso.setFechaFin(LocalDate.parse("2024-07-08"));
        ingreso.setMascota(mascota);
        ingreso.setEstado("ALTA");
        ingreso.setDniPersonaRegistra("79452348N");
        ingreso = ingresoRepository.save(ingreso);
    }

    @Test
    public void testGetIngresoOk() throws Exception {
        this.mockMvc.perform(get("/hospitalclinicovet-api/ingreso"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testPostIngresoOk() throws Exception {
        ingresoRepository.delete(ingreso);
        this.mockMvc.perform(post("/hospitalclinicovet-api/ingreso")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"fechaAlta\":\"2024-07-09\",\n" +
                        "    \"fechaFin\":\"2024-07-08\",\n" +
                        "    \"dniPersonaRegistra\":\"79452348N\",\n" +
                        "    \"mascotaId\":\"1\"\n" +
                        "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostIngresoKoNotFound() throws Exception {
        mascota.setDeleted(true);
        mascotaRepository.save(mascota);
        this.mockMvc.perform(post("/hospitalclinicovet-api/ingreso")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"fechaAlta\":\"2024-07-09\",\n" +
                                "    \"fechaFin\":\"2024-07-08\",\n" +
                                "    \"dniPersonaRegistra\":\"79452348N\",\n" +
                                "    \"mascotaId\":\"1\"\n" +
                                "}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testPostIngresoKoNotOwned() throws Exception {
        ingresoRepository.delete(ingreso);
        this.mockMvc.perform(post("/hospitalclinicovet-api/ingreso")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"fechaAlta\":\"2024-07-09\",\n" +
                        "    \"fechaFin\":\"2024-07-08\",\n" +
                        "    \"dniPersonaRegistra\":\"88888348N\",\n" +
                        "    \"mascotaId\":\"1\"\n" +
                        "}"))
                .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void testPutIngresoOk() throws Exception {
        this.mockMvc.perform(put("/hospitalclinicovet-api/ingreso/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"fechaFin\":\"2024-07-09\",\n" +
                                "    \"estado\":\"ANULADO\"\n" +
                                "}"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testPutIngresoKoWrongEstado() throws Exception {
        this.mockMvc.perform(put("/hospitalclinicovet-api/ingreso/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"fechaFin\":\"2024-07-09\",\n" +
                                "    \"estado\":\"CANCELADO\"\n" +
                                "}"))
                .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void testPutIngresoKoNoFechaFin() throws Exception {
        this.mockMvc.perform(put("/hospitalclinicovet-api/ingreso/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"estado\":\"FINALIZADO\"\n" +
                                "}"))
                .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void testPutIngresoKoIngresoNoEncontrado() throws Exception {
        this.mockMvc.perform(put("/hospitalclinicovet-api/ingreso/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"estado\":\"ANULADO\"\n" +
                                "}"))
                .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void testDeleteIngresoOk() throws Exception {
        this.mockMvc.perform(delete("/hospitalclinicovet-api/ingreso/1"))
                .andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteIngresoKo() throws Exception {
        this.mockMvc.perform(delete("/hospitalclinicovet-api/ingreso/2"))
                .andDo(print()).andExpect(status().is4xxClientError());
    }





}

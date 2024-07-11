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
public class MascotasTests {
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

        mascota.addIngreso(ingreso);
        mascotaRepository.save(mascota);
    }

    @Test
    public void testGetMascotaOk() throws Exception {
        this.mockMvc.perform(get("/hospitalclinicovet-api/mascota/1"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testGetMascotaKo() throws Exception {
        this.mockMvc.perform(get("/hospitalclinicovet-api/mascota/2"))
                .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetIngresosByMascotaIdOk() throws Exception {
        this.mockMvc.perform(get("/hospitalclinicovet-api/mascota/1/ingreso"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testGetIngresosByMascotaIdKo() throws Exception {
        this.mockMvc.perform(get("/hospitalclinicovet-api/mascota/2/ingreso"))
                .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void testPostMascotaOk() throws Exception {
        this.mockMvc.perform(post("/hospitalclinicovet-api/mascota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"id\":\"2\",\n" +
                                "    \"especie\":\"Gato\",\n" +
                                "    \"raza\":\"Siamés\",\n" +
                                "    \"edad\":\"5\",\n" +
                                "    \"codigoIdentificacion\":\"AFSD22\",\n" +
                                "    \"dniResponsable\":\"79452348N\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostMascotaKo() throws Exception {
        mascota.setDeleted(true);
        mascotaRepository.save(mascota);
        this.mockMvc.perform(post("/hospitalclinicovet-api/mascota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"id\":\"2\",\n" +
                                "    \"especie\":\"Gato\",\n" +
                                "    \"raza\":\"Siamés\",\n" +
                                "    \"edad\":\"5\",\n" +
                                "    \"codigoIdentificacion\":\"TEST12\",\n" +
                                "    \"dniResponsable\":\"79452348N\"\n" +
                                "}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testDeleteMascotaOk() throws Exception {
        this.mockMvc.perform(delete("/hospitalclinicovet-api/mascota/1"))
                .andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteMascotaKo() throws Exception {
        this.mockMvc.perform(get("/hospitalclinicovet-api/mascota/2/ingreso"))
                .andDo(print()).andExpect(status().is4xxClientError());
    }

}
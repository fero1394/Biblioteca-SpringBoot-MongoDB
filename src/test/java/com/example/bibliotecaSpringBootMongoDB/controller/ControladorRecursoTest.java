package com.example.bibliotecaSpringBootMongoDB.controller;

import mapper.RecursoMapper;
import modelo.Recurso;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import servicio.ServicioRecurso;
import utils.Area;
import utils.Mensaje;
import utils.Tipo;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ControladorRecursoTest {

    @MockBean
    private ServicioRecurso servicioRecurso;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void mostrarDisponibilidad() throws Exception {
        RecursoMapper mapper = new RecursoMapper();
        var recurso = new Recurso("123",
                Tipo.DOCUMENTAL,
                Area.CIENCIAS,
                "La selva");


        Mockito.when(servicioRecurso.comprobarDisponibilidad("123"))
                .thenReturn(new Mensaje(true, "El material está disponible"));

        mockMvc.perform(get("/recursos/disponibilidad/{id}", "123"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))


                // Validate the returned fields
                .andExpect(jsonPath("$.disponible", is(true)));
    }


}

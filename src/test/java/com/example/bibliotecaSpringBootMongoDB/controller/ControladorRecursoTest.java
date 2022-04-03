package com.example.bibliotecaSpringBootMongoDB.controller;

import com.example.bibliotecaSpringBootMongoDB.mapper.RecursoMapper;
import com.example.bibliotecaSpringBootMongoDB.modelo.Recurso;
import com.example.bibliotecaSpringBootMongoDB.servicio.ServicioRecurso;
import com.example.bibliotecaSpringBootMongoDB.utils.Area;
import com.example.bibliotecaSpringBootMongoDB.utils.Mensaje;
import com.example.bibliotecaSpringBootMongoDB.utils.Tipo;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

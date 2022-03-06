package br.com.alura.forum.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;


@RunWith(SpringRunner.class)
//@WebMvcTest //Para camada de controller tem que usar esse anotação
@SpringBootTest
@AutoConfigureMockMvc // so usa esta por nao esta usando a webMvcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)// Para nao substituir minhas configuração do application.properties
//@ActiveProfiles("test")//Considera que o ambiente ativo é o de test
public class AutenticacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveriaDevolver400CasoDadosAutenticacaoEstiverIncorretos() throws Exception{
        URI uri = new URI("/auth");
        String json = "{\"email\":\"invalido@gmail.com\" \"senha\":\"123456\"}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));


    }

}

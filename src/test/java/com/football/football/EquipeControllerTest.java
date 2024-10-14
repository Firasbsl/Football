package com.football.football;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.football.football.model.Equipe;
import com.football.football.model.Joueur;
import com.football.football.repository.EquipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
public class EquipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EquipeRepository equipeRepository;

    @BeforeEach
    public void setup() {
        // Nettoyer le dépôt avant chaque test pour éviter les conflits
        equipeRepository.deleteAll();

        // Ajouter quelques équipes de test pour les tests de récupération
        Equipe equipe1 = new Equipe();
        equipe1.setName("Paris Saint-Germain");
        equipe1.setAcronym("PSG");
        equipe1.setBudget(80000000.0);

        Equipe equipe2 = new Equipe();
        equipe2.setName("Olympique de Marseille");
        equipe2.setAcronym("OM");
        equipe2.setBudget(60000000.0);

        equipeRepository.saveAll(Arrays.asList(equipe1, equipe2));
    }

    @Test
    public void testCreateEquipe() throws Exception {
        Equipe equipe = new Equipe();
        equipe.setName("OGC Nice");
        equipe.setAcronym("OGCN");
        equipe.setBudget(75000000.0);

        String jsonRequest = objectMapper.writeValueAsString(equipe);

        mockMvc.perform(post("/api/equipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("OGC Nice"))
                .andExpect(jsonPath("$.acronym").value("OGCN"))
                .andExpect(jsonPath("$.budget").value(75000000.0));
    }

    @Test
    public void testGetEquipes() throws Exception {
        mockMvc.perform(get("/api/equipes?page=0&size=2&sort=name,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].name").value("Olympique de Marseille"))
                .andExpect(jsonPath("$.content[1].name").value("Paris Saint-Germain"))
                .andExpect(jsonPath("$.content[0].acronym").isNotEmpty())
                .andExpect(jsonPath("$.content[0].budget").isNotEmpty());
    }

    @Test
    public void testCreateEquipeWithJoueurs() throws Exception {
        Equipe equipe = new Equipe();
        equipe.setName("Olympique Lyonnais");
        equipe.setAcronym("OL");
        equipe.setBudget(65000000.0);

        Joueur joueur1 = new Joueur();
        joueur1.setName("Lucas Paqueta");
        joueur1.setPosition("Midfielder");

        Joueur joueur2 = new Joueur();
        joueur2.setName("Anthony Lopes");
        joueur2.setPosition("Goalkeeper");

        equipe.setJoueurs(Arrays.asList(joueur1, joueur2));

        String jsonRequest = objectMapper.writeValueAsString(equipe);

        mockMvc.perform(post("/api/equipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Olympique Lyonnais"))
                .andExpect(jsonPath("$.acronym").value("OL"))
                .andExpect(jsonPath("$.budget").value(65000000.0))
                .andExpect(jsonPath("$.joueurs").isArray())
                .andExpect(jsonPath("$.joueurs[0].name").value("Lucas Paqueta"))
                .andExpect(jsonPath("$.joueurs[1].name").value("Anthony Lopes"));
    }
}
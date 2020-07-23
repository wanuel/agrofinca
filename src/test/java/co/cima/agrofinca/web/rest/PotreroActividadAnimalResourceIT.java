package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.AgrofincaApp;
import co.cima.agrofinca.config.TestSecurityConfiguration;
import co.cima.agrofinca.domain.PotreroActividadAnimal;
import co.cima.agrofinca.repository.PotreroActividadAnimalRepository;
import co.cima.agrofinca.service.PotreroActividadAnimalService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PotreroActividadAnimalResource} REST controller.
 */
@SpringBootTest(classes = { AgrofincaApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PotreroActividadAnimalResourceIT {

    @Autowired
    private PotreroActividadAnimalRepository potreroActividadAnimalRepository;

    @Autowired
    private PotreroActividadAnimalService potreroActividadAnimalService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPotreroActividadAnimalMockMvc;

    private PotreroActividadAnimal potreroActividadAnimal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PotreroActividadAnimal createEntity(EntityManager em) {
        PotreroActividadAnimal potreroActividadAnimal = new PotreroActividadAnimal();
        return potreroActividadAnimal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PotreroActividadAnimal createUpdatedEntity(EntityManager em) {
        PotreroActividadAnimal potreroActividadAnimal = new PotreroActividadAnimal();
        return potreroActividadAnimal;
    }

    @BeforeEach
    public void initTest() {
        potreroActividadAnimal = createEntity(em);
    }

    @Test
    @Transactional
    public void createPotreroActividadAnimal() throws Exception {
        int databaseSizeBeforeCreate = potreroActividadAnimalRepository.findAll().size();
        // Create the PotreroActividadAnimal
        restPotreroActividadAnimalMockMvc.perform(post("/api/potrero-actividad-animals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(potreroActividadAnimal)))
            .andExpect(status().isCreated());

        // Validate the PotreroActividadAnimal in the database
        List<PotreroActividadAnimal> potreroActividadAnimalList = potreroActividadAnimalRepository.findAll();
        assertThat(potreroActividadAnimalList).hasSize(databaseSizeBeforeCreate + 1);
        PotreroActividadAnimal testPotreroActividadAnimal = potreroActividadAnimalList.get(potreroActividadAnimalList.size() - 1);
    }

    @Test
    @Transactional
    public void createPotreroActividadAnimalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = potreroActividadAnimalRepository.findAll().size();

        // Create the PotreroActividadAnimal with an existing ID
        potreroActividadAnimal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPotreroActividadAnimalMockMvc.perform(post("/api/potrero-actividad-animals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(potreroActividadAnimal)))
            .andExpect(status().isBadRequest());

        // Validate the PotreroActividadAnimal in the database
        List<PotreroActividadAnimal> potreroActividadAnimalList = potreroActividadAnimalRepository.findAll();
        assertThat(potreroActividadAnimalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPotreroActividadAnimals() throws Exception {
        // Initialize the database
        potreroActividadAnimalRepository.saveAndFlush(potreroActividadAnimal);

        // Get all the potreroActividadAnimalList
        restPotreroActividadAnimalMockMvc.perform(get("/api/potrero-actividad-animals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(potreroActividadAnimal.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getPotreroActividadAnimal() throws Exception {
        // Initialize the database
        potreroActividadAnimalRepository.saveAndFlush(potreroActividadAnimal);

        // Get the potreroActividadAnimal
        restPotreroActividadAnimalMockMvc.perform(get("/api/potrero-actividad-animals/{id}", potreroActividadAnimal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(potreroActividadAnimal.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPotreroActividadAnimal() throws Exception {
        // Get the potreroActividadAnimal
        restPotreroActividadAnimalMockMvc.perform(get("/api/potrero-actividad-animals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePotreroActividadAnimal() throws Exception {
        // Initialize the database
        potreroActividadAnimalService.save(potreroActividadAnimal);

        int databaseSizeBeforeUpdate = potreroActividadAnimalRepository.findAll().size();

        // Update the potreroActividadAnimal
        PotreroActividadAnimal updatedPotreroActividadAnimal = potreroActividadAnimalRepository.findById(potreroActividadAnimal.getId()).get();
        // Disconnect from session so that the updates on updatedPotreroActividadAnimal are not directly saved in db
        em.detach(updatedPotreroActividadAnimal);

        restPotreroActividadAnimalMockMvc.perform(put("/api/potrero-actividad-animals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPotreroActividadAnimal)))
            .andExpect(status().isOk());

        // Validate the PotreroActividadAnimal in the database
        List<PotreroActividadAnimal> potreroActividadAnimalList = potreroActividadAnimalRepository.findAll();
        assertThat(potreroActividadAnimalList).hasSize(databaseSizeBeforeUpdate);
        PotreroActividadAnimal testPotreroActividadAnimal = potreroActividadAnimalList.get(potreroActividadAnimalList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPotreroActividadAnimal() throws Exception {
        int databaseSizeBeforeUpdate = potreroActividadAnimalRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPotreroActividadAnimalMockMvc.perform(put("/api/potrero-actividad-animals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(potreroActividadAnimal)))
            .andExpect(status().isBadRequest());

        // Validate the PotreroActividadAnimal in the database
        List<PotreroActividadAnimal> potreroActividadAnimalList = potreroActividadAnimalRepository.findAll();
        assertThat(potreroActividadAnimalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePotreroActividadAnimal() throws Exception {
        // Initialize the database
        potreroActividadAnimalService.save(potreroActividadAnimal);

        int databaseSizeBeforeDelete = potreroActividadAnimalRepository.findAll().size();

        // Delete the potreroActividadAnimal
        restPotreroActividadAnimalMockMvc.perform(delete("/api/potrero-actividad-animals/{id}", potreroActividadAnimal.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PotreroActividadAnimal> potreroActividadAnimalList = potreroActividadAnimalRepository.findAll();
        assertThat(potreroActividadAnimalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

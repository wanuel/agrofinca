package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.AgrofincaApp;
import co.cima.agrofinca.config.TestSecurityConfiguration;
import co.cima.agrofinca.domain.AnimalCostos;
import co.cima.agrofinca.repository.AnimalCostosRepository;
import co.cima.agrofinca.service.AnimalCostosService;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AnimalCostosResource} REST controller.
 */
@SpringBootTest(classes = { AgrofincaApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class AnimalCostosResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    @Autowired
    private AnimalCostosRepository animalCostosRepository;

    @Autowired
    private AnimalCostosService animalCostosService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnimalCostosMockMvc;

    private AnimalCostos animalCostos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnimalCostos createEntity(EntityManager em) {
        AnimalCostos animalCostos = new AnimalCostos()
            .fecha(DEFAULT_FECHA)
            .valor(DEFAULT_VALOR);
        return animalCostos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnimalCostos createUpdatedEntity(EntityManager em) {
        AnimalCostos animalCostos = new AnimalCostos()
            .fecha(UPDATED_FECHA)
            .valor(UPDATED_VALOR);
        return animalCostos;
    }

    @BeforeEach
    public void initTest() {
        animalCostos = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnimalCostos() throws Exception {
        int databaseSizeBeforeCreate = animalCostosRepository.findAll().size();
        // Create the AnimalCostos
        restAnimalCostosMockMvc.perform(post("/api/animal-costos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(animalCostos)))
            .andExpect(status().isCreated());

        // Validate the AnimalCostos in the database
        List<AnimalCostos> animalCostosList = animalCostosRepository.findAll();
        assertThat(animalCostosList).hasSize(databaseSizeBeforeCreate + 1);
        AnimalCostos testAnimalCostos = animalCostosList.get(animalCostosList.size() - 1);
        assertThat(testAnimalCostos.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testAnimalCostos.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createAnimalCostosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = animalCostosRepository.findAll().size();

        // Create the AnimalCostos with an existing ID
        animalCostos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnimalCostosMockMvc.perform(post("/api/animal-costos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(animalCostos)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalCostos in the database
        List<AnimalCostos> animalCostosList = animalCostosRepository.findAll();
        assertThat(animalCostosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = animalCostosRepository.findAll().size();
        // set the field null
        animalCostos.setFecha(null);

        // Create the AnimalCostos, which fails.


        restAnimalCostosMockMvc.perform(post("/api/animal-costos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(animalCostos)))
            .andExpect(status().isBadRequest());

        List<AnimalCostos> animalCostosList = animalCostosRepository.findAll();
        assertThat(animalCostosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = animalCostosRepository.findAll().size();
        // set the field null
        animalCostos.setValor(null);

        // Create the AnimalCostos, which fails.


        restAnimalCostosMockMvc.perform(post("/api/animal-costos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(animalCostos)))
            .andExpect(status().isBadRequest());

        List<AnimalCostos> animalCostosList = animalCostosRepository.findAll();
        assertThat(animalCostosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnimalCostos() throws Exception {
        // Initialize the database
        animalCostosRepository.saveAndFlush(animalCostos);

        // Get all the animalCostosList
        restAnimalCostosMockMvc.perform(get("/api/animal-costos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animalCostos.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())));
    }
    
    @Test
    @Transactional
    public void getAnimalCostos() throws Exception {
        // Initialize the database
        animalCostosRepository.saveAndFlush(animalCostos);

        // Get the animalCostos
        restAnimalCostosMockMvc.perform(get("/api/animal-costos/{id}", animalCostos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(animalCostos.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAnimalCostos() throws Exception {
        // Get the animalCostos
        restAnimalCostosMockMvc.perform(get("/api/animal-costos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnimalCostos() throws Exception {
        // Initialize the database
        animalCostosService.save(animalCostos);

        int databaseSizeBeforeUpdate = animalCostosRepository.findAll().size();

        // Update the animalCostos
        AnimalCostos updatedAnimalCostos = animalCostosRepository.findById(animalCostos.getId()).get();
        // Disconnect from session so that the updates on updatedAnimalCostos are not directly saved in db
        em.detach(updatedAnimalCostos);
        updatedAnimalCostos
            .fecha(UPDATED_FECHA)
            .valor(UPDATED_VALOR);

        restAnimalCostosMockMvc.perform(put("/api/animal-costos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnimalCostos)))
            .andExpect(status().isOk());

        // Validate the AnimalCostos in the database
        List<AnimalCostos> animalCostosList = animalCostosRepository.findAll();
        assertThat(animalCostosList).hasSize(databaseSizeBeforeUpdate);
        AnimalCostos testAnimalCostos = animalCostosList.get(animalCostosList.size() - 1);
        assertThat(testAnimalCostos.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testAnimalCostos.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingAnimalCostos() throws Exception {
        int databaseSizeBeforeUpdate = animalCostosRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnimalCostosMockMvc.perform(put("/api/animal-costos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(animalCostos)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalCostos in the database
        List<AnimalCostos> animalCostosList = animalCostosRepository.findAll();
        assertThat(animalCostosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnimalCostos() throws Exception {
        // Initialize the database
        animalCostosService.save(animalCostos);

        int databaseSizeBeforeDelete = animalCostosRepository.findAll().size();

        // Delete the animalCostos
        restAnimalCostosMockMvc.perform(delete("/api/animal-costos/{id}", animalCostos.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnimalCostos> animalCostosList = animalCostosRepository.findAll();
        assertThat(animalCostosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

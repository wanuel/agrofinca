package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.AgrofincaApp;
import co.cima.agrofinca.config.TestSecurityConfiguration;
import co.cima.agrofinca.domain.Finca;
import co.cima.agrofinca.repository.FincaRepository;
import co.cima.agrofinca.service.FincaService;
import co.cima.agrofinca.service.dto.FincaCriteria;
import co.cima.agrofinca.service.FincaQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FincaResource} REST controller.
 */
@SpringBootTest(classes = { AgrofincaApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class FincaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AREA = new BigDecimal(1);
    private static final BigDecimal UPDATED_AREA = new BigDecimal(2);
    private static final BigDecimal SMALLER_AREA = new BigDecimal(1 - 1);

    @Autowired
    private FincaRepository fincaRepository;

    @Autowired
    private FincaService fincaService;

    @Autowired
    private FincaQueryService fincaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFincaMockMvc;

    private Finca finca;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Finca createEntity(EntityManager em) {
        Finca finca = new Finca()
            .nombre(DEFAULT_NOMBRE)
            .area(DEFAULT_AREA);
        return finca;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Finca createUpdatedEntity(EntityManager em) {
        Finca finca = new Finca()
            .nombre(UPDATED_NOMBRE)
            .area(UPDATED_AREA);
        return finca;
    }

    @BeforeEach
    public void initTest() {
        finca = createEntity(em);
    }

    @Test
    @Transactional
    public void createFinca() throws Exception {
        int databaseSizeBeforeCreate = fincaRepository.findAll().size();
        // Create the Finca
        restFincaMockMvc.perform(post("/api/fincas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(finca)))
            .andExpect(status().isCreated());

        // Validate the Finca in the database
        List<Finca> fincaList = fincaRepository.findAll();
        assertThat(fincaList).hasSize(databaseSizeBeforeCreate + 1);
        Finca testFinca = fincaList.get(fincaList.size() - 1);
        assertThat(testFinca.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testFinca.getArea()).isEqualTo(DEFAULT_AREA);
    }

    @Test
    @Transactional
    public void createFincaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fincaRepository.findAll().size();

        // Create the Finca with an existing ID
        finca.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFincaMockMvc.perform(post("/api/fincas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(finca)))
            .andExpect(status().isBadRequest());

        // Validate the Finca in the database
        List<Finca> fincaList = fincaRepository.findAll();
        assertThat(fincaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAreaIsRequired() throws Exception {
        int databaseSizeBeforeTest = fincaRepository.findAll().size();
        // set the field null
        finca.setArea(null);

        // Create the Finca, which fails.


        restFincaMockMvc.perform(post("/api/fincas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(finca)))
            .andExpect(status().isBadRequest());

        List<Finca> fincaList = fincaRepository.findAll();
        assertThat(fincaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFincas() throws Exception {
        // Initialize the database
        fincaRepository.saveAndFlush(finca);

        // Get all the fincaList
        restFincaMockMvc.perform(get("/api/fincas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(finca.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.intValue())));
    }
    
    @Test
    @Transactional
    public void getFinca() throws Exception {
        // Initialize the database
        fincaRepository.saveAndFlush(finca);

        // Get the finca
        restFincaMockMvc.perform(get("/api/fincas/{id}", finca.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(finca.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA.intValue()));
    }


    @Test
    @Transactional
    public void getFincasByIdFiltering() throws Exception {
        // Initialize the database
        fincaRepository.saveAndFlush(finca);

        Long id = finca.getId();

        defaultFincaShouldBeFound("id.equals=" + id);
        defaultFincaShouldNotBeFound("id.notEquals=" + id);

        defaultFincaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFincaShouldNotBeFound("id.greaterThan=" + id);

        defaultFincaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFincaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFincasByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        fincaRepository.saveAndFlush(finca);

        // Get all the fincaList where nombre equals to DEFAULT_NOMBRE
        defaultFincaShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the fincaList where nombre equals to UPDATED_NOMBRE
        defaultFincaShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllFincasByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fincaRepository.saveAndFlush(finca);

        // Get all the fincaList where nombre not equals to DEFAULT_NOMBRE
        defaultFincaShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the fincaList where nombre not equals to UPDATED_NOMBRE
        defaultFincaShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllFincasByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        fincaRepository.saveAndFlush(finca);

        // Get all the fincaList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultFincaShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the fincaList where nombre equals to UPDATED_NOMBRE
        defaultFincaShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllFincasByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        fincaRepository.saveAndFlush(finca);

        // Get all the fincaList where nombre is not null
        defaultFincaShouldBeFound("nombre.specified=true");

        // Get all the fincaList where nombre is null
        defaultFincaShouldNotBeFound("nombre.specified=false");
    }
                @Test
    @Transactional
    public void getAllFincasByNombreContainsSomething() throws Exception {
        // Initialize the database
        fincaRepository.saveAndFlush(finca);

        // Get all the fincaList where nombre contains DEFAULT_NOMBRE
        defaultFincaShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the fincaList where nombre contains UPDATED_NOMBRE
        defaultFincaShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllFincasByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        fincaRepository.saveAndFlush(finca);

        // Get all the fincaList where nombre does not contain DEFAULT_NOMBRE
        defaultFincaShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the fincaList where nombre does not contain UPDATED_NOMBRE
        defaultFincaShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }


    @Test
    @Transactional
    public void getAllFincasByAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        fincaRepository.saveAndFlush(finca);

        // Get all the fincaList where area equals to DEFAULT_AREA
        defaultFincaShouldBeFound("area.equals=" + DEFAULT_AREA);

        // Get all the fincaList where area equals to UPDATED_AREA
        defaultFincaShouldNotBeFound("area.equals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    public void getAllFincasByAreaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fincaRepository.saveAndFlush(finca);

        // Get all the fincaList where area not equals to DEFAULT_AREA
        defaultFincaShouldNotBeFound("area.notEquals=" + DEFAULT_AREA);

        // Get all the fincaList where area not equals to UPDATED_AREA
        defaultFincaShouldBeFound("area.notEquals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    public void getAllFincasByAreaIsInShouldWork() throws Exception {
        // Initialize the database
        fincaRepository.saveAndFlush(finca);

        // Get all the fincaList where area in DEFAULT_AREA or UPDATED_AREA
        defaultFincaShouldBeFound("area.in=" + DEFAULT_AREA + "," + UPDATED_AREA);

        // Get all the fincaList where area equals to UPDATED_AREA
        defaultFincaShouldNotBeFound("area.in=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    public void getAllFincasByAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fincaRepository.saveAndFlush(finca);

        // Get all the fincaList where area is not null
        defaultFincaShouldBeFound("area.specified=true");

        // Get all the fincaList where area is null
        defaultFincaShouldNotBeFound("area.specified=false");
    }

    @Test
    @Transactional
    public void getAllFincasByAreaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fincaRepository.saveAndFlush(finca);

        // Get all the fincaList where area is greater than or equal to DEFAULT_AREA
        defaultFincaShouldBeFound("area.greaterThanOrEqual=" + DEFAULT_AREA);

        // Get all the fincaList where area is greater than or equal to UPDATED_AREA
        defaultFincaShouldNotBeFound("area.greaterThanOrEqual=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    public void getAllFincasByAreaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fincaRepository.saveAndFlush(finca);

        // Get all the fincaList where area is less than or equal to DEFAULT_AREA
        defaultFincaShouldBeFound("area.lessThanOrEqual=" + DEFAULT_AREA);

        // Get all the fincaList where area is less than or equal to SMALLER_AREA
        defaultFincaShouldNotBeFound("area.lessThanOrEqual=" + SMALLER_AREA);
    }

    @Test
    @Transactional
    public void getAllFincasByAreaIsLessThanSomething() throws Exception {
        // Initialize the database
        fincaRepository.saveAndFlush(finca);

        // Get all the fincaList where area is less than DEFAULT_AREA
        defaultFincaShouldNotBeFound("area.lessThan=" + DEFAULT_AREA);

        // Get all the fincaList where area is less than UPDATED_AREA
        defaultFincaShouldBeFound("area.lessThan=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    public void getAllFincasByAreaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fincaRepository.saveAndFlush(finca);

        // Get all the fincaList where area is greater than DEFAULT_AREA
        defaultFincaShouldNotBeFound("area.greaterThan=" + DEFAULT_AREA);

        // Get all the fincaList where area is greater than SMALLER_AREA
        defaultFincaShouldBeFound("area.greaterThan=" + SMALLER_AREA);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFincaShouldBeFound(String filter) throws Exception {
        restFincaMockMvc.perform(get("/api/fincas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(finca.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.intValue())));

        // Check, that the count call also returns 1
        restFincaMockMvc.perform(get("/api/fincas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFincaShouldNotBeFound(String filter) throws Exception {
        restFincaMockMvc.perform(get("/api/fincas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFincaMockMvc.perform(get("/api/fincas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFinca() throws Exception {
        // Get the finca
        restFincaMockMvc.perform(get("/api/fincas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFinca() throws Exception {
        // Initialize the database
        fincaService.save(finca);

        int databaseSizeBeforeUpdate = fincaRepository.findAll().size();

        // Update the finca
        Finca updatedFinca = fincaRepository.findById(finca.getId()).get();
        // Disconnect from session so that the updates on updatedFinca are not directly saved in db
        em.detach(updatedFinca);
        updatedFinca
            .nombre(UPDATED_NOMBRE)
            .area(UPDATED_AREA);

        restFincaMockMvc.perform(put("/api/fincas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFinca)))
            .andExpect(status().isOk());

        // Validate the Finca in the database
        List<Finca> fincaList = fincaRepository.findAll();
        assertThat(fincaList).hasSize(databaseSizeBeforeUpdate);
        Finca testFinca = fincaList.get(fincaList.size() - 1);
        assertThat(testFinca.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testFinca.getArea()).isEqualTo(UPDATED_AREA);
    }

    @Test
    @Transactional
    public void updateNonExistingFinca() throws Exception {
        int databaseSizeBeforeUpdate = fincaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFincaMockMvc.perform(put("/api/fincas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(finca)))
            .andExpect(status().isBadRequest());

        // Validate the Finca in the database
        List<Finca> fincaList = fincaRepository.findAll();
        assertThat(fincaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFinca() throws Exception {
        // Initialize the database
        fincaService.save(finca);

        int databaseSizeBeforeDelete = fincaRepository.findAll().size();

        // Delete the finca
        restFincaMockMvc.perform(delete("/api/fincas/{id}", finca.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Finca> fincaList = fincaRepository.findAll();
        assertThat(fincaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

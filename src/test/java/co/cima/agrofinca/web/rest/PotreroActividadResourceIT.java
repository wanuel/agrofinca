package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.AgrofincaApp;
import co.cima.agrofinca.config.TestSecurityConfiguration;
import co.cima.agrofinca.domain.PotreroActividad;
import co.cima.agrofinca.repository.PotreroActividadRepository;
import co.cima.agrofinca.service.PotreroActividadService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.cima.agrofinca.domain.enumeration.SINO;
/**
 * Integration tests for the {@link PotreroActividadResource} REST controller.
 */
@SpringBootTest(classes = { AgrofincaApp.class, TestSecurityConfiguration.class })
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PotreroActividadResourceIT {

    private static final LocalDate DEFAULT_FECHA_INGRESO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INGRESO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_SALIDA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_SALIDA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CANTIDAD_BOVINOS = 1;
    private static final Integer UPDATED_CANTIDAD_BOVINOS = 2;

    private static final Integer DEFAULT_CANTIDAD_EQUINOS = 1;
    private static final Integer UPDATED_CANTIDAD_EQUINOS = 2;

    private static final Integer DEFAULT_CANTIDAD_MULARES = 1;
    private static final Integer UPDATED_CANTIDAD_MULARES = 2;

    private static final LocalDate DEFAULT_FECHA_LIMPIA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_LIMPIA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DIAS_DESCANSO = 1;
    private static final Integer UPDATED_DIAS_DESCANSO = 2;

    private static final Integer DEFAULT_DIAS_CARGA = 1;
    private static final Integer UPDATED_DIAS_CARGA = 2;

    private static final SINO DEFAULT_OCUPADO = SINO.SI;
    private static final SINO UPDATED_OCUPADO = SINO.NO;

    @Autowired
    private PotreroActividadRepository potreroActividadRepository;

    @Mock
    private PotreroActividadRepository potreroActividadRepositoryMock;

    @Mock
    private PotreroActividadService potreroActividadServiceMock;

    @Autowired
    private PotreroActividadService potreroActividadService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPotreroActividadMockMvc;

    private PotreroActividad potreroActividad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PotreroActividad createEntity(EntityManager em) {
        PotreroActividad potreroActividad = new PotreroActividad()
            .fechaIngreso(DEFAULT_FECHA_INGRESO)
            .fechaSalida(DEFAULT_FECHA_SALIDA)
            .cantidadBovinos(DEFAULT_CANTIDAD_BOVINOS)
            .cantidadEquinos(DEFAULT_CANTIDAD_EQUINOS)
            .cantidadMulares(DEFAULT_CANTIDAD_MULARES)
            .fechaLimpia(DEFAULT_FECHA_LIMPIA)
            .diasDescanso(DEFAULT_DIAS_DESCANSO)
            .diasCarga(DEFAULT_DIAS_CARGA)
            .ocupado(DEFAULT_OCUPADO);
        return potreroActividad;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PotreroActividad createUpdatedEntity(EntityManager em) {
        PotreroActividad potreroActividad = new PotreroActividad()
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .fechaSalida(UPDATED_FECHA_SALIDA)
            .cantidadBovinos(UPDATED_CANTIDAD_BOVINOS)
            .cantidadEquinos(UPDATED_CANTIDAD_EQUINOS)
            .cantidadMulares(UPDATED_CANTIDAD_MULARES)
            .fechaLimpia(UPDATED_FECHA_LIMPIA)
            .diasDescanso(UPDATED_DIAS_DESCANSO)
            .diasCarga(UPDATED_DIAS_CARGA)
            .ocupado(UPDATED_OCUPADO);
        return potreroActividad;
    }

    @BeforeEach
    public void initTest() {
        potreroActividad = createEntity(em);
    }

    @Test
    @Transactional
    public void createPotreroActividad() throws Exception {
        int databaseSizeBeforeCreate = potreroActividadRepository.findAll().size();
        // Create the PotreroActividad
        restPotreroActividadMockMvc.perform(post("/api/potrero-actividads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(potreroActividad)))
            .andExpect(status().isCreated());

        // Validate the PotreroActividad in the database
        List<PotreroActividad> potreroActividadList = potreroActividadRepository.findAll();
        assertThat(potreroActividadList).hasSize(databaseSizeBeforeCreate + 1);
        PotreroActividad testPotreroActividad = potreroActividadList.get(potreroActividadList.size() - 1);
        assertThat(testPotreroActividad.getFechaIngreso()).isEqualTo(DEFAULT_FECHA_INGRESO);
        assertThat(testPotreroActividad.getFechaSalida()).isEqualTo(DEFAULT_FECHA_SALIDA);
        assertThat(testPotreroActividad.getCantidadBovinos()).isEqualTo(DEFAULT_CANTIDAD_BOVINOS);
        assertThat(testPotreroActividad.getCantidadEquinos()).isEqualTo(DEFAULT_CANTIDAD_EQUINOS);
        assertThat(testPotreroActividad.getCantidadMulares()).isEqualTo(DEFAULT_CANTIDAD_MULARES);
        assertThat(testPotreroActividad.getFechaLimpia()).isEqualTo(DEFAULT_FECHA_LIMPIA);
        assertThat(testPotreroActividad.getDiasDescanso()).isEqualTo(DEFAULT_DIAS_DESCANSO);
        assertThat(testPotreroActividad.getDiasCarga()).isEqualTo(DEFAULT_DIAS_CARGA);
        assertThat(testPotreroActividad.getOcupado()).isEqualTo(DEFAULT_OCUPADO);
    }

    @Test
    @Transactional
    public void createPotreroActividadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = potreroActividadRepository.findAll().size();

        // Create the PotreroActividad with an existing ID
        potreroActividad.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPotreroActividadMockMvc.perform(post("/api/potrero-actividads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(potreroActividad)))
            .andExpect(status().isBadRequest());

        // Validate the PotreroActividad in the database
        List<PotreroActividad> potreroActividadList = potreroActividadRepository.findAll();
        assertThat(potreroActividadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFechaIngresoIsRequired() throws Exception {
        int databaseSizeBeforeTest = potreroActividadRepository.findAll().size();
        // set the field null
        potreroActividad.setFechaIngreso(null);

        // Create the PotreroActividad, which fails.


        restPotreroActividadMockMvc.perform(post("/api/potrero-actividads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(potreroActividad)))
            .andExpect(status().isBadRequest());

        List<PotreroActividad> potreroActividadList = potreroActividadRepository.findAll();
        assertThat(potreroActividadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCantidadBovinosIsRequired() throws Exception {
        int databaseSizeBeforeTest = potreroActividadRepository.findAll().size();
        // set the field null
        potreroActividad.setCantidadBovinos(null);

        // Create the PotreroActividad, which fails.


        restPotreroActividadMockMvc.perform(post("/api/potrero-actividads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(potreroActividad)))
            .andExpect(status().isBadRequest());

        List<PotreroActividad> potreroActividadList = potreroActividadRepository.findAll();
        assertThat(potreroActividadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCantidadEquinosIsRequired() throws Exception {
        int databaseSizeBeforeTest = potreroActividadRepository.findAll().size();
        // set the field null
        potreroActividad.setCantidadEquinos(null);

        // Create the PotreroActividad, which fails.


        restPotreroActividadMockMvc.perform(post("/api/potrero-actividads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(potreroActividad)))
            .andExpect(status().isBadRequest());

        List<PotreroActividad> potreroActividadList = potreroActividadRepository.findAll();
        assertThat(potreroActividadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCantidadMularesIsRequired() throws Exception {
        int databaseSizeBeforeTest = potreroActividadRepository.findAll().size();
        // set the field null
        potreroActividad.setCantidadMulares(null);

        // Create the PotreroActividad, which fails.


        restPotreroActividadMockMvc.perform(post("/api/potrero-actividads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(potreroActividad)))
            .andExpect(status().isBadRequest());

        List<PotreroActividad> potreroActividadList = potreroActividadRepository.findAll();
        assertThat(potreroActividadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOcupadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = potreroActividadRepository.findAll().size();
        // set the field null
        potreroActividad.setOcupado(null);

        // Create the PotreroActividad, which fails.


        restPotreroActividadMockMvc.perform(post("/api/potrero-actividads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(potreroActividad)))
            .andExpect(status().isBadRequest());

        List<PotreroActividad> potreroActividadList = potreroActividadRepository.findAll();
        assertThat(potreroActividadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPotreroActividads() throws Exception {
        // Initialize the database
        potreroActividadRepository.saveAndFlush(potreroActividad);

        // Get all the potreroActividadList
        restPotreroActividadMockMvc.perform(get("/api/potrero-actividads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(potreroActividad.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaIngreso").value(hasItem(DEFAULT_FECHA_INGRESO.toString())))
            .andExpect(jsonPath("$.[*].fechaSalida").value(hasItem(DEFAULT_FECHA_SALIDA.toString())))
            .andExpect(jsonPath("$.[*].cantidadBovinos").value(hasItem(DEFAULT_CANTIDAD_BOVINOS)))
            .andExpect(jsonPath("$.[*].cantidadEquinos").value(hasItem(DEFAULT_CANTIDAD_EQUINOS)))
            .andExpect(jsonPath("$.[*].cantidadMulares").value(hasItem(DEFAULT_CANTIDAD_MULARES)))
            .andExpect(jsonPath("$.[*].fechaLimpia").value(hasItem(DEFAULT_FECHA_LIMPIA.toString())))
            .andExpect(jsonPath("$.[*].diasDescanso").value(hasItem(DEFAULT_DIAS_DESCANSO)))
            .andExpect(jsonPath("$.[*].diasCarga").value(hasItem(DEFAULT_DIAS_CARGA)))
            .andExpect(jsonPath("$.[*].ocupado").value(hasItem(DEFAULT_OCUPADO.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPotreroActividadsWithEagerRelationshipsIsEnabled() throws Exception {
        when(potreroActividadServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPotreroActividadMockMvc.perform(get("/api/potrero-actividads?eagerload=true"))
            .andExpect(status().isOk());

        verify(potreroActividadServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPotreroActividadsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(potreroActividadServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPotreroActividadMockMvc.perform(get("/api/potrero-actividads?eagerload=true"))
            .andExpect(status().isOk());

        verify(potreroActividadServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPotreroActividad() throws Exception {
        // Initialize the database
        potreroActividadRepository.saveAndFlush(potreroActividad);

        // Get the potreroActividad
        restPotreroActividadMockMvc.perform(get("/api/potrero-actividads/{id}", potreroActividad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(potreroActividad.getId().intValue()))
            .andExpect(jsonPath("$.fechaIngreso").value(DEFAULT_FECHA_INGRESO.toString()))
            .andExpect(jsonPath("$.fechaSalida").value(DEFAULT_FECHA_SALIDA.toString()))
            .andExpect(jsonPath("$.cantidadBovinos").value(DEFAULT_CANTIDAD_BOVINOS))
            .andExpect(jsonPath("$.cantidadEquinos").value(DEFAULT_CANTIDAD_EQUINOS))
            .andExpect(jsonPath("$.cantidadMulares").value(DEFAULT_CANTIDAD_MULARES))
            .andExpect(jsonPath("$.fechaLimpia").value(DEFAULT_FECHA_LIMPIA.toString()))
            .andExpect(jsonPath("$.diasDescanso").value(DEFAULT_DIAS_DESCANSO))
            .andExpect(jsonPath("$.diasCarga").value(DEFAULT_DIAS_CARGA))
            .andExpect(jsonPath("$.ocupado").value(DEFAULT_OCUPADO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPotreroActividad() throws Exception {
        // Get the potreroActividad
        restPotreroActividadMockMvc.perform(get("/api/potrero-actividads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePotreroActividad() throws Exception {
        // Initialize the database
        potreroActividadService.save(potreroActividad);

        int databaseSizeBeforeUpdate = potreroActividadRepository.findAll().size();

        // Update the potreroActividad
        PotreroActividad updatedPotreroActividad = potreroActividadRepository.findById(potreroActividad.getId()).get();
        // Disconnect from session so that the updates on updatedPotreroActividad are not directly saved in db
        em.detach(updatedPotreroActividad);
        updatedPotreroActividad
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .fechaSalida(UPDATED_FECHA_SALIDA)
            .cantidadBovinos(UPDATED_CANTIDAD_BOVINOS)
            .cantidadEquinos(UPDATED_CANTIDAD_EQUINOS)
            .cantidadMulares(UPDATED_CANTIDAD_MULARES)
            .fechaLimpia(UPDATED_FECHA_LIMPIA)
            .diasDescanso(UPDATED_DIAS_DESCANSO)
            .diasCarga(UPDATED_DIAS_CARGA)
            .ocupado(UPDATED_OCUPADO);

        restPotreroActividadMockMvc.perform(put("/api/potrero-actividads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPotreroActividad)))
            .andExpect(status().isOk());

        // Validate the PotreroActividad in the database
        List<PotreroActividad> potreroActividadList = potreroActividadRepository.findAll();
        assertThat(potreroActividadList).hasSize(databaseSizeBeforeUpdate);
        PotreroActividad testPotreroActividad = potreroActividadList.get(potreroActividadList.size() - 1);
        assertThat(testPotreroActividad.getFechaIngreso()).isEqualTo(UPDATED_FECHA_INGRESO);
        assertThat(testPotreroActividad.getFechaSalida()).isEqualTo(UPDATED_FECHA_SALIDA);
        assertThat(testPotreroActividad.getCantidadBovinos()).isEqualTo(UPDATED_CANTIDAD_BOVINOS);
        assertThat(testPotreroActividad.getCantidadEquinos()).isEqualTo(UPDATED_CANTIDAD_EQUINOS);
        assertThat(testPotreroActividad.getCantidadMulares()).isEqualTo(UPDATED_CANTIDAD_MULARES);
        assertThat(testPotreroActividad.getFechaLimpia()).isEqualTo(UPDATED_FECHA_LIMPIA);
        assertThat(testPotreroActividad.getDiasDescanso()).isEqualTo(UPDATED_DIAS_DESCANSO);
        assertThat(testPotreroActividad.getDiasCarga()).isEqualTo(UPDATED_DIAS_CARGA);
        assertThat(testPotreroActividad.getOcupado()).isEqualTo(UPDATED_OCUPADO);
    }

    @Test
    @Transactional
    public void updateNonExistingPotreroActividad() throws Exception {
        int databaseSizeBeforeUpdate = potreroActividadRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPotreroActividadMockMvc.perform(put("/api/potrero-actividads").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(potreroActividad)))
            .andExpect(status().isBadRequest());

        // Validate the PotreroActividad in the database
        List<PotreroActividad> potreroActividadList = potreroActividadRepository.findAll();
        assertThat(potreroActividadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePotreroActividad() throws Exception {
        // Initialize the database
        potreroActividadService.save(potreroActividad);

        int databaseSizeBeforeDelete = potreroActividadRepository.findAll().size();

        // Delete the potreroActividad
        restPotreroActividadMockMvc.perform(delete("/api/potrero-actividads/{id}", potreroActividad.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PotreroActividad> potreroActividadList = potreroActividadRepository.findAll();
        assertThat(potreroActividadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

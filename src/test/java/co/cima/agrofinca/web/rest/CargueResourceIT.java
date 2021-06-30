package co.cima.agrofinca.web.rest;

import static co.cima.agrofinca.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.cima.agrofinca.IntegrationTest;
import co.cima.agrofinca.domain.Cargue;
import co.cima.agrofinca.repository.CargueRepository;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CargueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CargueResourceIT {

  private static final Integer DEFAULT_CARG_NRO_REGISTROS = 1;
  private static final Integer UPDATED_CARG_NRO_REGISTROS = 2;

  private static final String DEFAULT_CARG_JSON = "AAAAAAAAAA";
  private static final String UPDATED_CARG_JSON = "BBBBBBBBBB";

  private static final Integer DEFAULT_CARG_ENTIDAD = 1;
  private static final Integer UPDATED_CARG_ENTIDAD = 2;

  private static final String DEFAULT_CARG_NOMBRE_ARCHIVO = "AAAAAAAAAA";
  private static final String UPDATED_CARG_NOMBRE_ARCHIVO = "BBBBBBBBBB";

  private static final String DEFAULT_CARG_ESTADO = "AAAAAAAAAA";
  private static final String UPDATED_CARG_ESTADO = "BBBBBBBBBB";

  private static final String DEFAULT_CARG_TIPO = "AAAAAAAAAA";
  private static final String UPDATED_CARG_TIPO = "BBBBBBBBBB";

  private static final Integer DEFAULT_CARG_ES_REPROCESO = 1;
  private static final Integer UPDATED_CARG_ES_REPROCESO = 2;

  private static final String DEFAULT_CARG_HASH = "AAAAAAAAAA";
  private static final String UPDATED_CARG_HASH = "BBBBBBBBBB";

  private static final String DEFAULT_USUARIO_CREACION = "AAAAAAAAAA";
  private static final String UPDATED_USUARIO_CREACION = "BBBBBBBBBB";

  private static final ZonedDateTime DEFAULT_FECHA_CREACION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
  private static final ZonedDateTime UPDATED_FECHA_CREACION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

  private static final String DEFAULT_USUARIO_MODIFICACION = "AAAAAAAAAA";
  private static final String UPDATED_USUARIO_MODIFICACION = "BBBBBBBBBB";

  private static final ZonedDateTime DEFAULT_FECHA_MODIFICACION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
  private static final ZonedDateTime UPDATED_FECHA_MODIFICACION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

  private static final String ENTITY_API_URL = "/api/cargues";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private CargueRepository cargueRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restCargueMockMvc;

  private Cargue cargue;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Cargue createEntity(EntityManager em) {
    Cargue cargue = new Cargue()
      .cargNroRegistros(DEFAULT_CARG_NRO_REGISTROS)
      .cargJson(DEFAULT_CARG_JSON)
      .cargEntidad(DEFAULT_CARG_ENTIDAD)
      .cargNombreArchivo(DEFAULT_CARG_NOMBRE_ARCHIVO)
      .cargEstado(DEFAULT_CARG_ESTADO)
      .cargTipo(DEFAULT_CARG_TIPO)
      .cargEsReproceso(DEFAULT_CARG_ES_REPROCESO)
      .cargHash(DEFAULT_CARG_HASH)
      .usuarioCreacion(DEFAULT_USUARIO_CREACION)
      .fechaCreacion(DEFAULT_FECHA_CREACION)
      .usuarioModificacion(DEFAULT_USUARIO_MODIFICACION)
      .fechaModificacion(DEFAULT_FECHA_MODIFICACION);
    return cargue;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Cargue createUpdatedEntity(EntityManager em) {
    Cargue cargue = new Cargue()
      .cargNroRegistros(UPDATED_CARG_NRO_REGISTROS)
      .cargJson(UPDATED_CARG_JSON)
      .cargEntidad(UPDATED_CARG_ENTIDAD)
      .cargNombreArchivo(UPDATED_CARG_NOMBRE_ARCHIVO)
      .cargEstado(UPDATED_CARG_ESTADO)
      .cargTipo(UPDATED_CARG_TIPO)
      .cargEsReproceso(UPDATED_CARG_ES_REPROCESO)
      .cargHash(UPDATED_CARG_HASH)
      .usuarioCreacion(UPDATED_USUARIO_CREACION)
      .fechaCreacion(UPDATED_FECHA_CREACION)
      .usuarioModificacion(UPDATED_USUARIO_MODIFICACION)
      .fechaModificacion(UPDATED_FECHA_MODIFICACION);
    return cargue;
  }

  @BeforeEach
  public void initTest() {
    cargue = createEntity(em);
  }

  @Test
  @Transactional
  void createCargue() throws Exception {
    int databaseSizeBeforeCreate = cargueRepository.findAll().size();
    // Create the Cargue
    restCargueMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cargue)))
      .andExpect(status().isCreated());

    // Validate the Cargue in the database
    List<Cargue> cargueList = cargueRepository.findAll();
    assertThat(cargueList).hasSize(databaseSizeBeforeCreate + 1);
    Cargue testCargue = cargueList.get(cargueList.size() - 1);
    assertThat(testCargue.getCargNroRegistros()).isEqualTo(DEFAULT_CARG_NRO_REGISTROS);
    assertThat(testCargue.getCargJson()).isEqualTo(DEFAULT_CARG_JSON);
    assertThat(testCargue.getCargEntidad()).isEqualTo(DEFAULT_CARG_ENTIDAD);
    assertThat(testCargue.getCargNombreArchivo()).isEqualTo(DEFAULT_CARG_NOMBRE_ARCHIVO);
    assertThat(testCargue.getCargEstado()).isEqualTo(DEFAULT_CARG_ESTADO);
    assertThat(testCargue.getCargTipo()).isEqualTo(DEFAULT_CARG_TIPO);
    assertThat(testCargue.getCargEsReproceso()).isEqualTo(DEFAULT_CARG_ES_REPROCESO);
    assertThat(testCargue.getCargHash()).isEqualTo(DEFAULT_CARG_HASH);
    assertThat(testCargue.getUsuarioCreacion()).isEqualTo(DEFAULT_USUARIO_CREACION);
    assertThat(testCargue.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
    assertThat(testCargue.getUsuarioModificacion()).isEqualTo(DEFAULT_USUARIO_MODIFICACION);
    assertThat(testCargue.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
  }

  @Test
  @Transactional
  void createCargueWithExistingId() throws Exception {
    // Create the Cargue with an existing ID
    cargue.setId(1L);

    int databaseSizeBeforeCreate = cargueRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restCargueMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cargue)))
      .andExpect(status().isBadRequest());

    // Validate the Cargue in the database
    List<Cargue> cargueList = cargueRepository.findAll();
    assertThat(cargueList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkCargNroRegistrosIsRequired() throws Exception {
    int databaseSizeBeforeTest = cargueRepository.findAll().size();
    // set the field null
    cargue.setCargNroRegistros(null);

    // Create the Cargue, which fails.

    restCargueMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cargue)))
      .andExpect(status().isBadRequest());

    List<Cargue> cargueList = cargueRepository.findAll();
    assertThat(cargueList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkCargJsonIsRequired() throws Exception {
    int databaseSizeBeforeTest = cargueRepository.findAll().size();
    // set the field null
    cargue.setCargJson(null);

    // Create the Cargue, which fails.

    restCargueMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cargue)))
      .andExpect(status().isBadRequest());

    List<Cargue> cargueList = cargueRepository.findAll();
    assertThat(cargueList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkCargEntidadIsRequired() throws Exception {
    int databaseSizeBeforeTest = cargueRepository.findAll().size();
    // set the field null
    cargue.setCargEntidad(null);

    // Create the Cargue, which fails.

    restCargueMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cargue)))
      .andExpect(status().isBadRequest());

    List<Cargue> cargueList = cargueRepository.findAll();
    assertThat(cargueList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkCargNombreArchivoIsRequired() throws Exception {
    int databaseSizeBeforeTest = cargueRepository.findAll().size();
    // set the field null
    cargue.setCargNombreArchivo(null);

    // Create the Cargue, which fails.

    restCargueMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cargue)))
      .andExpect(status().isBadRequest());

    List<Cargue> cargueList = cargueRepository.findAll();
    assertThat(cargueList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkCargEstadoIsRequired() throws Exception {
    int databaseSizeBeforeTest = cargueRepository.findAll().size();
    // set the field null
    cargue.setCargEstado(null);

    // Create the Cargue, which fails.

    restCargueMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cargue)))
      .andExpect(status().isBadRequest());

    List<Cargue> cargueList = cargueRepository.findAll();
    assertThat(cargueList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkCargTipoIsRequired() throws Exception {
    int databaseSizeBeforeTest = cargueRepository.findAll().size();
    // set the field null
    cargue.setCargTipo(null);

    // Create the Cargue, which fails.

    restCargueMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cargue)))
      .andExpect(status().isBadRequest());

    List<Cargue> cargueList = cargueRepository.findAll();
    assertThat(cargueList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkCargEsReprocesoIsRequired() throws Exception {
    int databaseSizeBeforeTest = cargueRepository.findAll().size();
    // set the field null
    cargue.setCargEsReproceso(null);

    // Create the Cargue, which fails.

    restCargueMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cargue)))
      .andExpect(status().isBadRequest());

    List<Cargue> cargueList = cargueRepository.findAll();
    assertThat(cargueList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkCargHashIsRequired() throws Exception {
    int databaseSizeBeforeTest = cargueRepository.findAll().size();
    // set the field null
    cargue.setCargHash(null);

    // Create the Cargue, which fails.

    restCargueMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cargue)))
      .andExpect(status().isBadRequest());

    List<Cargue> cargueList = cargueRepository.findAll();
    assertThat(cargueList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkUsuarioCreacionIsRequired() throws Exception {
    int databaseSizeBeforeTest = cargueRepository.findAll().size();
    // set the field null
    cargue.setUsuarioCreacion(null);

    // Create the Cargue, which fails.

    restCargueMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cargue)))
      .andExpect(status().isBadRequest());

    List<Cargue> cargueList = cargueRepository.findAll();
    assertThat(cargueList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkFechaCreacionIsRequired() throws Exception {
    int databaseSizeBeforeTest = cargueRepository.findAll().size();
    // set the field null
    cargue.setFechaCreacion(null);

    // Create the Cargue, which fails.

    restCargueMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cargue)))
      .andExpect(status().isBadRequest());

    List<Cargue> cargueList = cargueRepository.findAll();
    assertThat(cargueList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllCargues() throws Exception {
    // Initialize the database
    cargueRepository.saveAndFlush(cargue);

    // Get all the cargueList
    restCargueMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(cargue.getId().intValue())))
      .andExpect(jsonPath("$.[*].cargNroRegistros").value(hasItem(DEFAULT_CARG_NRO_REGISTROS)))
      .andExpect(jsonPath("$.[*].cargJson").value(hasItem(DEFAULT_CARG_JSON)))
      .andExpect(jsonPath("$.[*].cargEntidad").value(hasItem(DEFAULT_CARG_ENTIDAD)))
      .andExpect(jsonPath("$.[*].cargNombreArchivo").value(hasItem(DEFAULT_CARG_NOMBRE_ARCHIVO)))
      .andExpect(jsonPath("$.[*].cargEstado").value(hasItem(DEFAULT_CARG_ESTADO)))
      .andExpect(jsonPath("$.[*].cargTipo").value(hasItem(DEFAULT_CARG_TIPO)))
      .andExpect(jsonPath("$.[*].cargEsReproceso").value(hasItem(DEFAULT_CARG_ES_REPROCESO)))
      .andExpect(jsonPath("$.[*].cargHash").value(hasItem(DEFAULT_CARG_HASH)))
      .andExpect(jsonPath("$.[*].usuarioCreacion").value(hasItem(DEFAULT_USUARIO_CREACION)))
      .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(sameInstant(DEFAULT_FECHA_CREACION))))
      .andExpect(jsonPath("$.[*].usuarioModificacion").value(hasItem(DEFAULT_USUARIO_MODIFICACION)))
      .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(sameInstant(DEFAULT_FECHA_MODIFICACION))));
  }

  @Test
  @Transactional
  void getCargue() throws Exception {
    // Initialize the database
    cargueRepository.saveAndFlush(cargue);

    // Get the cargue
    restCargueMockMvc
      .perform(get(ENTITY_API_URL_ID, cargue.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(cargue.getId().intValue()))
      .andExpect(jsonPath("$.cargNroRegistros").value(DEFAULT_CARG_NRO_REGISTROS))
      .andExpect(jsonPath("$.cargJson").value(DEFAULT_CARG_JSON))
      .andExpect(jsonPath("$.cargEntidad").value(DEFAULT_CARG_ENTIDAD))
      .andExpect(jsonPath("$.cargNombreArchivo").value(DEFAULT_CARG_NOMBRE_ARCHIVO))
      .andExpect(jsonPath("$.cargEstado").value(DEFAULT_CARG_ESTADO))
      .andExpect(jsonPath("$.cargTipo").value(DEFAULT_CARG_TIPO))
      .andExpect(jsonPath("$.cargEsReproceso").value(DEFAULT_CARG_ES_REPROCESO))
      .andExpect(jsonPath("$.cargHash").value(DEFAULT_CARG_HASH))
      .andExpect(jsonPath("$.usuarioCreacion").value(DEFAULT_USUARIO_CREACION))
      .andExpect(jsonPath("$.fechaCreacion").value(sameInstant(DEFAULT_FECHA_CREACION)))
      .andExpect(jsonPath("$.usuarioModificacion").value(DEFAULT_USUARIO_MODIFICACION))
      .andExpect(jsonPath("$.fechaModificacion").value(sameInstant(DEFAULT_FECHA_MODIFICACION)));
  }

  @Test
  @Transactional
  void getNonExistingCargue() throws Exception {
    // Get the cargue
    restCargueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewCargue() throws Exception {
    // Initialize the database
    cargueRepository.saveAndFlush(cargue);

    int databaseSizeBeforeUpdate = cargueRepository.findAll().size();

    // Update the cargue
    Cargue updatedCargue = cargueRepository.findById(cargue.getId()).get();
    // Disconnect from session so that the updates on updatedCargue are not directly saved in db
    em.detach(updatedCargue);
    updatedCargue
      .cargNroRegistros(UPDATED_CARG_NRO_REGISTROS)
      .cargJson(UPDATED_CARG_JSON)
      .cargEntidad(UPDATED_CARG_ENTIDAD)
      .cargNombreArchivo(UPDATED_CARG_NOMBRE_ARCHIVO)
      .cargEstado(UPDATED_CARG_ESTADO)
      .cargTipo(UPDATED_CARG_TIPO)
      .cargEsReproceso(UPDATED_CARG_ES_REPROCESO)
      .cargHash(UPDATED_CARG_HASH)
      .usuarioCreacion(UPDATED_USUARIO_CREACION)
      .fechaCreacion(UPDATED_FECHA_CREACION)
      .usuarioModificacion(UPDATED_USUARIO_MODIFICACION)
      .fechaModificacion(UPDATED_FECHA_MODIFICACION);

    restCargueMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedCargue.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedCargue))
      )
      .andExpect(status().isOk());

    // Validate the Cargue in the database
    List<Cargue> cargueList = cargueRepository.findAll();
    assertThat(cargueList).hasSize(databaseSizeBeforeUpdate);
    Cargue testCargue = cargueList.get(cargueList.size() - 1);
    assertThat(testCargue.getCargNroRegistros()).isEqualTo(UPDATED_CARG_NRO_REGISTROS);
    assertThat(testCargue.getCargJson()).isEqualTo(UPDATED_CARG_JSON);
    assertThat(testCargue.getCargEntidad()).isEqualTo(UPDATED_CARG_ENTIDAD);
    assertThat(testCargue.getCargNombreArchivo()).isEqualTo(UPDATED_CARG_NOMBRE_ARCHIVO);
    assertThat(testCargue.getCargEstado()).isEqualTo(UPDATED_CARG_ESTADO);
    assertThat(testCargue.getCargTipo()).isEqualTo(UPDATED_CARG_TIPO);
    assertThat(testCargue.getCargEsReproceso()).isEqualTo(UPDATED_CARG_ES_REPROCESO);
    assertThat(testCargue.getCargHash()).isEqualTo(UPDATED_CARG_HASH);
    assertThat(testCargue.getUsuarioCreacion()).isEqualTo(UPDATED_USUARIO_CREACION);
    assertThat(testCargue.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
    assertThat(testCargue.getUsuarioModificacion()).isEqualTo(UPDATED_USUARIO_MODIFICACION);
    assertThat(testCargue.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
  }

  @Test
  @Transactional
  void putNonExistingCargue() throws Exception {
    int databaseSizeBeforeUpdate = cargueRepository.findAll().size();
    cargue.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restCargueMockMvc
      .perform(
        put(ENTITY_API_URL_ID, cargue.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(cargue))
      )
      .andExpect(status().isBadRequest());

    // Validate the Cargue in the database
    List<Cargue> cargueList = cargueRepository.findAll();
    assertThat(cargueList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchCargue() throws Exception {
    int databaseSizeBeforeUpdate = cargueRepository.findAll().size();
    cargue.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restCargueMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(cargue))
      )
      .andExpect(status().isBadRequest());

    // Validate the Cargue in the database
    List<Cargue> cargueList = cargueRepository.findAll();
    assertThat(cargueList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamCargue() throws Exception {
    int databaseSizeBeforeUpdate = cargueRepository.findAll().size();
    cargue.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restCargueMockMvc
      .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cargue)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the Cargue in the database
    List<Cargue> cargueList = cargueRepository.findAll();
    assertThat(cargueList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateCargueWithPatch() throws Exception {
    // Initialize the database
    cargueRepository.saveAndFlush(cargue);

    int databaseSizeBeforeUpdate = cargueRepository.findAll().size();

    // Update the cargue using partial update
    Cargue partialUpdatedCargue = new Cargue();
    partialUpdatedCargue.setId(cargue.getId());

    partialUpdatedCargue
      .cargNroRegistros(UPDATED_CARG_NRO_REGISTROS)
      .cargJson(UPDATED_CARG_JSON)
      .cargNombreArchivo(UPDATED_CARG_NOMBRE_ARCHIVO)
      .cargHash(UPDATED_CARG_HASH)
      .fechaCreacion(UPDATED_FECHA_CREACION)
      .fechaModificacion(UPDATED_FECHA_MODIFICACION);

    restCargueMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedCargue.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCargue))
      )
      .andExpect(status().isOk());

    // Validate the Cargue in the database
    List<Cargue> cargueList = cargueRepository.findAll();
    assertThat(cargueList).hasSize(databaseSizeBeforeUpdate);
    Cargue testCargue = cargueList.get(cargueList.size() - 1);
    assertThat(testCargue.getCargNroRegistros()).isEqualTo(UPDATED_CARG_NRO_REGISTROS);
    assertThat(testCargue.getCargJson()).isEqualTo(UPDATED_CARG_JSON);
    assertThat(testCargue.getCargEntidad()).isEqualTo(DEFAULT_CARG_ENTIDAD);
    assertThat(testCargue.getCargNombreArchivo()).isEqualTo(UPDATED_CARG_NOMBRE_ARCHIVO);
    assertThat(testCargue.getCargEstado()).isEqualTo(DEFAULT_CARG_ESTADO);
    assertThat(testCargue.getCargTipo()).isEqualTo(DEFAULT_CARG_TIPO);
    assertThat(testCargue.getCargEsReproceso()).isEqualTo(DEFAULT_CARG_ES_REPROCESO);
    assertThat(testCargue.getCargHash()).isEqualTo(UPDATED_CARG_HASH);
    assertThat(testCargue.getUsuarioCreacion()).isEqualTo(DEFAULT_USUARIO_CREACION);
    assertThat(testCargue.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
    assertThat(testCargue.getUsuarioModificacion()).isEqualTo(DEFAULT_USUARIO_MODIFICACION);
    assertThat(testCargue.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
  }

  @Test
  @Transactional
  void fullUpdateCargueWithPatch() throws Exception {
    // Initialize the database
    cargueRepository.saveAndFlush(cargue);

    int databaseSizeBeforeUpdate = cargueRepository.findAll().size();

    // Update the cargue using partial update
    Cargue partialUpdatedCargue = new Cargue();
    partialUpdatedCargue.setId(cargue.getId());

    partialUpdatedCargue
      .cargNroRegistros(UPDATED_CARG_NRO_REGISTROS)
      .cargJson(UPDATED_CARG_JSON)
      .cargEntidad(UPDATED_CARG_ENTIDAD)
      .cargNombreArchivo(UPDATED_CARG_NOMBRE_ARCHIVO)
      .cargEstado(UPDATED_CARG_ESTADO)
      .cargTipo(UPDATED_CARG_TIPO)
      .cargEsReproceso(UPDATED_CARG_ES_REPROCESO)
      .cargHash(UPDATED_CARG_HASH)
      .usuarioCreacion(UPDATED_USUARIO_CREACION)
      .fechaCreacion(UPDATED_FECHA_CREACION)
      .usuarioModificacion(UPDATED_USUARIO_MODIFICACION)
      .fechaModificacion(UPDATED_FECHA_MODIFICACION);

    restCargueMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedCargue.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCargue))
      )
      .andExpect(status().isOk());

    // Validate the Cargue in the database
    List<Cargue> cargueList = cargueRepository.findAll();
    assertThat(cargueList).hasSize(databaseSizeBeforeUpdate);
    Cargue testCargue = cargueList.get(cargueList.size() - 1);
    assertThat(testCargue.getCargNroRegistros()).isEqualTo(UPDATED_CARG_NRO_REGISTROS);
    assertThat(testCargue.getCargJson()).isEqualTo(UPDATED_CARG_JSON);
    assertThat(testCargue.getCargEntidad()).isEqualTo(UPDATED_CARG_ENTIDAD);
    assertThat(testCargue.getCargNombreArchivo()).isEqualTo(UPDATED_CARG_NOMBRE_ARCHIVO);
    assertThat(testCargue.getCargEstado()).isEqualTo(UPDATED_CARG_ESTADO);
    assertThat(testCargue.getCargTipo()).isEqualTo(UPDATED_CARG_TIPO);
    assertThat(testCargue.getCargEsReproceso()).isEqualTo(UPDATED_CARG_ES_REPROCESO);
    assertThat(testCargue.getCargHash()).isEqualTo(UPDATED_CARG_HASH);
    assertThat(testCargue.getUsuarioCreacion()).isEqualTo(UPDATED_USUARIO_CREACION);
    assertThat(testCargue.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
    assertThat(testCargue.getUsuarioModificacion()).isEqualTo(UPDATED_USUARIO_MODIFICACION);
    assertThat(testCargue.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
  }

  @Test
  @Transactional
  void patchNonExistingCargue() throws Exception {
    int databaseSizeBeforeUpdate = cargueRepository.findAll().size();
    cargue.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restCargueMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, cargue.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(cargue))
      )
      .andExpect(status().isBadRequest());

    // Validate the Cargue in the database
    List<Cargue> cargueList = cargueRepository.findAll();
    assertThat(cargueList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchCargue() throws Exception {
    int databaseSizeBeforeUpdate = cargueRepository.findAll().size();
    cargue.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restCargueMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(cargue))
      )
      .andExpect(status().isBadRequest());

    // Validate the Cargue in the database
    List<Cargue> cargueList = cargueRepository.findAll();
    assertThat(cargueList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamCargue() throws Exception {
    int databaseSizeBeforeUpdate = cargueRepository.findAll().size();
    cargue.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restCargueMockMvc
      .perform(
        patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cargue))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the Cargue in the database
    List<Cargue> cargueList = cargueRepository.findAll();
    assertThat(cargueList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteCargue() throws Exception {
    // Initialize the database
    cargueRepository.saveAndFlush(cargue);

    int databaseSizeBeforeDelete = cargueRepository.findAll().size();

    // Delete the cargue
    restCargueMockMvc
      .perform(delete(ENTITY_API_URL_ID, cargue.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<Cargue> cargueList = cargueRepository.findAll();
    assertThat(cargueList).hasSize(databaseSizeBeforeDelete - 1);
  }
}

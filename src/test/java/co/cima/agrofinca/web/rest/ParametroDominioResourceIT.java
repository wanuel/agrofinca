package co.cima.agrofinca.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.cima.agrofinca.IntegrationTest;
import co.cima.agrofinca.domain.ParametroDominio;
import co.cima.agrofinca.repository.ParametroDominioRepository;
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
 * Integration tests for the {@link ParametroDominioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParametroDominioResourceIT {

  private static final Integer DEFAULT_PADO_ID = 1;
  private static final Integer UPDATED_PADO_ID = 2;

  private static final String DEFAULT_PADO_DESCRIPCION = "AAAAAAAAAA";
  private static final String UPDATED_PADO_DESCRIPCION = "BBBBBBBBBB";

  private static final String ENTITY_API_URL = "/api/parametro-dominios";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private ParametroDominioRepository parametroDominioRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restParametroDominioMockMvc;

  private ParametroDominio parametroDominio;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static ParametroDominio createEntity(EntityManager em) {
    ParametroDominio parametroDominio = new ParametroDominio().padoId(DEFAULT_PADO_ID).padoDescripcion(DEFAULT_PADO_DESCRIPCION);
    return parametroDominio;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static ParametroDominio createUpdatedEntity(EntityManager em) {
    ParametroDominio parametroDominio = new ParametroDominio().padoId(UPDATED_PADO_ID).padoDescripcion(UPDATED_PADO_DESCRIPCION);
    return parametroDominio;
  }

  @BeforeEach
  public void initTest() {
    parametroDominio = createEntity(em);
  }

  @Test
  @Transactional
  void createParametroDominio() throws Exception {
    int databaseSizeBeforeCreate = parametroDominioRepository.findAll().size();
    // Create the ParametroDominio
    restParametroDominioMockMvc
      .perform(
        post(ENTITY_API_URL)
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(parametroDominio))
      )
      .andExpect(status().isCreated());

    // Validate the ParametroDominio in the database
    List<ParametroDominio> parametroDominioList = parametroDominioRepository.findAll();
    assertThat(parametroDominioList).hasSize(databaseSizeBeforeCreate + 1);
    ParametroDominio testParametroDominio = parametroDominioList.get(parametroDominioList.size() - 1);
    assertThat(testParametroDominio.getPadoId()).isEqualTo(DEFAULT_PADO_ID);
    assertThat(testParametroDominio.getPadoDescripcion()).isEqualTo(DEFAULT_PADO_DESCRIPCION);
  }

  @Test
  @Transactional
  void createParametroDominioWithExistingId() throws Exception {
    // Create the ParametroDominio with an existing ID
    parametroDominio.setId(1L);

    int databaseSizeBeforeCreate = parametroDominioRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restParametroDominioMockMvc
      .perform(
        post(ENTITY_API_URL)
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(parametroDominio))
      )
      .andExpect(status().isBadRequest());

    // Validate the ParametroDominio in the database
    List<ParametroDominio> parametroDominioList = parametroDominioRepository.findAll();
    assertThat(parametroDominioList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkPadoIdIsRequired() throws Exception {
    int databaseSizeBeforeTest = parametroDominioRepository.findAll().size();
    // set the field null
    parametroDominio.setPadoId(null);

    // Create the ParametroDominio, which fails.

    restParametroDominioMockMvc
      .perform(
        post(ENTITY_API_URL)
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(parametroDominio))
      )
      .andExpect(status().isBadRequest());

    List<ParametroDominio> parametroDominioList = parametroDominioRepository.findAll();
    assertThat(parametroDominioList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkPadoDescripcionIsRequired() throws Exception {
    int databaseSizeBeforeTest = parametroDominioRepository.findAll().size();
    // set the field null
    parametroDominio.setPadoDescripcion(null);

    // Create the ParametroDominio, which fails.

    restParametroDominioMockMvc
      .perform(
        post(ENTITY_API_URL)
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(parametroDominio))
      )
      .andExpect(status().isBadRequest());

    List<ParametroDominio> parametroDominioList = parametroDominioRepository.findAll();
    assertThat(parametroDominioList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllParametroDominios() throws Exception {
    // Initialize the database
    parametroDominioRepository.saveAndFlush(parametroDominio);

    // Get all the parametroDominioList
    restParametroDominioMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(parametroDominio.getId().intValue())))
      .andExpect(jsonPath("$.[*].padoId").value(hasItem(DEFAULT_PADO_ID)))
      .andExpect(jsonPath("$.[*].padoDescripcion").value(hasItem(DEFAULT_PADO_DESCRIPCION)));
  }

  @Test
  @Transactional
  void getParametroDominio() throws Exception {
    // Initialize the database
    parametroDominioRepository.saveAndFlush(parametroDominio);

    // Get the parametroDominio
    restParametroDominioMockMvc
      .perform(get(ENTITY_API_URL_ID, parametroDominio.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(parametroDominio.getId().intValue()))
      .andExpect(jsonPath("$.padoId").value(DEFAULT_PADO_ID))
      .andExpect(jsonPath("$.padoDescripcion").value(DEFAULT_PADO_DESCRIPCION));
  }

  @Test
  @Transactional
  void getNonExistingParametroDominio() throws Exception {
    // Get the parametroDominio
    restParametroDominioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewParametroDominio() throws Exception {
    // Initialize the database
    parametroDominioRepository.saveAndFlush(parametroDominio);

    int databaseSizeBeforeUpdate = parametroDominioRepository.findAll().size();

    // Update the parametroDominio
    ParametroDominio updatedParametroDominio = parametroDominioRepository.findById(parametroDominio.getId()).get();
    // Disconnect from session so that the updates on updatedParametroDominio are not directly saved in db
    em.detach(updatedParametroDominio);
    updatedParametroDominio.padoId(UPDATED_PADO_ID).padoDescripcion(UPDATED_PADO_DESCRIPCION);

    restParametroDominioMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedParametroDominio.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedParametroDominio))
      )
      .andExpect(status().isOk());

    // Validate the ParametroDominio in the database
    List<ParametroDominio> parametroDominioList = parametroDominioRepository.findAll();
    assertThat(parametroDominioList).hasSize(databaseSizeBeforeUpdate);
    ParametroDominio testParametroDominio = parametroDominioList.get(parametroDominioList.size() - 1);
    assertThat(testParametroDominio.getPadoId()).isEqualTo(UPDATED_PADO_ID);
    assertThat(testParametroDominio.getPadoDescripcion()).isEqualTo(UPDATED_PADO_DESCRIPCION);
  }

  @Test
  @Transactional
  void putNonExistingParametroDominio() throws Exception {
    int databaseSizeBeforeUpdate = parametroDominioRepository.findAll().size();
    parametroDominio.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restParametroDominioMockMvc
      .perform(
        put(ENTITY_API_URL_ID, parametroDominio.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(parametroDominio))
      )
      .andExpect(status().isBadRequest());

    // Validate the ParametroDominio in the database
    List<ParametroDominio> parametroDominioList = parametroDominioRepository.findAll();
    assertThat(parametroDominioList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchParametroDominio() throws Exception {
    int databaseSizeBeforeUpdate = parametroDominioRepository.findAll().size();
    parametroDominio.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restParametroDominioMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(parametroDominio))
      )
      .andExpect(status().isBadRequest());

    // Validate the ParametroDominio in the database
    List<ParametroDominio> parametroDominioList = parametroDominioRepository.findAll();
    assertThat(parametroDominioList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamParametroDominio() throws Exception {
    int databaseSizeBeforeUpdate = parametroDominioRepository.findAll().size();
    parametroDominio.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restParametroDominioMockMvc
      .perform(
        put(ENTITY_API_URL)
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(parametroDominio))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the ParametroDominio in the database
    List<ParametroDominio> parametroDominioList = parametroDominioRepository.findAll();
    assertThat(parametroDominioList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateParametroDominioWithPatch() throws Exception {
    // Initialize the database
    parametroDominioRepository.saveAndFlush(parametroDominio);

    int databaseSizeBeforeUpdate = parametroDominioRepository.findAll().size();

    // Update the parametroDominio using partial update
    ParametroDominio partialUpdatedParametroDominio = new ParametroDominio();
    partialUpdatedParametroDominio.setId(parametroDominio.getId());

    partialUpdatedParametroDominio.padoId(UPDATED_PADO_ID);

    restParametroDominioMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedParametroDominio.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParametroDominio))
      )
      .andExpect(status().isOk());

    // Validate the ParametroDominio in the database
    List<ParametroDominio> parametroDominioList = parametroDominioRepository.findAll();
    assertThat(parametroDominioList).hasSize(databaseSizeBeforeUpdate);
    ParametroDominio testParametroDominio = parametroDominioList.get(parametroDominioList.size() - 1);
    assertThat(testParametroDominio.getPadoId()).isEqualTo(UPDATED_PADO_ID);
    assertThat(testParametroDominio.getPadoDescripcion()).isEqualTo(DEFAULT_PADO_DESCRIPCION);
  }

  @Test
  @Transactional
  void fullUpdateParametroDominioWithPatch() throws Exception {
    // Initialize the database
    parametroDominioRepository.saveAndFlush(parametroDominio);

    int databaseSizeBeforeUpdate = parametroDominioRepository.findAll().size();

    // Update the parametroDominio using partial update
    ParametroDominio partialUpdatedParametroDominio = new ParametroDominio();
    partialUpdatedParametroDominio.setId(parametroDominio.getId());

    partialUpdatedParametroDominio.padoId(UPDATED_PADO_ID).padoDescripcion(UPDATED_PADO_DESCRIPCION);

    restParametroDominioMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedParametroDominio.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParametroDominio))
      )
      .andExpect(status().isOk());

    // Validate the ParametroDominio in the database
    List<ParametroDominio> parametroDominioList = parametroDominioRepository.findAll();
    assertThat(parametroDominioList).hasSize(databaseSizeBeforeUpdate);
    ParametroDominio testParametroDominio = parametroDominioList.get(parametroDominioList.size() - 1);
    assertThat(testParametroDominio.getPadoId()).isEqualTo(UPDATED_PADO_ID);
    assertThat(testParametroDominio.getPadoDescripcion()).isEqualTo(UPDATED_PADO_DESCRIPCION);
  }

  @Test
  @Transactional
  void patchNonExistingParametroDominio() throws Exception {
    int databaseSizeBeforeUpdate = parametroDominioRepository.findAll().size();
    parametroDominio.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restParametroDominioMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, parametroDominio.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(parametroDominio))
      )
      .andExpect(status().isBadRequest());

    // Validate the ParametroDominio in the database
    List<ParametroDominio> parametroDominioList = parametroDominioRepository.findAll();
    assertThat(parametroDominioList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchParametroDominio() throws Exception {
    int databaseSizeBeforeUpdate = parametroDominioRepository.findAll().size();
    parametroDominio.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restParametroDominioMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(parametroDominio))
      )
      .andExpect(status().isBadRequest());

    // Validate the ParametroDominio in the database
    List<ParametroDominio> parametroDominioList = parametroDominioRepository.findAll();
    assertThat(parametroDominioList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamParametroDominio() throws Exception {
    int databaseSizeBeforeUpdate = parametroDominioRepository.findAll().size();
    parametroDominio.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restParametroDominioMockMvc
      .perform(
        patch(ENTITY_API_URL)
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(parametroDominio))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the ParametroDominio in the database
    List<ParametroDominio> parametroDominioList = parametroDominioRepository.findAll();
    assertThat(parametroDominioList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteParametroDominio() throws Exception {
    // Initialize the database
    parametroDominioRepository.saveAndFlush(parametroDominio);

    int databaseSizeBeforeDelete = parametroDominioRepository.findAll().size();

    // Delete the parametroDominio
    restParametroDominioMockMvc
      .perform(delete(ENTITY_API_URL_ID, parametroDominio.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<ParametroDominio> parametroDominioList = parametroDominioRepository.findAll();
    assertThat(parametroDominioList).hasSize(databaseSizeBeforeDelete - 1);
  }
}

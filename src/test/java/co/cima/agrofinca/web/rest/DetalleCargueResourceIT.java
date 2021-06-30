package co.cima.agrofinca.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.cima.agrofinca.IntegrationTest;
import co.cima.agrofinca.domain.DetalleCargue;
import co.cima.agrofinca.repository.DetalleCargueRepository;
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
 * Integration tests for the {@link DetalleCargueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DetalleCargueResourceIT {

  private static final String DEFAULT_DECA_CUP = "AAAAAAAAAA";
  private static final String UPDATED_DECA_CUP = "BBBBBBBBBB";

  private static final String DEFAULT_DECA_ESTADO = "AAAAAAAAAA";
  private static final String UPDATED_DECA_ESTADO = "BBBBBBBBBB";

  private static final String DEFAULT_DECA_JSON = "AAAAAAAAAA";
  private static final String UPDATED_DECA_JSON = "BBBBBBBBBB";

  private static final String ENTITY_API_URL = "/api/detalle-cargues";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private DetalleCargueRepository detalleCargueRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restDetalleCargueMockMvc;

  private DetalleCargue detalleCargue;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static DetalleCargue createEntity(EntityManager em) {
    DetalleCargue detalleCargue = new DetalleCargue().decaCup(DEFAULT_DECA_CUP).decaEstado(DEFAULT_DECA_ESTADO).decaJSON(DEFAULT_DECA_JSON);
    return detalleCargue;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static DetalleCargue createUpdatedEntity(EntityManager em) {
    DetalleCargue detalleCargue = new DetalleCargue().decaCup(UPDATED_DECA_CUP).decaEstado(UPDATED_DECA_ESTADO).decaJSON(UPDATED_DECA_JSON);
    return detalleCargue;
  }

  @BeforeEach
  public void initTest() {
    detalleCargue = createEntity(em);
  }

  @Test
  @Transactional
  void createDetalleCargue() throws Exception {
    int databaseSizeBeforeCreate = detalleCargueRepository.findAll().size();
    // Create the DetalleCargue
    restDetalleCargueMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detalleCargue))
      )
      .andExpect(status().isCreated());

    // Validate the DetalleCargue in the database
    List<DetalleCargue> detalleCargueList = detalleCargueRepository.findAll();
    assertThat(detalleCargueList).hasSize(databaseSizeBeforeCreate + 1);
    DetalleCargue testDetalleCargue = detalleCargueList.get(detalleCargueList.size() - 1);
    assertThat(testDetalleCargue.getDecaCup()).isEqualTo(DEFAULT_DECA_CUP);
    assertThat(testDetalleCargue.getDecaEstado()).isEqualTo(DEFAULT_DECA_ESTADO);
    assertThat(testDetalleCargue.getDecaJSON()).isEqualTo(DEFAULT_DECA_JSON);
  }

  @Test
  @Transactional
  void createDetalleCargueWithExistingId() throws Exception {
    // Create the DetalleCargue with an existing ID
    detalleCargue.setId(1L);

    int databaseSizeBeforeCreate = detalleCargueRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restDetalleCargueMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detalleCargue))
      )
      .andExpect(status().isBadRequest());

    // Validate the DetalleCargue in the database
    List<DetalleCargue> detalleCargueList = detalleCargueRepository.findAll();
    assertThat(detalleCargueList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkDecaCupIsRequired() throws Exception {
    int databaseSizeBeforeTest = detalleCargueRepository.findAll().size();
    // set the field null
    detalleCargue.setDecaCup(null);

    // Create the DetalleCargue, which fails.

    restDetalleCargueMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detalleCargue))
      )
      .andExpect(status().isBadRequest());

    List<DetalleCargue> detalleCargueList = detalleCargueRepository.findAll();
    assertThat(detalleCargueList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkDecaEstadoIsRequired() throws Exception {
    int databaseSizeBeforeTest = detalleCargueRepository.findAll().size();
    // set the field null
    detalleCargue.setDecaEstado(null);

    // Create the DetalleCargue, which fails.

    restDetalleCargueMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detalleCargue))
      )
      .andExpect(status().isBadRequest());

    List<DetalleCargue> detalleCargueList = detalleCargueRepository.findAll();
    assertThat(detalleCargueList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkDecaJSONIsRequired() throws Exception {
    int databaseSizeBeforeTest = detalleCargueRepository.findAll().size();
    // set the field null
    detalleCargue.setDecaJSON(null);

    // Create the DetalleCargue, which fails.

    restDetalleCargueMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detalleCargue))
      )
      .andExpect(status().isBadRequest());

    List<DetalleCargue> detalleCargueList = detalleCargueRepository.findAll();
    assertThat(detalleCargueList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllDetalleCargues() throws Exception {
    // Initialize the database
    detalleCargueRepository.saveAndFlush(detalleCargue);

    // Get all the detalleCargueList
    restDetalleCargueMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(detalleCargue.getId().intValue())))
      .andExpect(jsonPath("$.[*].decaCup").value(hasItem(DEFAULT_DECA_CUP)))
      .andExpect(jsonPath("$.[*].decaEstado").value(hasItem(DEFAULT_DECA_ESTADO)))
      .andExpect(jsonPath("$.[*].decaJSON").value(hasItem(DEFAULT_DECA_JSON)));
  }

  @Test
  @Transactional
  void getDetalleCargue() throws Exception {
    // Initialize the database
    detalleCargueRepository.saveAndFlush(detalleCargue);

    // Get the detalleCargue
    restDetalleCargueMockMvc
      .perform(get(ENTITY_API_URL_ID, detalleCargue.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(detalleCargue.getId().intValue()))
      .andExpect(jsonPath("$.decaCup").value(DEFAULT_DECA_CUP))
      .andExpect(jsonPath("$.decaEstado").value(DEFAULT_DECA_ESTADO))
      .andExpect(jsonPath("$.decaJSON").value(DEFAULT_DECA_JSON));
  }

  @Test
  @Transactional
  void getNonExistingDetalleCargue() throws Exception {
    // Get the detalleCargue
    restDetalleCargueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewDetalleCargue() throws Exception {
    // Initialize the database
    detalleCargueRepository.saveAndFlush(detalleCargue);

    int databaseSizeBeforeUpdate = detalleCargueRepository.findAll().size();

    // Update the detalleCargue
    DetalleCargue updatedDetalleCargue = detalleCargueRepository.findById(detalleCargue.getId()).get();
    // Disconnect from session so that the updates on updatedDetalleCargue are not directly saved in db
    em.detach(updatedDetalleCargue);
    updatedDetalleCargue.decaCup(UPDATED_DECA_CUP).decaEstado(UPDATED_DECA_ESTADO).decaJSON(UPDATED_DECA_JSON);

    restDetalleCargueMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedDetalleCargue.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedDetalleCargue))
      )
      .andExpect(status().isOk());

    // Validate the DetalleCargue in the database
    List<DetalleCargue> detalleCargueList = detalleCargueRepository.findAll();
    assertThat(detalleCargueList).hasSize(databaseSizeBeforeUpdate);
    DetalleCargue testDetalleCargue = detalleCargueList.get(detalleCargueList.size() - 1);
    assertThat(testDetalleCargue.getDecaCup()).isEqualTo(UPDATED_DECA_CUP);
    assertThat(testDetalleCargue.getDecaEstado()).isEqualTo(UPDATED_DECA_ESTADO);
    assertThat(testDetalleCargue.getDecaJSON()).isEqualTo(UPDATED_DECA_JSON);
  }

  @Test
  @Transactional
  void putNonExistingDetalleCargue() throws Exception {
    int databaseSizeBeforeUpdate = detalleCargueRepository.findAll().size();
    detalleCargue.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restDetalleCargueMockMvc
      .perform(
        put(ENTITY_API_URL_ID, detalleCargue.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(detalleCargue))
      )
      .andExpect(status().isBadRequest());

    // Validate the DetalleCargue in the database
    List<DetalleCargue> detalleCargueList = detalleCargueRepository.findAll();
    assertThat(detalleCargueList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchDetalleCargue() throws Exception {
    int databaseSizeBeforeUpdate = detalleCargueRepository.findAll().size();
    detalleCargue.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restDetalleCargueMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(detalleCargue))
      )
      .andExpect(status().isBadRequest());

    // Validate the DetalleCargue in the database
    List<DetalleCargue> detalleCargueList = detalleCargueRepository.findAll();
    assertThat(detalleCargueList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamDetalleCargue() throws Exception {
    int databaseSizeBeforeUpdate = detalleCargueRepository.findAll().size();
    detalleCargue.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restDetalleCargueMockMvc
      .perform(
        put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detalleCargue))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the DetalleCargue in the database
    List<DetalleCargue> detalleCargueList = detalleCargueRepository.findAll();
    assertThat(detalleCargueList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateDetalleCargueWithPatch() throws Exception {
    // Initialize the database
    detalleCargueRepository.saveAndFlush(detalleCargue);

    int databaseSizeBeforeUpdate = detalleCargueRepository.findAll().size();

    // Update the detalleCargue using partial update
    DetalleCargue partialUpdatedDetalleCargue = new DetalleCargue();
    partialUpdatedDetalleCargue.setId(detalleCargue.getId());

    partialUpdatedDetalleCargue.decaCup(UPDATED_DECA_CUP);

    restDetalleCargueMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedDetalleCargue.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetalleCargue))
      )
      .andExpect(status().isOk());

    // Validate the DetalleCargue in the database
    List<DetalleCargue> detalleCargueList = detalleCargueRepository.findAll();
    assertThat(detalleCargueList).hasSize(databaseSizeBeforeUpdate);
    DetalleCargue testDetalleCargue = detalleCargueList.get(detalleCargueList.size() - 1);
    assertThat(testDetalleCargue.getDecaCup()).isEqualTo(UPDATED_DECA_CUP);
    assertThat(testDetalleCargue.getDecaEstado()).isEqualTo(DEFAULT_DECA_ESTADO);
    assertThat(testDetalleCargue.getDecaJSON()).isEqualTo(DEFAULT_DECA_JSON);
  }

  @Test
  @Transactional
  void fullUpdateDetalleCargueWithPatch() throws Exception {
    // Initialize the database
    detalleCargueRepository.saveAndFlush(detalleCargue);

    int databaseSizeBeforeUpdate = detalleCargueRepository.findAll().size();

    // Update the detalleCargue using partial update
    DetalleCargue partialUpdatedDetalleCargue = new DetalleCargue();
    partialUpdatedDetalleCargue.setId(detalleCargue.getId());

    partialUpdatedDetalleCargue.decaCup(UPDATED_DECA_CUP).decaEstado(UPDATED_DECA_ESTADO).decaJSON(UPDATED_DECA_JSON);

    restDetalleCargueMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedDetalleCargue.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetalleCargue))
      )
      .andExpect(status().isOk());

    // Validate the DetalleCargue in the database
    List<DetalleCargue> detalleCargueList = detalleCargueRepository.findAll();
    assertThat(detalleCargueList).hasSize(databaseSizeBeforeUpdate);
    DetalleCargue testDetalleCargue = detalleCargueList.get(detalleCargueList.size() - 1);
    assertThat(testDetalleCargue.getDecaCup()).isEqualTo(UPDATED_DECA_CUP);
    assertThat(testDetalleCargue.getDecaEstado()).isEqualTo(UPDATED_DECA_ESTADO);
    assertThat(testDetalleCargue.getDecaJSON()).isEqualTo(UPDATED_DECA_JSON);
  }

  @Test
  @Transactional
  void patchNonExistingDetalleCargue() throws Exception {
    int databaseSizeBeforeUpdate = detalleCargueRepository.findAll().size();
    detalleCargue.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restDetalleCargueMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, detalleCargue.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(detalleCargue))
      )
      .andExpect(status().isBadRequest());

    // Validate the DetalleCargue in the database
    List<DetalleCargue> detalleCargueList = detalleCargueRepository.findAll();
    assertThat(detalleCargueList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchDetalleCargue() throws Exception {
    int databaseSizeBeforeUpdate = detalleCargueRepository.findAll().size();
    detalleCargue.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restDetalleCargueMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(detalleCargue))
      )
      .andExpect(status().isBadRequest());

    // Validate the DetalleCargue in the database
    List<DetalleCargue> detalleCargueList = detalleCargueRepository.findAll();
    assertThat(detalleCargueList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamDetalleCargue() throws Exception {
    int databaseSizeBeforeUpdate = detalleCargueRepository.findAll().size();
    detalleCargue.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restDetalleCargueMockMvc
      .perform(
        patch(ENTITY_API_URL)
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(detalleCargue))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the DetalleCargue in the database
    List<DetalleCargue> detalleCargueList = detalleCargueRepository.findAll();
    assertThat(detalleCargueList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteDetalleCargue() throws Exception {
    // Initialize the database
    detalleCargueRepository.saveAndFlush(detalleCargue);

    int databaseSizeBeforeDelete = detalleCargueRepository.findAll().size();

    // Delete the detalleCargue
    restDetalleCargueMockMvc
      .perform(delete(ENTITY_API_URL_ID, detalleCargue.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<DetalleCargue> detalleCargueList = detalleCargueRepository.findAll();
    assertThat(detalleCargueList).hasSize(databaseSizeBeforeDelete - 1);
  }
}

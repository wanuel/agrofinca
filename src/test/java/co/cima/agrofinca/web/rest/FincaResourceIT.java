package co.cima.agrofinca.web.rest;

import static co.cima.agrofinca.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.cima.agrofinca.IntegrationTest;
import co.cima.agrofinca.domain.Finca;
import co.cima.agrofinca.repository.FincaRepository;
import java.math.BigDecimal;
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
 * Integration tests for the {@link FincaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FincaResourceIT {

  private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
  private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

  private static final BigDecimal DEFAULT_AREA = new BigDecimal(1);
  private static final BigDecimal UPDATED_AREA = new BigDecimal(2);

  private static final String ENTITY_API_URL = "/api/fincas";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private FincaRepository fincaRepository;

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
    Finca finca = new Finca().nombre(DEFAULT_NOMBRE).area(DEFAULT_AREA);
    return finca;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Finca createUpdatedEntity(EntityManager em) {
    Finca finca = new Finca().nombre(UPDATED_NOMBRE).area(UPDATED_AREA);
    return finca;
  }

  @BeforeEach
  public void initTest() {
    finca = createEntity(em);
  }

  @Test
  @Transactional
  void createFinca() throws Exception {
    int databaseSizeBeforeCreate = fincaRepository.findAll().size();
    // Create the Finca
    restFincaMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(finca)))
      .andExpect(status().isCreated());

    // Validate the Finca in the database
    List<Finca> fincaList = fincaRepository.findAll();
    assertThat(fincaList).hasSize(databaseSizeBeforeCreate + 1);
    Finca testFinca = fincaList.get(fincaList.size() - 1);
    assertThat(testFinca.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    assertThat(testFinca.getArea()).isEqualByComparingTo(DEFAULT_AREA);
  }

  @Test
  @Transactional
  void createFincaWithExistingId() throws Exception {
    // Create the Finca with an existing ID
    finca.setId(1L);

    int databaseSizeBeforeCreate = fincaRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restFincaMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(finca)))
      .andExpect(status().isBadRequest());

    // Validate the Finca in the database
    List<Finca> fincaList = fincaRepository.findAll();
    assertThat(fincaList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkNombreIsRequired() throws Exception {
    int databaseSizeBeforeTest = fincaRepository.findAll().size();
    // set the field null
    finca.setNombre(null);

    // Create the Finca, which fails.

    restFincaMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(finca)))
      .andExpect(status().isBadRequest());

    List<Finca> fincaList = fincaRepository.findAll();
    assertThat(fincaList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllFincas() throws Exception {
    // Initialize the database
    fincaRepository.saveAndFlush(finca);

    // Get all the fincaList
    restFincaMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(finca.getId().intValue())))
      .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
      .andExpect(jsonPath("$.[*].area").value(hasItem(sameNumber(DEFAULT_AREA))));
  }

  @Test
  @Transactional
  void getFinca() throws Exception {
    // Initialize the database
    fincaRepository.saveAndFlush(finca);

    // Get the finca
    restFincaMockMvc
      .perform(get(ENTITY_API_URL_ID, finca.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(finca.getId().intValue()))
      .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
      .andExpect(jsonPath("$.area").value(sameNumber(DEFAULT_AREA)));
  }

  @Test
  @Transactional
  void getNonExistingFinca() throws Exception {
    // Get the finca
    restFincaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewFinca() throws Exception {
    // Initialize the database
    fincaRepository.saveAndFlush(finca);

    int databaseSizeBeforeUpdate = fincaRepository.findAll().size();

    // Update the finca
    Finca updatedFinca = fincaRepository.findById(finca.getId()).get();
    // Disconnect from session so that the updates on updatedFinca are not directly saved in db
    em.detach(updatedFinca);
    updatedFinca.nombre(UPDATED_NOMBRE).area(UPDATED_AREA);

    restFincaMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedFinca.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedFinca))
      )
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
  void putNonExistingFinca() throws Exception {
    int databaseSizeBeforeUpdate = fincaRepository.findAll().size();
    finca.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restFincaMockMvc
      .perform(
        put(ENTITY_API_URL_ID, finca.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(finca))
      )
      .andExpect(status().isBadRequest());

    // Validate the Finca in the database
    List<Finca> fincaList = fincaRepository.findAll();
    assertThat(fincaList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchFinca() throws Exception {
    int databaseSizeBeforeUpdate = fincaRepository.findAll().size();
    finca.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restFincaMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(finca))
      )
      .andExpect(status().isBadRequest());

    // Validate the Finca in the database
    List<Finca> fincaList = fincaRepository.findAll();
    assertThat(fincaList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamFinca() throws Exception {
    int databaseSizeBeforeUpdate = fincaRepository.findAll().size();
    finca.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restFincaMockMvc
      .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(finca)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the Finca in the database
    List<Finca> fincaList = fincaRepository.findAll();
    assertThat(fincaList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateFincaWithPatch() throws Exception {
    // Initialize the database
    fincaRepository.saveAndFlush(finca);

    int databaseSizeBeforeUpdate = fincaRepository.findAll().size();

    // Update the finca using partial update
    Finca partialUpdatedFinca = new Finca();
    partialUpdatedFinca.setId(finca.getId());

    restFincaMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedFinca.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFinca))
      )
      .andExpect(status().isOk());

    // Validate the Finca in the database
    List<Finca> fincaList = fincaRepository.findAll();
    assertThat(fincaList).hasSize(databaseSizeBeforeUpdate);
    Finca testFinca = fincaList.get(fincaList.size() - 1);
    assertThat(testFinca.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    assertThat(testFinca.getArea()).isEqualByComparingTo(DEFAULT_AREA);
  }

  @Test
  @Transactional
  void fullUpdateFincaWithPatch() throws Exception {
    // Initialize the database
    fincaRepository.saveAndFlush(finca);

    int databaseSizeBeforeUpdate = fincaRepository.findAll().size();

    // Update the finca using partial update
    Finca partialUpdatedFinca = new Finca();
    partialUpdatedFinca.setId(finca.getId());

    partialUpdatedFinca.nombre(UPDATED_NOMBRE).area(UPDATED_AREA);

    restFincaMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedFinca.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFinca))
      )
      .andExpect(status().isOk());

    // Validate the Finca in the database
    List<Finca> fincaList = fincaRepository.findAll();
    assertThat(fincaList).hasSize(databaseSizeBeforeUpdate);
    Finca testFinca = fincaList.get(fincaList.size() - 1);
    assertThat(testFinca.getNombre()).isEqualTo(UPDATED_NOMBRE);
    assertThat(testFinca.getArea()).isEqualByComparingTo(UPDATED_AREA);
  }

  @Test
  @Transactional
  void patchNonExistingFinca() throws Exception {
    int databaseSizeBeforeUpdate = fincaRepository.findAll().size();
    finca.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restFincaMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, finca.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(finca))
      )
      .andExpect(status().isBadRequest());

    // Validate the Finca in the database
    List<Finca> fincaList = fincaRepository.findAll();
    assertThat(fincaList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchFinca() throws Exception {
    int databaseSizeBeforeUpdate = fincaRepository.findAll().size();
    finca.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restFincaMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(finca))
      )
      .andExpect(status().isBadRequest());

    // Validate the Finca in the database
    List<Finca> fincaList = fincaRepository.findAll();
    assertThat(fincaList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamFinca() throws Exception {
    int databaseSizeBeforeUpdate = fincaRepository.findAll().size();
    finca.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restFincaMockMvc
      .perform(
        patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(finca))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the Finca in the database
    List<Finca> fincaList = fincaRepository.findAll();
    assertThat(fincaList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteFinca() throws Exception {
    // Initialize the database
    fincaRepository.saveAndFlush(finca);

    int databaseSizeBeforeDelete = fincaRepository.findAll().size();

    // Delete the finca
    restFincaMockMvc
      .perform(delete(ENTITY_API_URL_ID, finca.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<Finca> fincaList = fincaRepository.findAll();
    assertThat(fincaList).hasSize(databaseSizeBeforeDelete - 1);
  }
}

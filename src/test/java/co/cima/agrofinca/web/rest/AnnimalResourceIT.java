package co.cima.agrofinca.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.cima.agrofinca.IntegrationTest;
import co.cima.agrofinca.domain.Annimal;
import co.cima.agrofinca.repository.AnnimalRepository;
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
 * Integration tests for the {@link AnnimalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnnimalResourceIT {

  private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
  private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

  private static final String ENTITY_API_URL = "/api/annimals";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private AnnimalRepository annimalRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restAnnimalMockMvc;

  private Annimal annimal;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Annimal createEntity(EntityManager em) {
    Annimal annimal = new Annimal().nombre(DEFAULT_NOMBRE);
    return annimal;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Annimal createUpdatedEntity(EntityManager em) {
    Annimal annimal = new Annimal().nombre(UPDATED_NOMBRE);
    return annimal;
  }

  @BeforeEach
  public void initTest() {
    annimal = createEntity(em);
  }

  @Test
  @Transactional
  void createAnnimal() throws Exception {
    int databaseSizeBeforeCreate = annimalRepository.findAll().size();
    // Create the Annimal
    restAnnimalMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(annimal))
      )
      .andExpect(status().isCreated());

    // Validate the Annimal in the database
    List<Annimal> annimalList = annimalRepository.findAll();
    assertThat(annimalList).hasSize(databaseSizeBeforeCreate + 1);
    Annimal testAnnimal = annimalList.get(annimalList.size() - 1);
    assertThat(testAnnimal.getNombre()).isEqualTo(DEFAULT_NOMBRE);
  }

  @Test
  @Transactional
  void createAnnimalWithExistingId() throws Exception {
    // Create the Annimal with an existing ID
    annimal.setId(1L);

    int databaseSizeBeforeCreate = annimalRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restAnnimalMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(annimal))
      )
      .andExpect(status().isBadRequest());

    // Validate the Annimal in the database
    List<Annimal> annimalList = annimalRepository.findAll();
    assertThat(annimalList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkNombreIsRequired() throws Exception {
    int databaseSizeBeforeTest = annimalRepository.findAll().size();
    // set the field null
    annimal.setNombre(null);

    // Create the Annimal, which fails.

    restAnnimalMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(annimal))
      )
      .andExpect(status().isBadRequest());

    List<Annimal> annimalList = annimalRepository.findAll();
    assertThat(annimalList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllAnnimals() throws Exception {
    // Initialize the database
    annimalRepository.saveAndFlush(annimal);

    // Get all the annimalList
    restAnnimalMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(annimal.getId().intValue())))
      .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
  }

  @Test
  @Transactional
  void getAnnimal() throws Exception {
    // Initialize the database
    annimalRepository.saveAndFlush(annimal);

    // Get the annimal
    restAnnimalMockMvc
      .perform(get(ENTITY_API_URL_ID, annimal.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(annimal.getId().intValue()))
      .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
  }

  @Test
  @Transactional
  void getNonExistingAnnimal() throws Exception {
    // Get the annimal
    restAnnimalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewAnnimal() throws Exception {
    // Initialize the database
    annimalRepository.saveAndFlush(annimal);

    int databaseSizeBeforeUpdate = annimalRepository.findAll().size();

    // Update the annimal
    Annimal updatedAnnimal = annimalRepository.findById(annimal.getId()).get();
    // Disconnect from session so that the updates on updatedAnnimal are not directly saved in db
    em.detach(updatedAnnimal);
    updatedAnnimal.nombre(UPDATED_NOMBRE);

    restAnnimalMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedAnnimal.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedAnnimal))
      )
      .andExpect(status().isOk());

    // Validate the Annimal in the database
    List<Annimal> annimalList = annimalRepository.findAll();
    assertThat(annimalList).hasSize(databaseSizeBeforeUpdate);
    Annimal testAnnimal = annimalList.get(annimalList.size() - 1);
    assertThat(testAnnimal.getNombre()).isEqualTo(UPDATED_NOMBRE);
  }

  @Test
  @Transactional
  void putNonExistingAnnimal() throws Exception {
    int databaseSizeBeforeUpdate = annimalRepository.findAll().size();
    annimal.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restAnnimalMockMvc
      .perform(
        put(ENTITY_API_URL_ID, annimal.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(annimal))
      )
      .andExpect(status().isBadRequest());

    // Validate the Annimal in the database
    List<Annimal> annimalList = annimalRepository.findAll();
    assertThat(annimalList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchAnnimal() throws Exception {
    int databaseSizeBeforeUpdate = annimalRepository.findAll().size();
    annimal.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnnimalMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(annimal))
      )
      .andExpect(status().isBadRequest());

    // Validate the Annimal in the database
    List<Annimal> annimalList = annimalRepository.findAll();
    assertThat(annimalList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamAnnimal() throws Exception {
    int databaseSizeBeforeUpdate = annimalRepository.findAll().size();
    annimal.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnnimalMockMvc
      .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(annimal)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the Annimal in the database
    List<Annimal> annimalList = annimalRepository.findAll();
    assertThat(annimalList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateAnnimalWithPatch() throws Exception {
    // Initialize the database
    annimalRepository.saveAndFlush(annimal);

    int databaseSizeBeforeUpdate = annimalRepository.findAll().size();

    // Update the annimal using partial update
    Annimal partialUpdatedAnnimal = new Annimal();
    partialUpdatedAnnimal.setId(annimal.getId());

    partialUpdatedAnnimal.nombre(UPDATED_NOMBRE);

    restAnnimalMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedAnnimal.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnnimal))
      )
      .andExpect(status().isOk());

    // Validate the Annimal in the database
    List<Annimal> annimalList = annimalRepository.findAll();
    assertThat(annimalList).hasSize(databaseSizeBeforeUpdate);
    Annimal testAnnimal = annimalList.get(annimalList.size() - 1);
    assertThat(testAnnimal.getNombre()).isEqualTo(UPDATED_NOMBRE);
  }

  @Test
  @Transactional
  void fullUpdateAnnimalWithPatch() throws Exception {
    // Initialize the database
    annimalRepository.saveAndFlush(annimal);

    int databaseSizeBeforeUpdate = annimalRepository.findAll().size();

    // Update the annimal using partial update
    Annimal partialUpdatedAnnimal = new Annimal();
    partialUpdatedAnnimal.setId(annimal.getId());

    partialUpdatedAnnimal.nombre(UPDATED_NOMBRE);

    restAnnimalMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedAnnimal.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnnimal))
      )
      .andExpect(status().isOk());

    // Validate the Annimal in the database
    List<Annimal> annimalList = annimalRepository.findAll();
    assertThat(annimalList).hasSize(databaseSizeBeforeUpdate);
    Annimal testAnnimal = annimalList.get(annimalList.size() - 1);
    assertThat(testAnnimal.getNombre()).isEqualTo(UPDATED_NOMBRE);
  }

  @Test
  @Transactional
  void patchNonExistingAnnimal() throws Exception {
    int databaseSizeBeforeUpdate = annimalRepository.findAll().size();
    annimal.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restAnnimalMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, annimal.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(annimal))
      )
      .andExpect(status().isBadRequest());

    // Validate the Annimal in the database
    List<Annimal> annimalList = annimalRepository.findAll();
    assertThat(annimalList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchAnnimal() throws Exception {
    int databaseSizeBeforeUpdate = annimalRepository.findAll().size();
    annimal.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnnimalMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(annimal))
      )
      .andExpect(status().isBadRequest());

    // Validate the Annimal in the database
    List<Annimal> annimalList = annimalRepository.findAll();
    assertThat(annimalList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamAnnimal() throws Exception {
    int databaseSizeBeforeUpdate = annimalRepository.findAll().size();
    annimal.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnnimalMockMvc
      .perform(
        patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(annimal))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the Annimal in the database
    List<Annimal> annimalList = annimalRepository.findAll();
    assertThat(annimalList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteAnnimal() throws Exception {
    // Initialize the database
    annimalRepository.saveAndFlush(annimal);

    int databaseSizeBeforeDelete = annimalRepository.findAll().size();

    // Delete the annimal
    restAnnimalMockMvc
      .perform(delete(ENTITY_API_URL_ID, annimal.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<Annimal> annimalList = annimalRepository.findAll();
    assertThat(annimalList).hasSize(databaseSizeBeforeDelete - 1);
  }
}

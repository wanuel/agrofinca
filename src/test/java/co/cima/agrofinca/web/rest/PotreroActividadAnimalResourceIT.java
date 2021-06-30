package co.cima.agrofinca.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.cima.agrofinca.IntegrationTest;
import co.cima.agrofinca.domain.PotreroActividadAnimal;
import co.cima.agrofinca.repository.PotreroActividadAnimalRepository;
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
 * Integration tests for the {@link PotreroActividadAnimalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PotreroActividadAnimalResourceIT {

  private static final String ENTITY_API_URL = "/api/potrero-actividad-animals";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private PotreroActividadAnimalRepository potreroActividadAnimalRepository;

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
  void createPotreroActividadAnimal() throws Exception {
    int databaseSizeBeforeCreate = potreroActividadAnimalRepository.findAll().size();
    // Create the PotreroActividadAnimal
    restPotreroActividadAnimalMockMvc
      .perform(
        post(ENTITY_API_URL)
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(potreroActividadAnimal))
      )
      .andExpect(status().isCreated());

    // Validate the PotreroActividadAnimal in the database
    List<PotreroActividadAnimal> potreroActividadAnimalList = potreroActividadAnimalRepository.findAll();
    assertThat(potreroActividadAnimalList).hasSize(databaseSizeBeforeCreate + 1);
    PotreroActividadAnimal testPotreroActividadAnimal = potreroActividadAnimalList.get(potreroActividadAnimalList.size() - 1);
  }

  @Test
  @Transactional
  void createPotreroActividadAnimalWithExistingId() throws Exception {
    // Create the PotreroActividadAnimal with an existing ID
    potreroActividadAnimal.setId(1L);

    int databaseSizeBeforeCreate = potreroActividadAnimalRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restPotreroActividadAnimalMockMvc
      .perform(
        post(ENTITY_API_URL)
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(potreroActividadAnimal))
      )
      .andExpect(status().isBadRequest());

    // Validate the PotreroActividadAnimal in the database
    List<PotreroActividadAnimal> potreroActividadAnimalList = potreroActividadAnimalRepository.findAll();
    assertThat(potreroActividadAnimalList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void getAllPotreroActividadAnimals() throws Exception {
    // Initialize the database
    potreroActividadAnimalRepository.saveAndFlush(potreroActividadAnimal);

    // Get all the potreroActividadAnimalList
    restPotreroActividadAnimalMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(potreroActividadAnimal.getId().intValue())));
  }

  @Test
  @Transactional
  void getPotreroActividadAnimal() throws Exception {
    // Initialize the database
    potreroActividadAnimalRepository.saveAndFlush(potreroActividadAnimal);

    // Get the potreroActividadAnimal
    restPotreroActividadAnimalMockMvc
      .perform(get(ENTITY_API_URL_ID, potreroActividadAnimal.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(potreroActividadAnimal.getId().intValue()));
  }

  @Test
  @Transactional
  void getNonExistingPotreroActividadAnimal() throws Exception {
    // Get the potreroActividadAnimal
    restPotreroActividadAnimalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewPotreroActividadAnimal() throws Exception {
    // Initialize the database
    potreroActividadAnimalRepository.saveAndFlush(potreroActividadAnimal);

    int databaseSizeBeforeUpdate = potreroActividadAnimalRepository.findAll().size();

    // Update the potreroActividadAnimal
    PotreroActividadAnimal updatedPotreroActividadAnimal = potreroActividadAnimalRepository.findById(potreroActividadAnimal.getId()).get();
    // Disconnect from session so that the updates on updatedPotreroActividadAnimal are not directly saved in db
    em.detach(updatedPotreroActividadAnimal);

    restPotreroActividadAnimalMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedPotreroActividadAnimal.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedPotreroActividadAnimal))
      )
      .andExpect(status().isOk());

    // Validate the PotreroActividadAnimal in the database
    List<PotreroActividadAnimal> potreroActividadAnimalList = potreroActividadAnimalRepository.findAll();
    assertThat(potreroActividadAnimalList).hasSize(databaseSizeBeforeUpdate);
    PotreroActividadAnimal testPotreroActividadAnimal = potreroActividadAnimalList.get(potreroActividadAnimalList.size() - 1);
  }

  @Test
  @Transactional
  void putNonExistingPotreroActividadAnimal() throws Exception {
    int databaseSizeBeforeUpdate = potreroActividadAnimalRepository.findAll().size();
    potreroActividadAnimal.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restPotreroActividadAnimalMockMvc
      .perform(
        put(ENTITY_API_URL_ID, potreroActividadAnimal.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(potreroActividadAnimal))
      )
      .andExpect(status().isBadRequest());

    // Validate the PotreroActividadAnimal in the database
    List<PotreroActividadAnimal> potreroActividadAnimalList = potreroActividadAnimalRepository.findAll();
    assertThat(potreroActividadAnimalList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchPotreroActividadAnimal() throws Exception {
    int databaseSizeBeforeUpdate = potreroActividadAnimalRepository.findAll().size();
    potreroActividadAnimal.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restPotreroActividadAnimalMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(potreroActividadAnimal))
      )
      .andExpect(status().isBadRequest());

    // Validate the PotreroActividadAnimal in the database
    List<PotreroActividadAnimal> potreroActividadAnimalList = potreroActividadAnimalRepository.findAll();
    assertThat(potreroActividadAnimalList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamPotreroActividadAnimal() throws Exception {
    int databaseSizeBeforeUpdate = potreroActividadAnimalRepository.findAll().size();
    potreroActividadAnimal.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restPotreroActividadAnimalMockMvc
      .perform(
        put(ENTITY_API_URL)
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(potreroActividadAnimal))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the PotreroActividadAnimal in the database
    List<PotreroActividadAnimal> potreroActividadAnimalList = potreroActividadAnimalRepository.findAll();
    assertThat(potreroActividadAnimalList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdatePotreroActividadAnimalWithPatch() throws Exception {
    // Initialize the database
    potreroActividadAnimalRepository.saveAndFlush(potreroActividadAnimal);

    int databaseSizeBeforeUpdate = potreroActividadAnimalRepository.findAll().size();

    // Update the potreroActividadAnimal using partial update
    PotreroActividadAnimal partialUpdatedPotreroActividadAnimal = new PotreroActividadAnimal();
    partialUpdatedPotreroActividadAnimal.setId(potreroActividadAnimal.getId());

    restPotreroActividadAnimalMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedPotreroActividadAnimal.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPotreroActividadAnimal))
      )
      .andExpect(status().isOk());

    // Validate the PotreroActividadAnimal in the database
    List<PotreroActividadAnimal> potreroActividadAnimalList = potreroActividadAnimalRepository.findAll();
    assertThat(potreroActividadAnimalList).hasSize(databaseSizeBeforeUpdate);
    PotreroActividadAnimal testPotreroActividadAnimal = potreroActividadAnimalList.get(potreroActividadAnimalList.size() - 1);
  }

  @Test
  @Transactional
  void fullUpdatePotreroActividadAnimalWithPatch() throws Exception {
    // Initialize the database
    potreroActividadAnimalRepository.saveAndFlush(potreroActividadAnimal);

    int databaseSizeBeforeUpdate = potreroActividadAnimalRepository.findAll().size();

    // Update the potreroActividadAnimal using partial update
    PotreroActividadAnimal partialUpdatedPotreroActividadAnimal = new PotreroActividadAnimal();
    partialUpdatedPotreroActividadAnimal.setId(potreroActividadAnimal.getId());

    restPotreroActividadAnimalMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedPotreroActividadAnimal.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPotreroActividadAnimal))
      )
      .andExpect(status().isOk());

    // Validate the PotreroActividadAnimal in the database
    List<PotreroActividadAnimal> potreroActividadAnimalList = potreroActividadAnimalRepository.findAll();
    assertThat(potreroActividadAnimalList).hasSize(databaseSizeBeforeUpdate);
    PotreroActividadAnimal testPotreroActividadAnimal = potreroActividadAnimalList.get(potreroActividadAnimalList.size() - 1);
  }

  @Test
  @Transactional
  void patchNonExistingPotreroActividadAnimal() throws Exception {
    int databaseSizeBeforeUpdate = potreroActividadAnimalRepository.findAll().size();
    potreroActividadAnimal.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restPotreroActividadAnimalMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, potreroActividadAnimal.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(potreroActividadAnimal))
      )
      .andExpect(status().isBadRequest());

    // Validate the PotreroActividadAnimal in the database
    List<PotreroActividadAnimal> potreroActividadAnimalList = potreroActividadAnimalRepository.findAll();
    assertThat(potreroActividadAnimalList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchPotreroActividadAnimal() throws Exception {
    int databaseSizeBeforeUpdate = potreroActividadAnimalRepository.findAll().size();
    potreroActividadAnimal.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restPotreroActividadAnimalMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(potreroActividadAnimal))
      )
      .andExpect(status().isBadRequest());

    // Validate the PotreroActividadAnimal in the database
    List<PotreroActividadAnimal> potreroActividadAnimalList = potreroActividadAnimalRepository.findAll();
    assertThat(potreroActividadAnimalList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamPotreroActividadAnimal() throws Exception {
    int databaseSizeBeforeUpdate = potreroActividadAnimalRepository.findAll().size();
    potreroActividadAnimal.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restPotreroActividadAnimalMockMvc
      .perform(
        patch(ENTITY_API_URL)
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(potreroActividadAnimal))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the PotreroActividadAnimal in the database
    List<PotreroActividadAnimal> potreroActividadAnimalList = potreroActividadAnimalRepository.findAll();
    assertThat(potreroActividadAnimalList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deletePotreroActividadAnimal() throws Exception {
    // Initialize the database
    potreroActividadAnimalRepository.saveAndFlush(potreroActividadAnimal);

    int databaseSizeBeforeDelete = potreroActividadAnimalRepository.findAll().size();

    // Delete the potreroActividadAnimal
    restPotreroActividadAnimalMockMvc
      .perform(delete(ENTITY_API_URL_ID, potreroActividadAnimal.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<PotreroActividadAnimal> potreroActividadAnimalList = potreroActividadAnimalRepository.findAll();
    assertThat(potreroActividadAnimalList).hasSize(databaseSizeBeforeDelete - 1);
  }
}

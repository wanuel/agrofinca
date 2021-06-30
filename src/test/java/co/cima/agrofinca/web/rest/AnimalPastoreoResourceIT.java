package co.cima.agrofinca.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.cima.agrofinca.IntegrationTest;
import co.cima.agrofinca.domain.AnimalPastoreo;
import co.cima.agrofinca.repository.AnimalPastoreoRepository;
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
 * Integration tests for the {@link AnimalPastoreoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnimalPastoreoResourceIT {

  private static final String ENTITY_API_URL = "/api/animal-pastoreos";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private AnimalPastoreoRepository animalPastoreoRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restAnimalPastoreoMockMvc;

  private AnimalPastoreo animalPastoreo;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static AnimalPastoreo createEntity(EntityManager em) {
    AnimalPastoreo animalPastoreo = new AnimalPastoreo();
    return animalPastoreo;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static AnimalPastoreo createUpdatedEntity(EntityManager em) {
    AnimalPastoreo animalPastoreo = new AnimalPastoreo();
    return animalPastoreo;
  }

  @BeforeEach
  public void initTest() {
    animalPastoreo = createEntity(em);
  }

  @Test
  @Transactional
  void createAnimalPastoreo() throws Exception {
    int databaseSizeBeforeCreate = animalPastoreoRepository.findAll().size();
    // Create the AnimalPastoreo
    restAnimalPastoreoMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalPastoreo))
      )
      .andExpect(status().isCreated());

    // Validate the AnimalPastoreo in the database
    List<AnimalPastoreo> animalPastoreoList = animalPastoreoRepository.findAll();
    assertThat(animalPastoreoList).hasSize(databaseSizeBeforeCreate + 1);
    AnimalPastoreo testAnimalPastoreo = animalPastoreoList.get(animalPastoreoList.size() - 1);
  }

  @Test
  @Transactional
  void createAnimalPastoreoWithExistingId() throws Exception {
    // Create the AnimalPastoreo with an existing ID
    animalPastoreo.setId(1L);

    int databaseSizeBeforeCreate = animalPastoreoRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restAnimalPastoreoMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalPastoreo))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalPastoreo in the database
    List<AnimalPastoreo> animalPastoreoList = animalPastoreoRepository.findAll();
    assertThat(animalPastoreoList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void getAllAnimalPastoreos() throws Exception {
    // Initialize the database
    animalPastoreoRepository.saveAndFlush(animalPastoreo);

    // Get all the animalPastoreoList
    restAnimalPastoreoMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(animalPastoreo.getId().intValue())));
  }

  @Test
  @Transactional
  void getAnimalPastoreo() throws Exception {
    // Initialize the database
    animalPastoreoRepository.saveAndFlush(animalPastoreo);

    // Get the animalPastoreo
    restAnimalPastoreoMockMvc
      .perform(get(ENTITY_API_URL_ID, animalPastoreo.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(animalPastoreo.getId().intValue()));
  }

  @Test
  @Transactional
  void getNonExistingAnimalPastoreo() throws Exception {
    // Get the animalPastoreo
    restAnimalPastoreoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewAnimalPastoreo() throws Exception {
    // Initialize the database
    animalPastoreoRepository.saveAndFlush(animalPastoreo);

    int databaseSizeBeforeUpdate = animalPastoreoRepository.findAll().size();

    // Update the animalPastoreo
    AnimalPastoreo updatedAnimalPastoreo = animalPastoreoRepository.findById(animalPastoreo.getId()).get();
    // Disconnect from session so that the updates on updatedAnimalPastoreo are not directly saved in db
    em.detach(updatedAnimalPastoreo);

    restAnimalPastoreoMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedAnimalPastoreo.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedAnimalPastoreo))
      )
      .andExpect(status().isOk());

    // Validate the AnimalPastoreo in the database
    List<AnimalPastoreo> animalPastoreoList = animalPastoreoRepository.findAll();
    assertThat(animalPastoreoList).hasSize(databaseSizeBeforeUpdate);
    AnimalPastoreo testAnimalPastoreo = animalPastoreoList.get(animalPastoreoList.size() - 1);
  }

  @Test
  @Transactional
  void putNonExistingAnimalPastoreo() throws Exception {
    int databaseSizeBeforeUpdate = animalPastoreoRepository.findAll().size();
    animalPastoreo.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restAnimalPastoreoMockMvc
      .perform(
        put(ENTITY_API_URL_ID, animalPastoreo.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(animalPastoreo))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalPastoreo in the database
    List<AnimalPastoreo> animalPastoreoList = animalPastoreoRepository.findAll();
    assertThat(animalPastoreoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchAnimalPastoreo() throws Exception {
    int databaseSizeBeforeUpdate = animalPastoreoRepository.findAll().size();
    animalPastoreo.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalPastoreoMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(animalPastoreo))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalPastoreo in the database
    List<AnimalPastoreo> animalPastoreoList = animalPastoreoRepository.findAll();
    assertThat(animalPastoreoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamAnimalPastoreo() throws Exception {
    int databaseSizeBeforeUpdate = animalPastoreoRepository.findAll().size();
    animalPastoreo.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalPastoreoMockMvc
      .perform(
        put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalPastoreo))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the AnimalPastoreo in the database
    List<AnimalPastoreo> animalPastoreoList = animalPastoreoRepository.findAll();
    assertThat(animalPastoreoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateAnimalPastoreoWithPatch() throws Exception {
    // Initialize the database
    animalPastoreoRepository.saveAndFlush(animalPastoreo);

    int databaseSizeBeforeUpdate = animalPastoreoRepository.findAll().size();

    // Update the animalPastoreo using partial update
    AnimalPastoreo partialUpdatedAnimalPastoreo = new AnimalPastoreo();
    partialUpdatedAnimalPastoreo.setId(animalPastoreo.getId());

    restAnimalPastoreoMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedAnimalPastoreo.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnimalPastoreo))
      )
      .andExpect(status().isOk());

    // Validate the AnimalPastoreo in the database
    List<AnimalPastoreo> animalPastoreoList = animalPastoreoRepository.findAll();
    assertThat(animalPastoreoList).hasSize(databaseSizeBeforeUpdate);
    AnimalPastoreo testAnimalPastoreo = animalPastoreoList.get(animalPastoreoList.size() - 1);
  }

  @Test
  @Transactional
  void fullUpdateAnimalPastoreoWithPatch() throws Exception {
    // Initialize the database
    animalPastoreoRepository.saveAndFlush(animalPastoreo);

    int databaseSizeBeforeUpdate = animalPastoreoRepository.findAll().size();

    // Update the animalPastoreo using partial update
    AnimalPastoreo partialUpdatedAnimalPastoreo = new AnimalPastoreo();
    partialUpdatedAnimalPastoreo.setId(animalPastoreo.getId());

    restAnimalPastoreoMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedAnimalPastoreo.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnimalPastoreo))
      )
      .andExpect(status().isOk());

    // Validate the AnimalPastoreo in the database
    List<AnimalPastoreo> animalPastoreoList = animalPastoreoRepository.findAll();
    assertThat(animalPastoreoList).hasSize(databaseSizeBeforeUpdate);
    AnimalPastoreo testAnimalPastoreo = animalPastoreoList.get(animalPastoreoList.size() - 1);
  }

  @Test
  @Transactional
  void patchNonExistingAnimalPastoreo() throws Exception {
    int databaseSizeBeforeUpdate = animalPastoreoRepository.findAll().size();
    animalPastoreo.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restAnimalPastoreoMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, animalPastoreo.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animalPastoreo))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalPastoreo in the database
    List<AnimalPastoreo> animalPastoreoList = animalPastoreoRepository.findAll();
    assertThat(animalPastoreoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchAnimalPastoreo() throws Exception {
    int databaseSizeBeforeUpdate = animalPastoreoRepository.findAll().size();
    animalPastoreo.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalPastoreoMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animalPastoreo))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalPastoreo in the database
    List<AnimalPastoreo> animalPastoreoList = animalPastoreoRepository.findAll();
    assertThat(animalPastoreoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamAnimalPastoreo() throws Exception {
    int databaseSizeBeforeUpdate = animalPastoreoRepository.findAll().size();
    animalPastoreo.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalPastoreoMockMvc
      .perform(
        patch(ENTITY_API_URL)
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animalPastoreo))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the AnimalPastoreo in the database
    List<AnimalPastoreo> animalPastoreoList = animalPastoreoRepository.findAll();
    assertThat(animalPastoreoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteAnimalPastoreo() throws Exception {
    // Initialize the database
    animalPastoreoRepository.saveAndFlush(animalPastoreo);

    int databaseSizeBeforeDelete = animalPastoreoRepository.findAll().size();

    // Delete the animalPastoreo
    restAnimalPastoreoMockMvc
      .perform(delete(ENTITY_API_URL_ID, animalPastoreo.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<AnimalPastoreo> animalPastoreoList = animalPastoreoRepository.findAll();
    assertThat(animalPastoreoList).hasSize(databaseSizeBeforeDelete - 1);
  }
}

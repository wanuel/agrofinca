package co.cima.agrofinca.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.cima.agrofinca.IntegrationTest;
import co.cima.agrofinca.domain.AnimalEvento;
import co.cima.agrofinca.repository.AnimalEventoRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link AnimalEventoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnimalEventoResourceIT {

  private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

  private static final String ENTITY_API_URL = "/api/animal-eventos";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private AnimalEventoRepository animalEventoRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restAnimalEventoMockMvc;

  private AnimalEvento animalEvento;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static AnimalEvento createEntity(EntityManager em) {
    AnimalEvento animalEvento = new AnimalEvento().fecha(DEFAULT_FECHA);
    return animalEvento;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static AnimalEvento createUpdatedEntity(EntityManager em) {
    AnimalEvento animalEvento = new AnimalEvento().fecha(UPDATED_FECHA);
    return animalEvento;
  }

  @BeforeEach
  public void initTest() {
    animalEvento = createEntity(em);
  }

  @Test
  @Transactional
  void createAnimalEvento() throws Exception {
    int databaseSizeBeforeCreate = animalEventoRepository.findAll().size();
    // Create the AnimalEvento
    restAnimalEventoMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalEvento))
      )
      .andExpect(status().isCreated());

    // Validate the AnimalEvento in the database
    List<AnimalEvento> animalEventoList = animalEventoRepository.findAll();
    assertThat(animalEventoList).hasSize(databaseSizeBeforeCreate + 1);
    AnimalEvento testAnimalEvento = animalEventoList.get(animalEventoList.size() - 1);
    assertThat(testAnimalEvento.getFecha()).isEqualTo(DEFAULT_FECHA);
  }

  @Test
  @Transactional
  void createAnimalEventoWithExistingId() throws Exception {
    // Create the AnimalEvento with an existing ID
    animalEvento.setId(1L);

    int databaseSizeBeforeCreate = animalEventoRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restAnimalEventoMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalEvento))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalEvento in the database
    List<AnimalEvento> animalEventoList = animalEventoRepository.findAll();
    assertThat(animalEventoList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void getAllAnimalEventos() throws Exception {
    // Initialize the database
    animalEventoRepository.saveAndFlush(animalEvento);

    // Get all the animalEventoList
    restAnimalEventoMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(animalEvento.getId().intValue())))
      .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
  }

  @Test
  @Transactional
  void getAnimalEvento() throws Exception {
    // Initialize the database
    animalEventoRepository.saveAndFlush(animalEvento);

    // Get the animalEvento
    restAnimalEventoMockMvc
      .perform(get(ENTITY_API_URL_ID, animalEvento.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(animalEvento.getId().intValue()))
      .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
  }

  @Test
  @Transactional
  void getNonExistingAnimalEvento() throws Exception {
    // Get the animalEvento
    restAnimalEventoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewAnimalEvento() throws Exception {
    // Initialize the database
    animalEventoRepository.saveAndFlush(animalEvento);

    int databaseSizeBeforeUpdate = animalEventoRepository.findAll().size();

    // Update the animalEvento
    AnimalEvento updatedAnimalEvento = animalEventoRepository.findById(animalEvento.getId()).get();
    // Disconnect from session so that the updates on updatedAnimalEvento are not directly saved in db
    em.detach(updatedAnimalEvento);
    updatedAnimalEvento.fecha(UPDATED_FECHA);

    restAnimalEventoMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedAnimalEvento.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedAnimalEvento))
      )
      .andExpect(status().isOk());

    // Validate the AnimalEvento in the database
    List<AnimalEvento> animalEventoList = animalEventoRepository.findAll();
    assertThat(animalEventoList).hasSize(databaseSizeBeforeUpdate);
    AnimalEvento testAnimalEvento = animalEventoList.get(animalEventoList.size() - 1);
    assertThat(testAnimalEvento.getFecha()).isEqualTo(UPDATED_FECHA);
  }

  @Test
  @Transactional
  void putNonExistingAnimalEvento() throws Exception {
    int databaseSizeBeforeUpdate = animalEventoRepository.findAll().size();
    animalEvento.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restAnimalEventoMockMvc
      .perform(
        put(ENTITY_API_URL_ID, animalEvento.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(animalEvento))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalEvento in the database
    List<AnimalEvento> animalEventoList = animalEventoRepository.findAll();
    assertThat(animalEventoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchAnimalEvento() throws Exception {
    int databaseSizeBeforeUpdate = animalEventoRepository.findAll().size();
    animalEvento.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalEventoMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(animalEvento))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalEvento in the database
    List<AnimalEvento> animalEventoList = animalEventoRepository.findAll();
    assertThat(animalEventoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamAnimalEvento() throws Exception {
    int databaseSizeBeforeUpdate = animalEventoRepository.findAll().size();
    animalEvento.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalEventoMockMvc
      .perform(
        put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalEvento))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the AnimalEvento in the database
    List<AnimalEvento> animalEventoList = animalEventoRepository.findAll();
    assertThat(animalEventoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateAnimalEventoWithPatch() throws Exception {
    // Initialize the database
    animalEventoRepository.saveAndFlush(animalEvento);

    int databaseSizeBeforeUpdate = animalEventoRepository.findAll().size();

    // Update the animalEvento using partial update
    AnimalEvento partialUpdatedAnimalEvento = new AnimalEvento();
    partialUpdatedAnimalEvento.setId(animalEvento.getId());

    partialUpdatedAnimalEvento.fecha(UPDATED_FECHA);

    restAnimalEventoMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedAnimalEvento.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnimalEvento))
      )
      .andExpect(status().isOk());

    // Validate the AnimalEvento in the database
    List<AnimalEvento> animalEventoList = animalEventoRepository.findAll();
    assertThat(animalEventoList).hasSize(databaseSizeBeforeUpdate);
    AnimalEvento testAnimalEvento = animalEventoList.get(animalEventoList.size() - 1);
    assertThat(testAnimalEvento.getFecha()).isEqualTo(UPDATED_FECHA);
  }

  @Test
  @Transactional
  void fullUpdateAnimalEventoWithPatch() throws Exception {
    // Initialize the database
    animalEventoRepository.saveAndFlush(animalEvento);

    int databaseSizeBeforeUpdate = animalEventoRepository.findAll().size();

    // Update the animalEvento using partial update
    AnimalEvento partialUpdatedAnimalEvento = new AnimalEvento();
    partialUpdatedAnimalEvento.setId(animalEvento.getId());

    partialUpdatedAnimalEvento.fecha(UPDATED_FECHA);

    restAnimalEventoMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedAnimalEvento.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnimalEvento))
      )
      .andExpect(status().isOk());

    // Validate the AnimalEvento in the database
    List<AnimalEvento> animalEventoList = animalEventoRepository.findAll();
    assertThat(animalEventoList).hasSize(databaseSizeBeforeUpdate);
    AnimalEvento testAnimalEvento = animalEventoList.get(animalEventoList.size() - 1);
    assertThat(testAnimalEvento.getFecha()).isEqualTo(UPDATED_FECHA);
  }

  @Test
  @Transactional
  void patchNonExistingAnimalEvento() throws Exception {
    int databaseSizeBeforeUpdate = animalEventoRepository.findAll().size();
    animalEvento.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restAnimalEventoMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, animalEvento.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animalEvento))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalEvento in the database
    List<AnimalEvento> animalEventoList = animalEventoRepository.findAll();
    assertThat(animalEventoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchAnimalEvento() throws Exception {
    int databaseSizeBeforeUpdate = animalEventoRepository.findAll().size();
    animalEvento.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalEventoMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animalEvento))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalEvento in the database
    List<AnimalEvento> animalEventoList = animalEventoRepository.findAll();
    assertThat(animalEventoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamAnimalEvento() throws Exception {
    int databaseSizeBeforeUpdate = animalEventoRepository.findAll().size();
    animalEvento.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalEventoMockMvc
      .perform(
        patch(ENTITY_API_URL)
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animalEvento))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the AnimalEvento in the database
    List<AnimalEvento> animalEventoList = animalEventoRepository.findAll();
    assertThat(animalEventoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteAnimalEvento() throws Exception {
    // Initialize the database
    animalEventoRepository.saveAndFlush(animalEvento);

    int databaseSizeBeforeDelete = animalEventoRepository.findAll().size();

    // Delete the animalEvento
    restAnimalEventoMockMvc
      .perform(delete(ENTITY_API_URL_ID, animalEvento.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<AnimalEvento> animalEventoList = animalEventoRepository.findAll();
    assertThat(animalEventoList).hasSize(databaseSizeBeforeDelete - 1);
  }
}

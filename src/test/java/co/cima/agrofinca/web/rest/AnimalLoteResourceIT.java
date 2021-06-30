package co.cima.agrofinca.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.cima.agrofinca.IntegrationTest;
import co.cima.agrofinca.domain.AnimalLote;
import co.cima.agrofinca.repository.AnimalLoteRepository;
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
 * Integration tests for the {@link AnimalLoteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnimalLoteResourceIT {

  private static final LocalDate DEFAULT_FECHA_ENTRADA = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_FECHA_ENTRADA = LocalDate.now(ZoneId.systemDefault());

  private static final LocalDate DEFAULT_FECHA_SALIDA = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_FECHA_SALIDA = LocalDate.now(ZoneId.systemDefault());

  private static final String ENTITY_API_URL = "/api/animal-lotes";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private AnimalLoteRepository animalLoteRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restAnimalLoteMockMvc;

  private AnimalLote animalLote;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static AnimalLote createEntity(EntityManager em) {
    AnimalLote animalLote = new AnimalLote().fechaEntrada(DEFAULT_FECHA_ENTRADA).fechaSalida(DEFAULT_FECHA_SALIDA);
    return animalLote;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static AnimalLote createUpdatedEntity(EntityManager em) {
    AnimalLote animalLote = new AnimalLote().fechaEntrada(UPDATED_FECHA_ENTRADA).fechaSalida(UPDATED_FECHA_SALIDA);
    return animalLote;
  }

  @BeforeEach
  public void initTest() {
    animalLote = createEntity(em);
  }

  @Test
  @Transactional
  void createAnimalLote() throws Exception {
    int databaseSizeBeforeCreate = animalLoteRepository.findAll().size();
    // Create the AnimalLote
    restAnimalLoteMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalLote))
      )
      .andExpect(status().isCreated());

    // Validate the AnimalLote in the database
    List<AnimalLote> animalLoteList = animalLoteRepository.findAll();
    assertThat(animalLoteList).hasSize(databaseSizeBeforeCreate + 1);
    AnimalLote testAnimalLote = animalLoteList.get(animalLoteList.size() - 1);
    assertThat(testAnimalLote.getFechaEntrada()).isEqualTo(DEFAULT_FECHA_ENTRADA);
    assertThat(testAnimalLote.getFechaSalida()).isEqualTo(DEFAULT_FECHA_SALIDA);
  }

  @Test
  @Transactional
  void createAnimalLoteWithExistingId() throws Exception {
    // Create the AnimalLote with an existing ID
    animalLote.setId(1L);

    int databaseSizeBeforeCreate = animalLoteRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restAnimalLoteMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalLote))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalLote in the database
    List<AnimalLote> animalLoteList = animalLoteRepository.findAll();
    assertThat(animalLoteList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkFechaEntradaIsRequired() throws Exception {
    int databaseSizeBeforeTest = animalLoteRepository.findAll().size();
    // set the field null
    animalLote.setFechaEntrada(null);

    // Create the AnimalLote, which fails.

    restAnimalLoteMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalLote))
      )
      .andExpect(status().isBadRequest());

    List<AnimalLote> animalLoteList = animalLoteRepository.findAll();
    assertThat(animalLoteList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllAnimalLotes() throws Exception {
    // Initialize the database
    animalLoteRepository.saveAndFlush(animalLote);

    // Get all the animalLoteList
    restAnimalLoteMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(animalLote.getId().intValue())))
      .andExpect(jsonPath("$.[*].fechaEntrada").value(hasItem(DEFAULT_FECHA_ENTRADA.toString())))
      .andExpect(jsonPath("$.[*].fechaSalida").value(hasItem(DEFAULT_FECHA_SALIDA.toString())));
  }

  @Test
  @Transactional
  void getAnimalLote() throws Exception {
    // Initialize the database
    animalLoteRepository.saveAndFlush(animalLote);

    // Get the animalLote
    restAnimalLoteMockMvc
      .perform(get(ENTITY_API_URL_ID, animalLote.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(animalLote.getId().intValue()))
      .andExpect(jsonPath("$.fechaEntrada").value(DEFAULT_FECHA_ENTRADA.toString()))
      .andExpect(jsonPath("$.fechaSalida").value(DEFAULT_FECHA_SALIDA.toString()));
  }

  @Test
  @Transactional
  void getNonExistingAnimalLote() throws Exception {
    // Get the animalLote
    restAnimalLoteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewAnimalLote() throws Exception {
    // Initialize the database
    animalLoteRepository.saveAndFlush(animalLote);

    int databaseSizeBeforeUpdate = animalLoteRepository.findAll().size();

    // Update the animalLote
    AnimalLote updatedAnimalLote = animalLoteRepository.findById(animalLote.getId()).get();
    // Disconnect from session so that the updates on updatedAnimalLote are not directly saved in db
    em.detach(updatedAnimalLote);
    updatedAnimalLote.fechaEntrada(UPDATED_FECHA_ENTRADA).fechaSalida(UPDATED_FECHA_SALIDA);

    restAnimalLoteMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedAnimalLote.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedAnimalLote))
      )
      .andExpect(status().isOk());

    // Validate the AnimalLote in the database
    List<AnimalLote> animalLoteList = animalLoteRepository.findAll();
    assertThat(animalLoteList).hasSize(databaseSizeBeforeUpdate);
    AnimalLote testAnimalLote = animalLoteList.get(animalLoteList.size() - 1);
    assertThat(testAnimalLote.getFechaEntrada()).isEqualTo(UPDATED_FECHA_ENTRADA);
    assertThat(testAnimalLote.getFechaSalida()).isEqualTo(UPDATED_FECHA_SALIDA);
  }

  @Test
  @Transactional
  void putNonExistingAnimalLote() throws Exception {
    int databaseSizeBeforeUpdate = animalLoteRepository.findAll().size();
    animalLote.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restAnimalLoteMockMvc
      .perform(
        put(ENTITY_API_URL_ID, animalLote.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(animalLote))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalLote in the database
    List<AnimalLote> animalLoteList = animalLoteRepository.findAll();
    assertThat(animalLoteList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchAnimalLote() throws Exception {
    int databaseSizeBeforeUpdate = animalLoteRepository.findAll().size();
    animalLote.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalLoteMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(animalLote))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalLote in the database
    List<AnimalLote> animalLoteList = animalLoteRepository.findAll();
    assertThat(animalLoteList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamAnimalLote() throws Exception {
    int databaseSizeBeforeUpdate = animalLoteRepository.findAll().size();
    animalLote.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalLoteMockMvc
      .perform(
        put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalLote))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the AnimalLote in the database
    List<AnimalLote> animalLoteList = animalLoteRepository.findAll();
    assertThat(animalLoteList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateAnimalLoteWithPatch() throws Exception {
    // Initialize the database
    animalLoteRepository.saveAndFlush(animalLote);

    int databaseSizeBeforeUpdate = animalLoteRepository.findAll().size();

    // Update the animalLote using partial update
    AnimalLote partialUpdatedAnimalLote = new AnimalLote();
    partialUpdatedAnimalLote.setId(animalLote.getId());

    restAnimalLoteMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedAnimalLote.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnimalLote))
      )
      .andExpect(status().isOk());

    // Validate the AnimalLote in the database
    List<AnimalLote> animalLoteList = animalLoteRepository.findAll();
    assertThat(animalLoteList).hasSize(databaseSizeBeforeUpdate);
    AnimalLote testAnimalLote = animalLoteList.get(animalLoteList.size() - 1);
    assertThat(testAnimalLote.getFechaEntrada()).isEqualTo(DEFAULT_FECHA_ENTRADA);
    assertThat(testAnimalLote.getFechaSalida()).isEqualTo(DEFAULT_FECHA_SALIDA);
  }

  @Test
  @Transactional
  void fullUpdateAnimalLoteWithPatch() throws Exception {
    // Initialize the database
    animalLoteRepository.saveAndFlush(animalLote);

    int databaseSizeBeforeUpdate = animalLoteRepository.findAll().size();

    // Update the animalLote using partial update
    AnimalLote partialUpdatedAnimalLote = new AnimalLote();
    partialUpdatedAnimalLote.setId(animalLote.getId());

    partialUpdatedAnimalLote.fechaEntrada(UPDATED_FECHA_ENTRADA).fechaSalida(UPDATED_FECHA_SALIDA);

    restAnimalLoteMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedAnimalLote.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnimalLote))
      )
      .andExpect(status().isOk());

    // Validate the AnimalLote in the database
    List<AnimalLote> animalLoteList = animalLoteRepository.findAll();
    assertThat(animalLoteList).hasSize(databaseSizeBeforeUpdate);
    AnimalLote testAnimalLote = animalLoteList.get(animalLoteList.size() - 1);
    assertThat(testAnimalLote.getFechaEntrada()).isEqualTo(UPDATED_FECHA_ENTRADA);
    assertThat(testAnimalLote.getFechaSalida()).isEqualTo(UPDATED_FECHA_SALIDA);
  }

  @Test
  @Transactional
  void patchNonExistingAnimalLote() throws Exception {
    int databaseSizeBeforeUpdate = animalLoteRepository.findAll().size();
    animalLote.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restAnimalLoteMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, animalLote.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animalLote))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalLote in the database
    List<AnimalLote> animalLoteList = animalLoteRepository.findAll();
    assertThat(animalLoteList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchAnimalLote() throws Exception {
    int databaseSizeBeforeUpdate = animalLoteRepository.findAll().size();
    animalLote.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalLoteMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animalLote))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalLote in the database
    List<AnimalLote> animalLoteList = animalLoteRepository.findAll();
    assertThat(animalLoteList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamAnimalLote() throws Exception {
    int databaseSizeBeforeUpdate = animalLoteRepository.findAll().size();
    animalLote.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalLoteMockMvc
      .perform(
        patch(ENTITY_API_URL)
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animalLote))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the AnimalLote in the database
    List<AnimalLote> animalLoteList = animalLoteRepository.findAll();
    assertThat(animalLoteList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteAnimalLote() throws Exception {
    // Initialize the database
    animalLoteRepository.saveAndFlush(animalLote);

    int databaseSizeBeforeDelete = animalLoteRepository.findAll().size();

    // Delete the animalLote
    restAnimalLoteMockMvc
      .perform(delete(ENTITY_API_URL_ID, animalLote.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<AnimalLote> animalLoteList = animalLoteRepository.findAll();
    assertThat(animalLoteList).hasSize(databaseSizeBeforeDelete - 1);
  }
}

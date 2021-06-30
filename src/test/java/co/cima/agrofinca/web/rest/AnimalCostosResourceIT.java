package co.cima.agrofinca.web.rest;

import static co.cima.agrofinca.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.cima.agrofinca.IntegrationTest;
import co.cima.agrofinca.domain.AnimalCostos;
import co.cima.agrofinca.repository.AnimalCostosRepository;
import java.math.BigDecimal;
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
 * Integration tests for the {@link AnimalCostosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnimalCostosResourceIT {

  private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

  private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
  private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

  private static final String ENTITY_API_URL = "/api/animal-costos";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private AnimalCostosRepository animalCostosRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restAnimalCostosMockMvc;

  private AnimalCostos animalCostos;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static AnimalCostos createEntity(EntityManager em) {
    AnimalCostos animalCostos = new AnimalCostos().fecha(DEFAULT_FECHA).valor(DEFAULT_VALOR);
    return animalCostos;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static AnimalCostos createUpdatedEntity(EntityManager em) {
    AnimalCostos animalCostos = new AnimalCostos().fecha(UPDATED_FECHA).valor(UPDATED_VALOR);
    return animalCostos;
  }

  @BeforeEach
  public void initTest() {
    animalCostos = createEntity(em);
  }

  @Test
  @Transactional
  void createAnimalCostos() throws Exception {
    int databaseSizeBeforeCreate = animalCostosRepository.findAll().size();
    // Create the AnimalCostos
    restAnimalCostosMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalCostos))
      )
      .andExpect(status().isCreated());

    // Validate the AnimalCostos in the database
    List<AnimalCostos> animalCostosList = animalCostosRepository.findAll();
    assertThat(animalCostosList).hasSize(databaseSizeBeforeCreate + 1);
    AnimalCostos testAnimalCostos = animalCostosList.get(animalCostosList.size() - 1);
    assertThat(testAnimalCostos.getFecha()).isEqualTo(DEFAULT_FECHA);
    assertThat(testAnimalCostos.getValor()).isEqualByComparingTo(DEFAULT_VALOR);
  }

  @Test
  @Transactional
  void createAnimalCostosWithExistingId() throws Exception {
    // Create the AnimalCostos with an existing ID
    animalCostos.setId(1L);

    int databaseSizeBeforeCreate = animalCostosRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restAnimalCostosMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalCostos))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalCostos in the database
    List<AnimalCostos> animalCostosList = animalCostosRepository.findAll();
    assertThat(animalCostosList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkFechaIsRequired() throws Exception {
    int databaseSizeBeforeTest = animalCostosRepository.findAll().size();
    // set the field null
    animalCostos.setFecha(null);

    // Create the AnimalCostos, which fails.

    restAnimalCostosMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalCostos))
      )
      .andExpect(status().isBadRequest());

    List<AnimalCostos> animalCostosList = animalCostosRepository.findAll();
    assertThat(animalCostosList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkValorIsRequired() throws Exception {
    int databaseSizeBeforeTest = animalCostosRepository.findAll().size();
    // set the field null
    animalCostos.setValor(null);

    // Create the AnimalCostos, which fails.

    restAnimalCostosMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalCostos))
      )
      .andExpect(status().isBadRequest());

    List<AnimalCostos> animalCostosList = animalCostosRepository.findAll();
    assertThat(animalCostosList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllAnimalCostos() throws Exception {
    // Initialize the database
    animalCostosRepository.saveAndFlush(animalCostos);

    // Get all the animalCostosList
    restAnimalCostosMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(animalCostos.getId().intValue())))
      .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
      .andExpect(jsonPath("$.[*].valor").value(hasItem(sameNumber(DEFAULT_VALOR))));
  }

  @Test
  @Transactional
  void getAnimalCostos() throws Exception {
    // Initialize the database
    animalCostosRepository.saveAndFlush(animalCostos);

    // Get the animalCostos
    restAnimalCostosMockMvc
      .perform(get(ENTITY_API_URL_ID, animalCostos.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(animalCostos.getId().intValue()))
      .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
      .andExpect(jsonPath("$.valor").value(sameNumber(DEFAULT_VALOR)));
  }

  @Test
  @Transactional
  void getNonExistingAnimalCostos() throws Exception {
    // Get the animalCostos
    restAnimalCostosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewAnimalCostos() throws Exception {
    // Initialize the database
    animalCostosRepository.saveAndFlush(animalCostos);

    int databaseSizeBeforeUpdate = animalCostosRepository.findAll().size();

    // Update the animalCostos
    AnimalCostos updatedAnimalCostos = animalCostosRepository.findById(animalCostos.getId()).get();
    // Disconnect from session so that the updates on updatedAnimalCostos are not directly saved in db
    em.detach(updatedAnimalCostos);
    updatedAnimalCostos.fecha(UPDATED_FECHA).valor(UPDATED_VALOR);

    restAnimalCostosMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedAnimalCostos.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedAnimalCostos))
      )
      .andExpect(status().isOk());

    // Validate the AnimalCostos in the database
    List<AnimalCostos> animalCostosList = animalCostosRepository.findAll();
    assertThat(animalCostosList).hasSize(databaseSizeBeforeUpdate);
    AnimalCostos testAnimalCostos = animalCostosList.get(animalCostosList.size() - 1);
    assertThat(testAnimalCostos.getFecha()).isEqualTo(UPDATED_FECHA);
    assertThat(testAnimalCostos.getValor()).isEqualTo(UPDATED_VALOR);
  }

  @Test
  @Transactional
  void putNonExistingAnimalCostos() throws Exception {
    int databaseSizeBeforeUpdate = animalCostosRepository.findAll().size();
    animalCostos.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restAnimalCostosMockMvc
      .perform(
        put(ENTITY_API_URL_ID, animalCostos.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(animalCostos))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalCostos in the database
    List<AnimalCostos> animalCostosList = animalCostosRepository.findAll();
    assertThat(animalCostosList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchAnimalCostos() throws Exception {
    int databaseSizeBeforeUpdate = animalCostosRepository.findAll().size();
    animalCostos.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalCostosMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(animalCostos))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalCostos in the database
    List<AnimalCostos> animalCostosList = animalCostosRepository.findAll();
    assertThat(animalCostosList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamAnimalCostos() throws Exception {
    int databaseSizeBeforeUpdate = animalCostosRepository.findAll().size();
    animalCostos.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalCostosMockMvc
      .perform(
        put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalCostos))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the AnimalCostos in the database
    List<AnimalCostos> animalCostosList = animalCostosRepository.findAll();
    assertThat(animalCostosList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateAnimalCostosWithPatch() throws Exception {
    // Initialize the database
    animalCostosRepository.saveAndFlush(animalCostos);

    int databaseSizeBeforeUpdate = animalCostosRepository.findAll().size();

    // Update the animalCostos using partial update
    AnimalCostos partialUpdatedAnimalCostos = new AnimalCostos();
    partialUpdatedAnimalCostos.setId(animalCostos.getId());

    restAnimalCostosMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedAnimalCostos.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnimalCostos))
      )
      .andExpect(status().isOk());

    // Validate the AnimalCostos in the database
    List<AnimalCostos> animalCostosList = animalCostosRepository.findAll();
    assertThat(animalCostosList).hasSize(databaseSizeBeforeUpdate);
    AnimalCostos testAnimalCostos = animalCostosList.get(animalCostosList.size() - 1);
    assertThat(testAnimalCostos.getFecha()).isEqualTo(DEFAULT_FECHA);
    assertThat(testAnimalCostos.getValor()).isEqualByComparingTo(DEFAULT_VALOR);
  }

  @Test
  @Transactional
  void fullUpdateAnimalCostosWithPatch() throws Exception {
    // Initialize the database
    animalCostosRepository.saveAndFlush(animalCostos);

    int databaseSizeBeforeUpdate = animalCostosRepository.findAll().size();

    // Update the animalCostos using partial update
    AnimalCostos partialUpdatedAnimalCostos = new AnimalCostos();
    partialUpdatedAnimalCostos.setId(animalCostos.getId());

    partialUpdatedAnimalCostos.fecha(UPDATED_FECHA).valor(UPDATED_VALOR);

    restAnimalCostosMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedAnimalCostos.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnimalCostos))
      )
      .andExpect(status().isOk());

    // Validate the AnimalCostos in the database
    List<AnimalCostos> animalCostosList = animalCostosRepository.findAll();
    assertThat(animalCostosList).hasSize(databaseSizeBeforeUpdate);
    AnimalCostos testAnimalCostos = animalCostosList.get(animalCostosList.size() - 1);
    assertThat(testAnimalCostos.getFecha()).isEqualTo(UPDATED_FECHA);
    assertThat(testAnimalCostos.getValor()).isEqualByComparingTo(UPDATED_VALOR);
  }

  @Test
  @Transactional
  void patchNonExistingAnimalCostos() throws Exception {
    int databaseSizeBeforeUpdate = animalCostosRepository.findAll().size();
    animalCostos.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restAnimalCostosMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, animalCostos.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animalCostos))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalCostos in the database
    List<AnimalCostos> animalCostosList = animalCostosRepository.findAll();
    assertThat(animalCostosList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchAnimalCostos() throws Exception {
    int databaseSizeBeforeUpdate = animalCostosRepository.findAll().size();
    animalCostos.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalCostosMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animalCostos))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalCostos in the database
    List<AnimalCostos> animalCostosList = animalCostosRepository.findAll();
    assertThat(animalCostosList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamAnimalCostos() throws Exception {
    int databaseSizeBeforeUpdate = animalCostosRepository.findAll().size();
    animalCostos.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalCostosMockMvc
      .perform(
        patch(ENTITY_API_URL)
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animalCostos))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the AnimalCostos in the database
    List<AnimalCostos> animalCostosList = animalCostosRepository.findAll();
    assertThat(animalCostosList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteAnimalCostos() throws Exception {
    // Initialize the database
    animalCostosRepository.saveAndFlush(animalCostos);

    int databaseSizeBeforeDelete = animalCostosRepository.findAll().size();

    // Delete the animalCostos
    restAnimalCostosMockMvc
      .perform(delete(ENTITY_API_URL_ID, animalCostos.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<AnimalCostos> animalCostosList = animalCostosRepository.findAll();
    assertThat(animalCostosList).hasSize(databaseSizeBeforeDelete - 1);
  }
}

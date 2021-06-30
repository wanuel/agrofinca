package co.cima.agrofinca.web.rest;

import static co.cima.agrofinca.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.cima.agrofinca.IntegrationTest;
import co.cima.agrofinca.domain.AnimalPeso;
import co.cima.agrofinca.repository.AnimalPesoRepository;
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
 * Integration tests for the {@link AnimalPesoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnimalPesoResourceIT {

  private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

  private static final BigDecimal DEFAULT_PESO = new BigDecimal(1);
  private static final BigDecimal UPDATED_PESO = new BigDecimal(2);

  private static final String ENTITY_API_URL = "/api/animal-pesos";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private AnimalPesoRepository animalPesoRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restAnimalPesoMockMvc;

  private AnimalPeso animalPeso;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static AnimalPeso createEntity(EntityManager em) {
    AnimalPeso animalPeso = new AnimalPeso().fecha(DEFAULT_FECHA).peso(DEFAULT_PESO);
    return animalPeso;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static AnimalPeso createUpdatedEntity(EntityManager em) {
    AnimalPeso animalPeso = new AnimalPeso().fecha(UPDATED_FECHA).peso(UPDATED_PESO);
    return animalPeso;
  }

  @BeforeEach
  public void initTest() {
    animalPeso = createEntity(em);
  }

  @Test
  @Transactional
  void createAnimalPeso() throws Exception {
    int databaseSizeBeforeCreate = animalPesoRepository.findAll().size();
    // Create the AnimalPeso
    restAnimalPesoMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalPeso))
      )
      .andExpect(status().isCreated());

    // Validate the AnimalPeso in the database
    List<AnimalPeso> animalPesoList = animalPesoRepository.findAll();
    assertThat(animalPesoList).hasSize(databaseSizeBeforeCreate + 1);
    AnimalPeso testAnimalPeso = animalPesoList.get(animalPesoList.size() - 1);
    assertThat(testAnimalPeso.getFecha()).isEqualTo(DEFAULT_FECHA);
    assertThat(testAnimalPeso.getPeso()).isEqualByComparingTo(DEFAULT_PESO);
  }

  @Test
  @Transactional
  void createAnimalPesoWithExistingId() throws Exception {
    // Create the AnimalPeso with an existing ID
    animalPeso.setId(1L);

    int databaseSizeBeforeCreate = animalPesoRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restAnimalPesoMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalPeso))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalPeso in the database
    List<AnimalPeso> animalPesoList = animalPesoRepository.findAll();
    assertThat(animalPesoList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkFechaIsRequired() throws Exception {
    int databaseSizeBeforeTest = animalPesoRepository.findAll().size();
    // set the field null
    animalPeso.setFecha(null);

    // Create the AnimalPeso, which fails.

    restAnimalPesoMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalPeso))
      )
      .andExpect(status().isBadRequest());

    List<AnimalPeso> animalPesoList = animalPesoRepository.findAll();
    assertThat(animalPesoList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkPesoIsRequired() throws Exception {
    int databaseSizeBeforeTest = animalPesoRepository.findAll().size();
    // set the field null
    animalPeso.setPeso(null);

    // Create the AnimalPeso, which fails.

    restAnimalPesoMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalPeso))
      )
      .andExpect(status().isBadRequest());

    List<AnimalPeso> animalPesoList = animalPesoRepository.findAll();
    assertThat(animalPesoList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllAnimalPesos() throws Exception {
    // Initialize the database
    animalPesoRepository.saveAndFlush(animalPeso);

    // Get all the animalPesoList
    restAnimalPesoMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(animalPeso.getId().intValue())))
      .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
      .andExpect(jsonPath("$.[*].peso").value(hasItem(sameNumber(DEFAULT_PESO))));
  }

  @Test
  @Transactional
  void getAnimalPeso() throws Exception {
    // Initialize the database
    animalPesoRepository.saveAndFlush(animalPeso);

    // Get the animalPeso
    restAnimalPesoMockMvc
      .perform(get(ENTITY_API_URL_ID, animalPeso.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(animalPeso.getId().intValue()))
      .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
      .andExpect(jsonPath("$.peso").value(sameNumber(DEFAULT_PESO)));
  }

  @Test
  @Transactional
  void getNonExistingAnimalPeso() throws Exception {
    // Get the animalPeso
    restAnimalPesoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewAnimalPeso() throws Exception {
    // Initialize the database
    animalPesoRepository.saveAndFlush(animalPeso);

    int databaseSizeBeforeUpdate = animalPesoRepository.findAll().size();

    // Update the animalPeso
    AnimalPeso updatedAnimalPeso = animalPesoRepository.findById(animalPeso.getId()).get();
    // Disconnect from session so that the updates on updatedAnimalPeso are not directly saved in db
    em.detach(updatedAnimalPeso);
    updatedAnimalPeso.fecha(UPDATED_FECHA).peso(UPDATED_PESO);

    restAnimalPesoMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedAnimalPeso.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedAnimalPeso))
      )
      .andExpect(status().isOk());

    // Validate the AnimalPeso in the database
    List<AnimalPeso> animalPesoList = animalPesoRepository.findAll();
    assertThat(animalPesoList).hasSize(databaseSizeBeforeUpdate);
    AnimalPeso testAnimalPeso = animalPesoList.get(animalPesoList.size() - 1);
    assertThat(testAnimalPeso.getFecha()).isEqualTo(UPDATED_FECHA);
    assertThat(testAnimalPeso.getPeso()).isEqualTo(UPDATED_PESO);
  }

  @Test
  @Transactional
  void putNonExistingAnimalPeso() throws Exception {
    int databaseSizeBeforeUpdate = animalPesoRepository.findAll().size();
    animalPeso.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restAnimalPesoMockMvc
      .perform(
        put(ENTITY_API_URL_ID, animalPeso.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(animalPeso))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalPeso in the database
    List<AnimalPeso> animalPesoList = animalPesoRepository.findAll();
    assertThat(animalPesoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchAnimalPeso() throws Exception {
    int databaseSizeBeforeUpdate = animalPesoRepository.findAll().size();
    animalPeso.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalPesoMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(animalPeso))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalPeso in the database
    List<AnimalPeso> animalPesoList = animalPesoRepository.findAll();
    assertThat(animalPesoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamAnimalPeso() throws Exception {
    int databaseSizeBeforeUpdate = animalPesoRepository.findAll().size();
    animalPeso.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalPesoMockMvc
      .perform(
        put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalPeso))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the AnimalPeso in the database
    List<AnimalPeso> animalPesoList = animalPesoRepository.findAll();
    assertThat(animalPesoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateAnimalPesoWithPatch() throws Exception {
    // Initialize the database
    animalPesoRepository.saveAndFlush(animalPeso);

    int databaseSizeBeforeUpdate = animalPesoRepository.findAll().size();

    // Update the animalPeso using partial update
    AnimalPeso partialUpdatedAnimalPeso = new AnimalPeso();
    partialUpdatedAnimalPeso.setId(animalPeso.getId());

    partialUpdatedAnimalPeso.peso(UPDATED_PESO);

    restAnimalPesoMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedAnimalPeso.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnimalPeso))
      )
      .andExpect(status().isOk());

    // Validate the AnimalPeso in the database
    List<AnimalPeso> animalPesoList = animalPesoRepository.findAll();
    assertThat(animalPesoList).hasSize(databaseSizeBeforeUpdate);
    AnimalPeso testAnimalPeso = animalPesoList.get(animalPesoList.size() - 1);
    assertThat(testAnimalPeso.getFecha()).isEqualTo(DEFAULT_FECHA);
    assertThat(testAnimalPeso.getPeso()).isEqualByComparingTo(UPDATED_PESO);
  }

  @Test
  @Transactional
  void fullUpdateAnimalPesoWithPatch() throws Exception {
    // Initialize the database
    animalPesoRepository.saveAndFlush(animalPeso);

    int databaseSizeBeforeUpdate = animalPesoRepository.findAll().size();

    // Update the animalPeso using partial update
    AnimalPeso partialUpdatedAnimalPeso = new AnimalPeso();
    partialUpdatedAnimalPeso.setId(animalPeso.getId());

    partialUpdatedAnimalPeso.fecha(UPDATED_FECHA).peso(UPDATED_PESO);

    restAnimalPesoMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedAnimalPeso.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnimalPeso))
      )
      .andExpect(status().isOk());

    // Validate the AnimalPeso in the database
    List<AnimalPeso> animalPesoList = animalPesoRepository.findAll();
    assertThat(animalPesoList).hasSize(databaseSizeBeforeUpdate);
    AnimalPeso testAnimalPeso = animalPesoList.get(animalPesoList.size() - 1);
    assertThat(testAnimalPeso.getFecha()).isEqualTo(UPDATED_FECHA);
    assertThat(testAnimalPeso.getPeso()).isEqualByComparingTo(UPDATED_PESO);
  }

  @Test
  @Transactional
  void patchNonExistingAnimalPeso() throws Exception {
    int databaseSizeBeforeUpdate = animalPesoRepository.findAll().size();
    animalPeso.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restAnimalPesoMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, animalPeso.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animalPeso))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalPeso in the database
    List<AnimalPeso> animalPesoList = animalPesoRepository.findAll();
    assertThat(animalPesoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchAnimalPeso() throws Exception {
    int databaseSizeBeforeUpdate = animalPesoRepository.findAll().size();
    animalPeso.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalPesoMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animalPeso))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalPeso in the database
    List<AnimalPeso> animalPesoList = animalPesoRepository.findAll();
    assertThat(animalPesoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamAnimalPeso() throws Exception {
    int databaseSizeBeforeUpdate = animalPesoRepository.findAll().size();
    animalPeso.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalPesoMockMvc
      .perform(
        patch(ENTITY_API_URL)
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animalPeso))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the AnimalPeso in the database
    List<AnimalPeso> animalPesoList = animalPesoRepository.findAll();
    assertThat(animalPesoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteAnimalPeso() throws Exception {
    // Initialize the database
    animalPesoRepository.saveAndFlush(animalPeso);

    int databaseSizeBeforeDelete = animalPesoRepository.findAll().size();

    // Delete the animalPeso
    restAnimalPesoMockMvc
      .perform(delete(ENTITY_API_URL_ID, animalPeso.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<AnimalPeso> animalPesoList = animalPesoRepository.findAll();
    assertThat(animalPesoList).hasSize(databaseSizeBeforeDelete - 1);
  }
}

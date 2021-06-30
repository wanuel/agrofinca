package co.cima.agrofinca.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.cima.agrofinca.IntegrationTest;
import co.cima.agrofinca.domain.AnimalVacunas;
import co.cima.agrofinca.repository.AnimalVacunasRepository;
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
 * Integration tests for the {@link AnimalVacunasResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnimalVacunasResourceIT {

  private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

  private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
  private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

  private static final String DEFAULT_LABORATORIO = "AAAAAAAAAA";
  private static final String UPDATED_LABORATORIO = "BBBBBBBBBB";

  private static final String DEFAULT_DOSIS = "AAAAAAAAAA";
  private static final String UPDATED_DOSIS = "BBBBBBBBBB";

  private static final String ENTITY_API_URL = "/api/animal-vacunas";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private AnimalVacunasRepository animalVacunasRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restAnimalVacunasMockMvc;

  private AnimalVacunas animalVacunas;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static AnimalVacunas createEntity(EntityManager em) {
    AnimalVacunas animalVacunas = new AnimalVacunas()
      .fecha(DEFAULT_FECHA)
      .nombre(DEFAULT_NOMBRE)
      .laboratorio(DEFAULT_LABORATORIO)
      .dosis(DEFAULT_DOSIS);
    return animalVacunas;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static AnimalVacunas createUpdatedEntity(EntityManager em) {
    AnimalVacunas animalVacunas = new AnimalVacunas()
      .fecha(UPDATED_FECHA)
      .nombre(UPDATED_NOMBRE)
      .laboratorio(UPDATED_LABORATORIO)
      .dosis(UPDATED_DOSIS);
    return animalVacunas;
  }

  @BeforeEach
  public void initTest() {
    animalVacunas = createEntity(em);
  }

  @Test
  @Transactional
  void createAnimalVacunas() throws Exception {
    int databaseSizeBeforeCreate = animalVacunasRepository.findAll().size();
    // Create the AnimalVacunas
    restAnimalVacunasMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalVacunas))
      )
      .andExpect(status().isCreated());

    // Validate the AnimalVacunas in the database
    List<AnimalVacunas> animalVacunasList = animalVacunasRepository.findAll();
    assertThat(animalVacunasList).hasSize(databaseSizeBeforeCreate + 1);
    AnimalVacunas testAnimalVacunas = animalVacunasList.get(animalVacunasList.size() - 1);
    assertThat(testAnimalVacunas.getFecha()).isEqualTo(DEFAULT_FECHA);
    assertThat(testAnimalVacunas.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    assertThat(testAnimalVacunas.getLaboratorio()).isEqualTo(DEFAULT_LABORATORIO);
    assertThat(testAnimalVacunas.getDosis()).isEqualTo(DEFAULT_DOSIS);
  }

  @Test
  @Transactional
  void createAnimalVacunasWithExistingId() throws Exception {
    // Create the AnimalVacunas with an existing ID
    animalVacunas.setId(1L);

    int databaseSizeBeforeCreate = animalVacunasRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restAnimalVacunasMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalVacunas))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalVacunas in the database
    List<AnimalVacunas> animalVacunasList = animalVacunasRepository.findAll();
    assertThat(animalVacunasList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkFechaIsRequired() throws Exception {
    int databaseSizeBeforeTest = animalVacunasRepository.findAll().size();
    // set the field null
    animalVacunas.setFecha(null);

    // Create the AnimalVacunas, which fails.

    restAnimalVacunasMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalVacunas))
      )
      .andExpect(status().isBadRequest());

    List<AnimalVacunas> animalVacunasList = animalVacunasRepository.findAll();
    assertThat(animalVacunasList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkNombreIsRequired() throws Exception {
    int databaseSizeBeforeTest = animalVacunasRepository.findAll().size();
    // set the field null
    animalVacunas.setNombre(null);

    // Create the AnimalVacunas, which fails.

    restAnimalVacunasMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalVacunas))
      )
      .andExpect(status().isBadRequest());

    List<AnimalVacunas> animalVacunasList = animalVacunasRepository.findAll();
    assertThat(animalVacunasList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkDosisIsRequired() throws Exception {
    int databaseSizeBeforeTest = animalVacunasRepository.findAll().size();
    // set the field null
    animalVacunas.setDosis(null);

    // Create the AnimalVacunas, which fails.

    restAnimalVacunasMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalVacunas))
      )
      .andExpect(status().isBadRequest());

    List<AnimalVacunas> animalVacunasList = animalVacunasRepository.findAll();
    assertThat(animalVacunasList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllAnimalVacunas() throws Exception {
    // Initialize the database
    animalVacunasRepository.saveAndFlush(animalVacunas);

    // Get all the animalVacunasList
    restAnimalVacunasMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(animalVacunas.getId().intValue())))
      .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
      .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
      .andExpect(jsonPath("$.[*].laboratorio").value(hasItem(DEFAULT_LABORATORIO)))
      .andExpect(jsonPath("$.[*].dosis").value(hasItem(DEFAULT_DOSIS)));
  }

  @Test
  @Transactional
  void getAnimalVacunas() throws Exception {
    // Initialize the database
    animalVacunasRepository.saveAndFlush(animalVacunas);

    // Get the animalVacunas
    restAnimalVacunasMockMvc
      .perform(get(ENTITY_API_URL_ID, animalVacunas.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(animalVacunas.getId().intValue()))
      .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
      .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
      .andExpect(jsonPath("$.laboratorio").value(DEFAULT_LABORATORIO))
      .andExpect(jsonPath("$.dosis").value(DEFAULT_DOSIS));
  }

  @Test
  @Transactional
  void getNonExistingAnimalVacunas() throws Exception {
    // Get the animalVacunas
    restAnimalVacunasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewAnimalVacunas() throws Exception {
    // Initialize the database
    animalVacunasRepository.saveAndFlush(animalVacunas);

    int databaseSizeBeforeUpdate = animalVacunasRepository.findAll().size();

    // Update the animalVacunas
    AnimalVacunas updatedAnimalVacunas = animalVacunasRepository.findById(animalVacunas.getId()).get();
    // Disconnect from session so that the updates on updatedAnimalVacunas are not directly saved in db
    em.detach(updatedAnimalVacunas);
    updatedAnimalVacunas.fecha(UPDATED_FECHA).nombre(UPDATED_NOMBRE).laboratorio(UPDATED_LABORATORIO).dosis(UPDATED_DOSIS);

    restAnimalVacunasMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedAnimalVacunas.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedAnimalVacunas))
      )
      .andExpect(status().isOk());

    // Validate the AnimalVacunas in the database
    List<AnimalVacunas> animalVacunasList = animalVacunasRepository.findAll();
    assertThat(animalVacunasList).hasSize(databaseSizeBeforeUpdate);
    AnimalVacunas testAnimalVacunas = animalVacunasList.get(animalVacunasList.size() - 1);
    assertThat(testAnimalVacunas.getFecha()).isEqualTo(UPDATED_FECHA);
    assertThat(testAnimalVacunas.getNombre()).isEqualTo(UPDATED_NOMBRE);
    assertThat(testAnimalVacunas.getLaboratorio()).isEqualTo(UPDATED_LABORATORIO);
    assertThat(testAnimalVacunas.getDosis()).isEqualTo(UPDATED_DOSIS);
  }

  @Test
  @Transactional
  void putNonExistingAnimalVacunas() throws Exception {
    int databaseSizeBeforeUpdate = animalVacunasRepository.findAll().size();
    animalVacunas.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restAnimalVacunasMockMvc
      .perform(
        put(ENTITY_API_URL_ID, animalVacunas.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(animalVacunas))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalVacunas in the database
    List<AnimalVacunas> animalVacunasList = animalVacunasRepository.findAll();
    assertThat(animalVacunasList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchAnimalVacunas() throws Exception {
    int databaseSizeBeforeUpdate = animalVacunasRepository.findAll().size();
    animalVacunas.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalVacunasMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(animalVacunas))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalVacunas in the database
    List<AnimalVacunas> animalVacunasList = animalVacunasRepository.findAll();
    assertThat(animalVacunasList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamAnimalVacunas() throws Exception {
    int databaseSizeBeforeUpdate = animalVacunasRepository.findAll().size();
    animalVacunas.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalVacunasMockMvc
      .perform(
        put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalVacunas))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the AnimalVacunas in the database
    List<AnimalVacunas> animalVacunasList = animalVacunasRepository.findAll();
    assertThat(animalVacunasList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateAnimalVacunasWithPatch() throws Exception {
    // Initialize the database
    animalVacunasRepository.saveAndFlush(animalVacunas);

    int databaseSizeBeforeUpdate = animalVacunasRepository.findAll().size();

    // Update the animalVacunas using partial update
    AnimalVacunas partialUpdatedAnimalVacunas = new AnimalVacunas();
    partialUpdatedAnimalVacunas.setId(animalVacunas.getId());

    partialUpdatedAnimalVacunas.nombre(UPDATED_NOMBRE).dosis(UPDATED_DOSIS);

    restAnimalVacunasMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedAnimalVacunas.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnimalVacunas))
      )
      .andExpect(status().isOk());

    // Validate the AnimalVacunas in the database
    List<AnimalVacunas> animalVacunasList = animalVacunasRepository.findAll();
    assertThat(animalVacunasList).hasSize(databaseSizeBeforeUpdate);
    AnimalVacunas testAnimalVacunas = animalVacunasList.get(animalVacunasList.size() - 1);
    assertThat(testAnimalVacunas.getFecha()).isEqualTo(DEFAULT_FECHA);
    assertThat(testAnimalVacunas.getNombre()).isEqualTo(UPDATED_NOMBRE);
    assertThat(testAnimalVacunas.getLaboratorio()).isEqualTo(DEFAULT_LABORATORIO);
    assertThat(testAnimalVacunas.getDosis()).isEqualTo(UPDATED_DOSIS);
  }

  @Test
  @Transactional
  void fullUpdateAnimalVacunasWithPatch() throws Exception {
    // Initialize the database
    animalVacunasRepository.saveAndFlush(animalVacunas);

    int databaseSizeBeforeUpdate = animalVacunasRepository.findAll().size();

    // Update the animalVacunas using partial update
    AnimalVacunas partialUpdatedAnimalVacunas = new AnimalVacunas();
    partialUpdatedAnimalVacunas.setId(animalVacunas.getId());

    partialUpdatedAnimalVacunas.fecha(UPDATED_FECHA).nombre(UPDATED_NOMBRE).laboratorio(UPDATED_LABORATORIO).dosis(UPDATED_DOSIS);

    restAnimalVacunasMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedAnimalVacunas.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnimalVacunas))
      )
      .andExpect(status().isOk());

    // Validate the AnimalVacunas in the database
    List<AnimalVacunas> animalVacunasList = animalVacunasRepository.findAll();
    assertThat(animalVacunasList).hasSize(databaseSizeBeforeUpdate);
    AnimalVacunas testAnimalVacunas = animalVacunasList.get(animalVacunasList.size() - 1);
    assertThat(testAnimalVacunas.getFecha()).isEqualTo(UPDATED_FECHA);
    assertThat(testAnimalVacunas.getNombre()).isEqualTo(UPDATED_NOMBRE);
    assertThat(testAnimalVacunas.getLaboratorio()).isEqualTo(UPDATED_LABORATORIO);
    assertThat(testAnimalVacunas.getDosis()).isEqualTo(UPDATED_DOSIS);
  }

  @Test
  @Transactional
  void patchNonExistingAnimalVacunas() throws Exception {
    int databaseSizeBeforeUpdate = animalVacunasRepository.findAll().size();
    animalVacunas.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restAnimalVacunasMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, animalVacunas.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animalVacunas))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalVacunas in the database
    List<AnimalVacunas> animalVacunasList = animalVacunasRepository.findAll();
    assertThat(animalVacunasList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchAnimalVacunas() throws Exception {
    int databaseSizeBeforeUpdate = animalVacunasRepository.findAll().size();
    animalVacunas.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalVacunasMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animalVacunas))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalVacunas in the database
    List<AnimalVacunas> animalVacunasList = animalVacunasRepository.findAll();
    assertThat(animalVacunasList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamAnimalVacunas() throws Exception {
    int databaseSizeBeforeUpdate = animalVacunasRepository.findAll().size();
    animalVacunas.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalVacunasMockMvc
      .perform(
        patch(ENTITY_API_URL)
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animalVacunas))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the AnimalVacunas in the database
    List<AnimalVacunas> animalVacunasList = animalVacunasRepository.findAll();
    assertThat(animalVacunasList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteAnimalVacunas() throws Exception {
    // Initialize the database
    animalVacunasRepository.saveAndFlush(animalVacunas);

    int databaseSizeBeforeDelete = animalVacunasRepository.findAll().size();

    // Delete the animalVacunas
    restAnimalVacunasMockMvc
      .perform(delete(ENTITY_API_URL_ID, animalVacunas.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<AnimalVacunas> animalVacunasList = animalVacunasRepository.findAll();
    assertThat(animalVacunasList).hasSize(databaseSizeBeforeDelete - 1);
  }
}

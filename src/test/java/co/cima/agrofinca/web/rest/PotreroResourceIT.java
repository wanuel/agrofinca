package co.cima.agrofinca.web.rest;

import static co.cima.agrofinca.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.cima.agrofinca.IntegrationTest;
import co.cima.agrofinca.domain.Potrero;
import co.cima.agrofinca.repository.PotreroRepository;
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
 * Integration tests for the {@link PotreroResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PotreroResourceIT {

  private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
  private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

  private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
  private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

  private static final String DEFAULT_PASTO = "AAAAAAAAAA";
  private static final String UPDATED_PASTO = "BBBBBBBBBB";

  private static final BigDecimal DEFAULT_AREA = new BigDecimal(1);
  private static final BigDecimal UPDATED_AREA = new BigDecimal(2);

  private static final String ENTITY_API_URL = "/api/potreros";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private PotreroRepository potreroRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restPotreroMockMvc;

  private Potrero potrero;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Potrero createEntity(EntityManager em) {
    Potrero potrero = new Potrero().nombre(DEFAULT_NOMBRE).descripcion(DEFAULT_DESCRIPCION).pasto(DEFAULT_PASTO).area(DEFAULT_AREA);
    return potrero;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Potrero createUpdatedEntity(EntityManager em) {
    Potrero potrero = new Potrero().nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION).pasto(UPDATED_PASTO).area(UPDATED_AREA);
    return potrero;
  }

  @BeforeEach
  public void initTest() {
    potrero = createEntity(em);
  }

  @Test
  @Transactional
  void createPotrero() throws Exception {
    int databaseSizeBeforeCreate = potreroRepository.findAll().size();
    // Create the Potrero
    restPotreroMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(potrero))
      )
      .andExpect(status().isCreated());

    // Validate the Potrero in the database
    List<Potrero> potreroList = potreroRepository.findAll();
    assertThat(potreroList).hasSize(databaseSizeBeforeCreate + 1);
    Potrero testPotrero = potreroList.get(potreroList.size() - 1);
    assertThat(testPotrero.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    assertThat(testPotrero.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    assertThat(testPotrero.getPasto()).isEqualTo(DEFAULT_PASTO);
    assertThat(testPotrero.getArea()).isEqualByComparingTo(DEFAULT_AREA);
  }

  @Test
  @Transactional
  void createPotreroWithExistingId() throws Exception {
    // Create the Potrero with an existing ID
    potrero.setId(1L);

    int databaseSizeBeforeCreate = potreroRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restPotreroMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(potrero))
      )
      .andExpect(status().isBadRequest());

    // Validate the Potrero in the database
    List<Potrero> potreroList = potreroRepository.findAll();
    assertThat(potreroList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkNombreIsRequired() throws Exception {
    int databaseSizeBeforeTest = potreroRepository.findAll().size();
    // set the field null
    potrero.setNombre(null);

    // Create the Potrero, which fails.

    restPotreroMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(potrero))
      )
      .andExpect(status().isBadRequest());

    List<Potrero> potreroList = potreroRepository.findAll();
    assertThat(potreroList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllPotreros() throws Exception {
    // Initialize the database
    potreroRepository.saveAndFlush(potrero);

    // Get all the potreroList
    restPotreroMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(potrero.getId().intValue())))
      .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
      .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
      .andExpect(jsonPath("$.[*].pasto").value(hasItem(DEFAULT_PASTO)))
      .andExpect(jsonPath("$.[*].area").value(hasItem(sameNumber(DEFAULT_AREA))));
  }

  @Test
  @Transactional
  void getPotrero() throws Exception {
    // Initialize the database
    potreroRepository.saveAndFlush(potrero);

    // Get the potrero
    restPotreroMockMvc
      .perform(get(ENTITY_API_URL_ID, potrero.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(potrero.getId().intValue()))
      .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
      .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
      .andExpect(jsonPath("$.pasto").value(DEFAULT_PASTO))
      .andExpect(jsonPath("$.area").value(sameNumber(DEFAULT_AREA)));
  }

  @Test
  @Transactional
  void getNonExistingPotrero() throws Exception {
    // Get the potrero
    restPotreroMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewPotrero() throws Exception {
    // Initialize the database
    potreroRepository.saveAndFlush(potrero);

    int databaseSizeBeforeUpdate = potreroRepository.findAll().size();

    // Update the potrero
    Potrero updatedPotrero = potreroRepository.findById(potrero.getId()).get();
    // Disconnect from session so that the updates on updatedPotrero are not directly saved in db
    em.detach(updatedPotrero);
    updatedPotrero.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION).pasto(UPDATED_PASTO).area(UPDATED_AREA);

    restPotreroMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedPotrero.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedPotrero))
      )
      .andExpect(status().isOk());

    // Validate the Potrero in the database
    List<Potrero> potreroList = potreroRepository.findAll();
    assertThat(potreroList).hasSize(databaseSizeBeforeUpdate);
    Potrero testPotrero = potreroList.get(potreroList.size() - 1);
    assertThat(testPotrero.getNombre()).isEqualTo(UPDATED_NOMBRE);
    assertThat(testPotrero.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    assertThat(testPotrero.getPasto()).isEqualTo(UPDATED_PASTO);
    assertThat(testPotrero.getArea()).isEqualTo(UPDATED_AREA);
  }

  @Test
  @Transactional
  void putNonExistingPotrero() throws Exception {
    int databaseSizeBeforeUpdate = potreroRepository.findAll().size();
    potrero.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restPotreroMockMvc
      .perform(
        put(ENTITY_API_URL_ID, potrero.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(potrero))
      )
      .andExpect(status().isBadRequest());

    // Validate the Potrero in the database
    List<Potrero> potreroList = potreroRepository.findAll();
    assertThat(potreroList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchPotrero() throws Exception {
    int databaseSizeBeforeUpdate = potreroRepository.findAll().size();
    potrero.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restPotreroMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(potrero))
      )
      .andExpect(status().isBadRequest());

    // Validate the Potrero in the database
    List<Potrero> potreroList = potreroRepository.findAll();
    assertThat(potreroList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamPotrero() throws Exception {
    int databaseSizeBeforeUpdate = potreroRepository.findAll().size();
    potrero.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restPotreroMockMvc
      .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(potrero)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the Potrero in the database
    List<Potrero> potreroList = potreroRepository.findAll();
    assertThat(potreroList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdatePotreroWithPatch() throws Exception {
    // Initialize the database
    potreroRepository.saveAndFlush(potrero);

    int databaseSizeBeforeUpdate = potreroRepository.findAll().size();

    // Update the potrero using partial update
    Potrero partialUpdatedPotrero = new Potrero();
    partialUpdatedPotrero.setId(potrero.getId());

    partialUpdatedPotrero.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION).pasto(UPDATED_PASTO);

    restPotreroMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedPotrero.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPotrero))
      )
      .andExpect(status().isOk());

    // Validate the Potrero in the database
    List<Potrero> potreroList = potreroRepository.findAll();
    assertThat(potreroList).hasSize(databaseSizeBeforeUpdate);
    Potrero testPotrero = potreroList.get(potreroList.size() - 1);
    assertThat(testPotrero.getNombre()).isEqualTo(UPDATED_NOMBRE);
    assertThat(testPotrero.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    assertThat(testPotrero.getPasto()).isEqualTo(UPDATED_PASTO);
    assertThat(testPotrero.getArea()).isEqualByComparingTo(DEFAULT_AREA);
  }

  @Test
  @Transactional
  void fullUpdatePotreroWithPatch() throws Exception {
    // Initialize the database
    potreroRepository.saveAndFlush(potrero);

    int databaseSizeBeforeUpdate = potreroRepository.findAll().size();

    // Update the potrero using partial update
    Potrero partialUpdatedPotrero = new Potrero();
    partialUpdatedPotrero.setId(potrero.getId());

    partialUpdatedPotrero.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION).pasto(UPDATED_PASTO).area(UPDATED_AREA);

    restPotreroMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedPotrero.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPotrero))
      )
      .andExpect(status().isOk());

    // Validate the Potrero in the database
    List<Potrero> potreroList = potreroRepository.findAll();
    assertThat(potreroList).hasSize(databaseSizeBeforeUpdate);
    Potrero testPotrero = potreroList.get(potreroList.size() - 1);
    assertThat(testPotrero.getNombre()).isEqualTo(UPDATED_NOMBRE);
    assertThat(testPotrero.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    assertThat(testPotrero.getPasto()).isEqualTo(UPDATED_PASTO);
    assertThat(testPotrero.getArea()).isEqualByComparingTo(UPDATED_AREA);
  }

  @Test
  @Transactional
  void patchNonExistingPotrero() throws Exception {
    int databaseSizeBeforeUpdate = potreroRepository.findAll().size();
    potrero.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restPotreroMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, potrero.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(potrero))
      )
      .andExpect(status().isBadRequest());

    // Validate the Potrero in the database
    List<Potrero> potreroList = potreroRepository.findAll();
    assertThat(potreroList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchPotrero() throws Exception {
    int databaseSizeBeforeUpdate = potreroRepository.findAll().size();
    potrero.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restPotreroMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(potrero))
      )
      .andExpect(status().isBadRequest());

    // Validate the Potrero in the database
    List<Potrero> potreroList = potreroRepository.findAll();
    assertThat(potreroList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamPotrero() throws Exception {
    int databaseSizeBeforeUpdate = potreroRepository.findAll().size();
    potrero.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restPotreroMockMvc
      .perform(
        patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(potrero))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the Potrero in the database
    List<Potrero> potreroList = potreroRepository.findAll();
    assertThat(potreroList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deletePotrero() throws Exception {
    // Initialize the database
    potreroRepository.saveAndFlush(potrero);

    int databaseSizeBeforeDelete = potreroRepository.findAll().size();

    // Delete the potrero
    restPotreroMockMvc
      .perform(delete(ENTITY_API_URL_ID, potrero.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<Potrero> potreroList = potreroRepository.findAll();
    assertThat(potreroList).hasSize(databaseSizeBeforeDelete - 1);
  }
}

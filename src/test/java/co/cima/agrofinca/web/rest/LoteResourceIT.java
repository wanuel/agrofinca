package co.cima.agrofinca.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.cima.agrofinca.IntegrationTest;
import co.cima.agrofinca.domain.Lote;
import co.cima.agrofinca.domain.enumeration.TipoLoteEnum;
import co.cima.agrofinca.repository.LoteRepository;
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
 * Integration tests for the {@link LoteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LoteResourceIT {

  private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
  private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

  private static final TipoLoteEnum DEFAULT_TIPO = TipoLoteEnum.COMPRA;
  private static final TipoLoteEnum UPDATED_TIPO = TipoLoteEnum.VENTA;

  private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

  private static final Integer DEFAULT_NUMERO_ANIMALES = 1;
  private static final Integer UPDATED_NUMERO_ANIMALES = 2;

  private static final String ENTITY_API_URL = "/api/lotes";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private LoteRepository loteRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restLoteMockMvc;

  private Lote lote;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Lote createEntity(EntityManager em) {
    Lote lote = new Lote().nombre(DEFAULT_NOMBRE).tipo(DEFAULT_TIPO).fecha(DEFAULT_FECHA).numeroAnimales(DEFAULT_NUMERO_ANIMALES);
    return lote;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Lote createUpdatedEntity(EntityManager em) {
    Lote lote = new Lote().nombre(UPDATED_NOMBRE).tipo(UPDATED_TIPO).fecha(UPDATED_FECHA).numeroAnimales(UPDATED_NUMERO_ANIMALES);
    return lote;
  }

  @BeforeEach
  public void initTest() {
    lote = createEntity(em);
  }

  @Test
  @Transactional
  void createLote() throws Exception {
    int databaseSizeBeforeCreate = loteRepository.findAll().size();
    // Create the Lote
    restLoteMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lote)))
      .andExpect(status().isCreated());

    // Validate the Lote in the database
    List<Lote> loteList = loteRepository.findAll();
    assertThat(loteList).hasSize(databaseSizeBeforeCreate + 1);
    Lote testLote = loteList.get(loteList.size() - 1);
    assertThat(testLote.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    assertThat(testLote.getTipo()).isEqualTo(DEFAULT_TIPO);
    assertThat(testLote.getFecha()).isEqualTo(DEFAULT_FECHA);
    assertThat(testLote.getNumeroAnimales()).isEqualTo(DEFAULT_NUMERO_ANIMALES);
  }

  @Test
  @Transactional
  void createLoteWithExistingId() throws Exception {
    // Create the Lote with an existing ID
    lote.setId(1L);

    int databaseSizeBeforeCreate = loteRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restLoteMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lote)))
      .andExpect(status().isBadRequest());

    // Validate the Lote in the database
    List<Lote> loteList = loteRepository.findAll();
    assertThat(loteList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkNombreIsRequired() throws Exception {
    int databaseSizeBeforeTest = loteRepository.findAll().size();
    // set the field null
    lote.setNombre(null);

    // Create the Lote, which fails.

    restLoteMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lote)))
      .andExpect(status().isBadRequest());

    List<Lote> loteList = loteRepository.findAll();
    assertThat(loteList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkTipoIsRequired() throws Exception {
    int databaseSizeBeforeTest = loteRepository.findAll().size();
    // set the field null
    lote.setTipo(null);

    // Create the Lote, which fails.

    restLoteMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lote)))
      .andExpect(status().isBadRequest());

    List<Lote> loteList = loteRepository.findAll();
    assertThat(loteList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkFechaIsRequired() throws Exception {
    int databaseSizeBeforeTest = loteRepository.findAll().size();
    // set the field null
    lote.setFecha(null);

    // Create the Lote, which fails.

    restLoteMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lote)))
      .andExpect(status().isBadRequest());

    List<Lote> loteList = loteRepository.findAll();
    assertThat(loteList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkNumeroAnimalesIsRequired() throws Exception {
    int databaseSizeBeforeTest = loteRepository.findAll().size();
    // set the field null
    lote.setNumeroAnimales(null);

    // Create the Lote, which fails.

    restLoteMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lote)))
      .andExpect(status().isBadRequest());

    List<Lote> loteList = loteRepository.findAll();
    assertThat(loteList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllLotes() throws Exception {
    // Initialize the database
    loteRepository.saveAndFlush(lote);

    // Get all the loteList
    restLoteMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(lote.getId().intValue())))
      .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
      .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
      .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
      .andExpect(jsonPath("$.[*].numeroAnimales").value(hasItem(DEFAULT_NUMERO_ANIMALES)));
  }

  @Test
  @Transactional
  void getLote() throws Exception {
    // Initialize the database
    loteRepository.saveAndFlush(lote);

    // Get the lote
    restLoteMockMvc
      .perform(get(ENTITY_API_URL_ID, lote.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(lote.getId().intValue()))
      .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
      .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
      .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
      .andExpect(jsonPath("$.numeroAnimales").value(DEFAULT_NUMERO_ANIMALES));
  }

  @Test
  @Transactional
  void getNonExistingLote() throws Exception {
    // Get the lote
    restLoteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewLote() throws Exception {
    // Initialize the database
    loteRepository.saveAndFlush(lote);

    int databaseSizeBeforeUpdate = loteRepository.findAll().size();

    // Update the lote
    Lote updatedLote = loteRepository.findById(lote.getId()).get();
    // Disconnect from session so that the updates on updatedLote are not directly saved in db
    em.detach(updatedLote);
    updatedLote.nombre(UPDATED_NOMBRE).tipo(UPDATED_TIPO).fecha(UPDATED_FECHA).numeroAnimales(UPDATED_NUMERO_ANIMALES);

    restLoteMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedLote.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedLote))
      )
      .andExpect(status().isOk());

    // Validate the Lote in the database
    List<Lote> loteList = loteRepository.findAll();
    assertThat(loteList).hasSize(databaseSizeBeforeUpdate);
    Lote testLote = loteList.get(loteList.size() - 1);
    assertThat(testLote.getNombre()).isEqualTo(UPDATED_NOMBRE);
    assertThat(testLote.getTipo()).isEqualTo(UPDATED_TIPO);
    assertThat(testLote.getFecha()).isEqualTo(UPDATED_FECHA);
    assertThat(testLote.getNumeroAnimales()).isEqualTo(UPDATED_NUMERO_ANIMALES);
  }

  @Test
  @Transactional
  void putNonExistingLote() throws Exception {
    int databaseSizeBeforeUpdate = loteRepository.findAll().size();
    lote.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restLoteMockMvc
      .perform(
        put(ENTITY_API_URL_ID, lote.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(lote))
      )
      .andExpect(status().isBadRequest());

    // Validate the Lote in the database
    List<Lote> loteList = loteRepository.findAll();
    assertThat(loteList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchLote() throws Exception {
    int databaseSizeBeforeUpdate = loteRepository.findAll().size();
    lote.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restLoteMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(lote))
      )
      .andExpect(status().isBadRequest());

    // Validate the Lote in the database
    List<Lote> loteList = loteRepository.findAll();
    assertThat(loteList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamLote() throws Exception {
    int databaseSizeBeforeUpdate = loteRepository.findAll().size();
    lote.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restLoteMockMvc
      .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lote)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the Lote in the database
    List<Lote> loteList = loteRepository.findAll();
    assertThat(loteList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateLoteWithPatch() throws Exception {
    // Initialize the database
    loteRepository.saveAndFlush(lote);

    int databaseSizeBeforeUpdate = loteRepository.findAll().size();

    // Update the lote using partial update
    Lote partialUpdatedLote = new Lote();
    partialUpdatedLote.setId(lote.getId());

    partialUpdatedLote.nombre(UPDATED_NOMBRE);

    restLoteMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedLote.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLote))
      )
      .andExpect(status().isOk());

    // Validate the Lote in the database
    List<Lote> loteList = loteRepository.findAll();
    assertThat(loteList).hasSize(databaseSizeBeforeUpdate);
    Lote testLote = loteList.get(loteList.size() - 1);
    assertThat(testLote.getNombre()).isEqualTo(UPDATED_NOMBRE);
    assertThat(testLote.getTipo()).isEqualTo(DEFAULT_TIPO);
    assertThat(testLote.getFecha()).isEqualTo(DEFAULT_FECHA);
    assertThat(testLote.getNumeroAnimales()).isEqualTo(DEFAULT_NUMERO_ANIMALES);
  }

  @Test
  @Transactional
  void fullUpdateLoteWithPatch() throws Exception {
    // Initialize the database
    loteRepository.saveAndFlush(lote);

    int databaseSizeBeforeUpdate = loteRepository.findAll().size();

    // Update the lote using partial update
    Lote partialUpdatedLote = new Lote();
    partialUpdatedLote.setId(lote.getId());

    partialUpdatedLote.nombre(UPDATED_NOMBRE).tipo(UPDATED_TIPO).fecha(UPDATED_FECHA).numeroAnimales(UPDATED_NUMERO_ANIMALES);

    restLoteMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedLote.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLote))
      )
      .andExpect(status().isOk());

    // Validate the Lote in the database
    List<Lote> loteList = loteRepository.findAll();
    assertThat(loteList).hasSize(databaseSizeBeforeUpdate);
    Lote testLote = loteList.get(loteList.size() - 1);
    assertThat(testLote.getNombre()).isEqualTo(UPDATED_NOMBRE);
    assertThat(testLote.getTipo()).isEqualTo(UPDATED_TIPO);
    assertThat(testLote.getFecha()).isEqualTo(UPDATED_FECHA);
    assertThat(testLote.getNumeroAnimales()).isEqualTo(UPDATED_NUMERO_ANIMALES);
  }

  @Test
  @Transactional
  void patchNonExistingLote() throws Exception {
    int databaseSizeBeforeUpdate = loteRepository.findAll().size();
    lote.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restLoteMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, lote.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(lote))
      )
      .andExpect(status().isBadRequest());

    // Validate the Lote in the database
    List<Lote> loteList = loteRepository.findAll();
    assertThat(loteList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchLote() throws Exception {
    int databaseSizeBeforeUpdate = loteRepository.findAll().size();
    lote.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restLoteMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(lote))
      )
      .andExpect(status().isBadRequest());

    // Validate the Lote in the database
    List<Lote> loteList = loteRepository.findAll();
    assertThat(loteList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamLote() throws Exception {
    int databaseSizeBeforeUpdate = loteRepository.findAll().size();
    lote.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restLoteMockMvc
      .perform(
        patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(lote))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the Lote in the database
    List<Lote> loteList = loteRepository.findAll();
    assertThat(loteList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteLote() throws Exception {
    // Initialize the database
    loteRepository.saveAndFlush(lote);

    int databaseSizeBeforeDelete = loteRepository.findAll().size();

    // Delete the lote
    restLoteMockMvc
      .perform(delete(ENTITY_API_URL_ID, lote.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<Lote> loteList = loteRepository.findAll();
    assertThat(loteList).hasSize(databaseSizeBeforeDelete - 1);
  }
}

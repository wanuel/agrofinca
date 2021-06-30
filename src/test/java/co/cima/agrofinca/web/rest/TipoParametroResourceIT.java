package co.cima.agrofinca.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.cima.agrofinca.IntegrationTest;
import co.cima.agrofinca.domain.TipoParametro;
import co.cima.agrofinca.repository.TipoParametroRepository;
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
 * Integration tests for the {@link TipoParametroResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoParametroResourceIT {

  private static final String DEFAULT_TIPA_DESCRIPCION = "AAAAAAAAAA";
  private static final String UPDATED_TIPA_DESCRIPCION = "BBBBBBBBBB";

  private static final String ENTITY_API_URL = "/api/tipo-parametros";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private TipoParametroRepository tipoParametroRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restTipoParametroMockMvc;

  private TipoParametro tipoParametro;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static TipoParametro createEntity(EntityManager em) {
    TipoParametro tipoParametro = new TipoParametro().tipaDescripcion(DEFAULT_TIPA_DESCRIPCION);
    return tipoParametro;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static TipoParametro createUpdatedEntity(EntityManager em) {
    TipoParametro tipoParametro = new TipoParametro().tipaDescripcion(UPDATED_TIPA_DESCRIPCION);
    return tipoParametro;
  }

  @BeforeEach
  public void initTest() {
    tipoParametro = createEntity(em);
  }

  @Test
  @Transactional
  void createTipoParametro() throws Exception {
    int databaseSizeBeforeCreate = tipoParametroRepository.findAll().size();
    // Create the TipoParametro
    restTipoParametroMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoParametro))
      )
      .andExpect(status().isCreated());

    // Validate the TipoParametro in the database
    List<TipoParametro> tipoParametroList = tipoParametroRepository.findAll();
    assertThat(tipoParametroList).hasSize(databaseSizeBeforeCreate + 1);
    TipoParametro testTipoParametro = tipoParametroList.get(tipoParametroList.size() - 1);
    assertThat(testTipoParametro.getTipaDescripcion()).isEqualTo(DEFAULT_TIPA_DESCRIPCION);
  }

  @Test
  @Transactional
  void createTipoParametroWithExistingId() throws Exception {
    // Create the TipoParametro with an existing ID
    tipoParametro.setId(1L);

    int databaseSizeBeforeCreate = tipoParametroRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restTipoParametroMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoParametro))
      )
      .andExpect(status().isBadRequest());

    // Validate the TipoParametro in the database
    List<TipoParametro> tipoParametroList = tipoParametroRepository.findAll();
    assertThat(tipoParametroList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkTipaDescripcionIsRequired() throws Exception {
    int databaseSizeBeforeTest = tipoParametroRepository.findAll().size();
    // set the field null
    tipoParametro.setTipaDescripcion(null);

    // Create the TipoParametro, which fails.

    restTipoParametroMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoParametro))
      )
      .andExpect(status().isBadRequest());

    List<TipoParametro> tipoParametroList = tipoParametroRepository.findAll();
    assertThat(tipoParametroList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllTipoParametros() throws Exception {
    // Initialize the database
    tipoParametroRepository.saveAndFlush(tipoParametro);

    // Get all the tipoParametroList
    restTipoParametroMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(tipoParametro.getId().intValue())))
      .andExpect(jsonPath("$.[*].tipaDescripcion").value(hasItem(DEFAULT_TIPA_DESCRIPCION)));
  }

  @Test
  @Transactional
  void getTipoParametro() throws Exception {
    // Initialize the database
    tipoParametroRepository.saveAndFlush(tipoParametro);

    // Get the tipoParametro
    restTipoParametroMockMvc
      .perform(get(ENTITY_API_URL_ID, tipoParametro.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(tipoParametro.getId().intValue()))
      .andExpect(jsonPath("$.tipaDescripcion").value(DEFAULT_TIPA_DESCRIPCION));
  }

  @Test
  @Transactional
  void getNonExistingTipoParametro() throws Exception {
    // Get the tipoParametro
    restTipoParametroMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewTipoParametro() throws Exception {
    // Initialize the database
    tipoParametroRepository.saveAndFlush(tipoParametro);

    int databaseSizeBeforeUpdate = tipoParametroRepository.findAll().size();

    // Update the tipoParametro
    TipoParametro updatedTipoParametro = tipoParametroRepository.findById(tipoParametro.getId()).get();
    // Disconnect from session so that the updates on updatedTipoParametro are not directly saved in db
    em.detach(updatedTipoParametro);
    updatedTipoParametro.tipaDescripcion(UPDATED_TIPA_DESCRIPCION);

    restTipoParametroMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedTipoParametro.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedTipoParametro))
      )
      .andExpect(status().isOk());

    // Validate the TipoParametro in the database
    List<TipoParametro> tipoParametroList = tipoParametroRepository.findAll();
    assertThat(tipoParametroList).hasSize(databaseSizeBeforeUpdate);
    TipoParametro testTipoParametro = tipoParametroList.get(tipoParametroList.size() - 1);
    assertThat(testTipoParametro.getTipaDescripcion()).isEqualTo(UPDATED_TIPA_DESCRIPCION);
  }

  @Test
  @Transactional
  void putNonExistingTipoParametro() throws Exception {
    int databaseSizeBeforeUpdate = tipoParametroRepository.findAll().size();
    tipoParametro.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restTipoParametroMockMvc
      .perform(
        put(ENTITY_API_URL_ID, tipoParametro.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(tipoParametro))
      )
      .andExpect(status().isBadRequest());

    // Validate the TipoParametro in the database
    List<TipoParametro> tipoParametroList = tipoParametroRepository.findAll();
    assertThat(tipoParametroList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchTipoParametro() throws Exception {
    int databaseSizeBeforeUpdate = tipoParametroRepository.findAll().size();
    tipoParametro.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restTipoParametroMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(tipoParametro))
      )
      .andExpect(status().isBadRequest());

    // Validate the TipoParametro in the database
    List<TipoParametro> tipoParametroList = tipoParametroRepository.findAll();
    assertThat(tipoParametroList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamTipoParametro() throws Exception {
    int databaseSizeBeforeUpdate = tipoParametroRepository.findAll().size();
    tipoParametro.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restTipoParametroMockMvc
      .perform(
        put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoParametro))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the TipoParametro in the database
    List<TipoParametro> tipoParametroList = tipoParametroRepository.findAll();
    assertThat(tipoParametroList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateTipoParametroWithPatch() throws Exception {
    // Initialize the database
    tipoParametroRepository.saveAndFlush(tipoParametro);

    int databaseSizeBeforeUpdate = tipoParametroRepository.findAll().size();

    // Update the tipoParametro using partial update
    TipoParametro partialUpdatedTipoParametro = new TipoParametro();
    partialUpdatedTipoParametro.setId(tipoParametro.getId());

    partialUpdatedTipoParametro.tipaDescripcion(UPDATED_TIPA_DESCRIPCION);

    restTipoParametroMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedTipoParametro.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoParametro))
      )
      .andExpect(status().isOk());

    // Validate the TipoParametro in the database
    List<TipoParametro> tipoParametroList = tipoParametroRepository.findAll();
    assertThat(tipoParametroList).hasSize(databaseSizeBeforeUpdate);
    TipoParametro testTipoParametro = tipoParametroList.get(tipoParametroList.size() - 1);
    assertThat(testTipoParametro.getTipaDescripcion()).isEqualTo(UPDATED_TIPA_DESCRIPCION);
  }

  @Test
  @Transactional
  void fullUpdateTipoParametroWithPatch() throws Exception {
    // Initialize the database
    tipoParametroRepository.saveAndFlush(tipoParametro);

    int databaseSizeBeforeUpdate = tipoParametroRepository.findAll().size();

    // Update the tipoParametro using partial update
    TipoParametro partialUpdatedTipoParametro = new TipoParametro();
    partialUpdatedTipoParametro.setId(tipoParametro.getId());

    partialUpdatedTipoParametro.tipaDescripcion(UPDATED_TIPA_DESCRIPCION);

    restTipoParametroMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedTipoParametro.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoParametro))
      )
      .andExpect(status().isOk());

    // Validate the TipoParametro in the database
    List<TipoParametro> tipoParametroList = tipoParametroRepository.findAll();
    assertThat(tipoParametroList).hasSize(databaseSizeBeforeUpdate);
    TipoParametro testTipoParametro = tipoParametroList.get(tipoParametroList.size() - 1);
    assertThat(testTipoParametro.getTipaDescripcion()).isEqualTo(UPDATED_TIPA_DESCRIPCION);
  }

  @Test
  @Transactional
  void patchNonExistingTipoParametro() throws Exception {
    int databaseSizeBeforeUpdate = tipoParametroRepository.findAll().size();
    tipoParametro.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restTipoParametroMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, tipoParametro.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(tipoParametro))
      )
      .andExpect(status().isBadRequest());

    // Validate the TipoParametro in the database
    List<TipoParametro> tipoParametroList = tipoParametroRepository.findAll();
    assertThat(tipoParametroList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchTipoParametro() throws Exception {
    int databaseSizeBeforeUpdate = tipoParametroRepository.findAll().size();
    tipoParametro.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restTipoParametroMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(tipoParametro))
      )
      .andExpect(status().isBadRequest());

    // Validate the TipoParametro in the database
    List<TipoParametro> tipoParametroList = tipoParametroRepository.findAll();
    assertThat(tipoParametroList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamTipoParametro() throws Exception {
    int databaseSizeBeforeUpdate = tipoParametroRepository.findAll().size();
    tipoParametro.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restTipoParametroMockMvc
      .perform(
        patch(ENTITY_API_URL)
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(tipoParametro))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the TipoParametro in the database
    List<TipoParametro> tipoParametroList = tipoParametroRepository.findAll();
    assertThat(tipoParametroList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteTipoParametro() throws Exception {
    // Initialize the database
    tipoParametroRepository.saveAndFlush(tipoParametro);

    int databaseSizeBeforeDelete = tipoParametroRepository.findAll().size();

    // Delete the tipoParametro
    restTipoParametroMockMvc
      .perform(delete(ENTITY_API_URL_ID, tipoParametro.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<TipoParametro> tipoParametroList = tipoParametroRepository.findAll();
    assertThat(tipoParametroList).hasSize(databaseSizeBeforeDelete - 1);
  }
}

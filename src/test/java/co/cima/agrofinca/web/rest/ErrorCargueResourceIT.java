package co.cima.agrofinca.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.cima.agrofinca.IntegrationTest;
import co.cima.agrofinca.domain.ErrorCargue;
import co.cima.agrofinca.repository.ErrorCargueRepository;
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
 * Integration tests for the {@link ErrorCargueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ErrorCargueResourceIT {

  private static final String DEFAULT_ERCA_ERROR = "AAAAAAAAAA";
  private static final String UPDATED_ERCA_ERROR = "BBBBBBBBBB";

  private static final String ENTITY_API_URL = "/api/error-cargues";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private ErrorCargueRepository errorCargueRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restErrorCargueMockMvc;

  private ErrorCargue errorCargue;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static ErrorCargue createEntity(EntityManager em) {
    ErrorCargue errorCargue = new ErrorCargue().ercaError(DEFAULT_ERCA_ERROR);
    return errorCargue;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static ErrorCargue createUpdatedEntity(EntityManager em) {
    ErrorCargue errorCargue = new ErrorCargue().ercaError(UPDATED_ERCA_ERROR);
    return errorCargue;
  }

  @BeforeEach
  public void initTest() {
    errorCargue = createEntity(em);
  }

  @Test
  @Transactional
  void createErrorCargue() throws Exception {
    int databaseSizeBeforeCreate = errorCargueRepository.findAll().size();
    // Create the ErrorCargue
    restErrorCargueMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(errorCargue))
      )
      .andExpect(status().isCreated());

    // Validate the ErrorCargue in the database
    List<ErrorCargue> errorCargueList = errorCargueRepository.findAll();
    assertThat(errorCargueList).hasSize(databaseSizeBeforeCreate + 1);
    ErrorCargue testErrorCargue = errorCargueList.get(errorCargueList.size() - 1);
    assertThat(testErrorCargue.getErcaError()).isEqualTo(DEFAULT_ERCA_ERROR);
  }

  @Test
  @Transactional
  void createErrorCargueWithExistingId() throws Exception {
    // Create the ErrorCargue with an existing ID
    errorCargue.setId(1L);

    int databaseSizeBeforeCreate = errorCargueRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restErrorCargueMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(errorCargue))
      )
      .andExpect(status().isBadRequest());

    // Validate the ErrorCargue in the database
    List<ErrorCargue> errorCargueList = errorCargueRepository.findAll();
    assertThat(errorCargueList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkErcaErrorIsRequired() throws Exception {
    int databaseSizeBeforeTest = errorCargueRepository.findAll().size();
    // set the field null
    errorCargue.setErcaError(null);

    // Create the ErrorCargue, which fails.

    restErrorCargueMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(errorCargue))
      )
      .andExpect(status().isBadRequest());

    List<ErrorCargue> errorCargueList = errorCargueRepository.findAll();
    assertThat(errorCargueList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllErrorCargues() throws Exception {
    // Initialize the database
    errorCargueRepository.saveAndFlush(errorCargue);

    // Get all the errorCargueList
    restErrorCargueMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(errorCargue.getId().intValue())))
      .andExpect(jsonPath("$.[*].ercaError").value(hasItem(DEFAULT_ERCA_ERROR)));
  }

  @Test
  @Transactional
  void getErrorCargue() throws Exception {
    // Initialize the database
    errorCargueRepository.saveAndFlush(errorCargue);

    // Get the errorCargue
    restErrorCargueMockMvc
      .perform(get(ENTITY_API_URL_ID, errorCargue.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(errorCargue.getId().intValue()))
      .andExpect(jsonPath("$.ercaError").value(DEFAULT_ERCA_ERROR));
  }

  @Test
  @Transactional
  void getNonExistingErrorCargue() throws Exception {
    // Get the errorCargue
    restErrorCargueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewErrorCargue() throws Exception {
    // Initialize the database
    errorCargueRepository.saveAndFlush(errorCargue);

    int databaseSizeBeforeUpdate = errorCargueRepository.findAll().size();

    // Update the errorCargue
    ErrorCargue updatedErrorCargue = errorCargueRepository.findById(errorCargue.getId()).get();
    // Disconnect from session so that the updates on updatedErrorCargue are not directly saved in db
    em.detach(updatedErrorCargue);
    updatedErrorCargue.ercaError(UPDATED_ERCA_ERROR);

    restErrorCargueMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedErrorCargue.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedErrorCargue))
      )
      .andExpect(status().isOk());

    // Validate the ErrorCargue in the database
    List<ErrorCargue> errorCargueList = errorCargueRepository.findAll();
    assertThat(errorCargueList).hasSize(databaseSizeBeforeUpdate);
    ErrorCargue testErrorCargue = errorCargueList.get(errorCargueList.size() - 1);
    assertThat(testErrorCargue.getErcaError()).isEqualTo(UPDATED_ERCA_ERROR);
  }

  @Test
  @Transactional
  void putNonExistingErrorCargue() throws Exception {
    int databaseSizeBeforeUpdate = errorCargueRepository.findAll().size();
    errorCargue.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restErrorCargueMockMvc
      .perform(
        put(ENTITY_API_URL_ID, errorCargue.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(errorCargue))
      )
      .andExpect(status().isBadRequest());

    // Validate the ErrorCargue in the database
    List<ErrorCargue> errorCargueList = errorCargueRepository.findAll();
    assertThat(errorCargueList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchErrorCargue() throws Exception {
    int databaseSizeBeforeUpdate = errorCargueRepository.findAll().size();
    errorCargue.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restErrorCargueMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(errorCargue))
      )
      .andExpect(status().isBadRequest());

    // Validate the ErrorCargue in the database
    List<ErrorCargue> errorCargueList = errorCargueRepository.findAll();
    assertThat(errorCargueList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamErrorCargue() throws Exception {
    int databaseSizeBeforeUpdate = errorCargueRepository.findAll().size();
    errorCargue.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restErrorCargueMockMvc
      .perform(
        put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(errorCargue))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the ErrorCargue in the database
    List<ErrorCargue> errorCargueList = errorCargueRepository.findAll();
    assertThat(errorCargueList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateErrorCargueWithPatch() throws Exception {
    // Initialize the database
    errorCargueRepository.saveAndFlush(errorCargue);

    int databaseSizeBeforeUpdate = errorCargueRepository.findAll().size();

    // Update the errorCargue using partial update
    ErrorCargue partialUpdatedErrorCargue = new ErrorCargue();
    partialUpdatedErrorCargue.setId(errorCargue.getId());

    restErrorCargueMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedErrorCargue.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedErrorCargue))
      )
      .andExpect(status().isOk());

    // Validate the ErrorCargue in the database
    List<ErrorCargue> errorCargueList = errorCargueRepository.findAll();
    assertThat(errorCargueList).hasSize(databaseSizeBeforeUpdate);
    ErrorCargue testErrorCargue = errorCargueList.get(errorCargueList.size() - 1);
    assertThat(testErrorCargue.getErcaError()).isEqualTo(DEFAULT_ERCA_ERROR);
  }

  @Test
  @Transactional
  void fullUpdateErrorCargueWithPatch() throws Exception {
    // Initialize the database
    errorCargueRepository.saveAndFlush(errorCargue);

    int databaseSizeBeforeUpdate = errorCargueRepository.findAll().size();

    // Update the errorCargue using partial update
    ErrorCargue partialUpdatedErrorCargue = new ErrorCargue();
    partialUpdatedErrorCargue.setId(errorCargue.getId());

    partialUpdatedErrorCargue.ercaError(UPDATED_ERCA_ERROR);

    restErrorCargueMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedErrorCargue.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedErrorCargue))
      )
      .andExpect(status().isOk());

    // Validate the ErrorCargue in the database
    List<ErrorCargue> errorCargueList = errorCargueRepository.findAll();
    assertThat(errorCargueList).hasSize(databaseSizeBeforeUpdate);
    ErrorCargue testErrorCargue = errorCargueList.get(errorCargueList.size() - 1);
    assertThat(testErrorCargue.getErcaError()).isEqualTo(UPDATED_ERCA_ERROR);
  }

  @Test
  @Transactional
  void patchNonExistingErrorCargue() throws Exception {
    int databaseSizeBeforeUpdate = errorCargueRepository.findAll().size();
    errorCargue.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restErrorCargueMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, errorCargue.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(errorCargue))
      )
      .andExpect(status().isBadRequest());

    // Validate the ErrorCargue in the database
    List<ErrorCargue> errorCargueList = errorCargueRepository.findAll();
    assertThat(errorCargueList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchErrorCargue() throws Exception {
    int databaseSizeBeforeUpdate = errorCargueRepository.findAll().size();
    errorCargue.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restErrorCargueMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(errorCargue))
      )
      .andExpect(status().isBadRequest());

    // Validate the ErrorCargue in the database
    List<ErrorCargue> errorCargueList = errorCargueRepository.findAll();
    assertThat(errorCargueList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamErrorCargue() throws Exception {
    int databaseSizeBeforeUpdate = errorCargueRepository.findAll().size();
    errorCargue.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restErrorCargueMockMvc
      .perform(
        patch(ENTITY_API_URL)
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(errorCargue))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the ErrorCargue in the database
    List<ErrorCargue> errorCargueList = errorCargueRepository.findAll();
    assertThat(errorCargueList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteErrorCargue() throws Exception {
    // Initialize the database
    errorCargueRepository.saveAndFlush(errorCargue);

    int databaseSizeBeforeDelete = errorCargueRepository.findAll().size();

    // Delete the errorCargue
    restErrorCargueMockMvc
      .perform(delete(ENTITY_API_URL_ID, errorCargue.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<ErrorCargue> errorCargueList = errorCargueRepository.findAll();
    assertThat(errorCargueList).hasSize(databaseSizeBeforeDelete - 1);
  }
}

package co.cima.agrofinca.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.cima.agrofinca.IntegrationTest;
import co.cima.agrofinca.domain.AnimalImagen;
import co.cima.agrofinca.repository.AnimalImagenRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link AnimalImagenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnimalImagenResourceIT {

  private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

  private static final String DEFAULT_NOTA = "AAAAAAAAAA";
  private static final String UPDATED_NOTA = "BBBBBBBBBB";

  private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
  private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
  private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
  private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

  private static final String ENTITY_API_URL = "/api/animal-imagens";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private AnimalImagenRepository animalImagenRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restAnimalImagenMockMvc;

  private AnimalImagen animalImagen;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static AnimalImagen createEntity(EntityManager em) {
    AnimalImagen animalImagen = new AnimalImagen()
      .fecha(DEFAULT_FECHA)
      .nota(DEFAULT_NOTA)
      .imagen(DEFAULT_IMAGEN)
      .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE);
    return animalImagen;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static AnimalImagen createUpdatedEntity(EntityManager em) {
    AnimalImagen animalImagen = new AnimalImagen()
      .fecha(UPDATED_FECHA)
      .nota(UPDATED_NOTA)
      .imagen(UPDATED_IMAGEN)
      .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);
    return animalImagen;
  }

  @BeforeEach
  public void initTest() {
    animalImagen = createEntity(em);
  }

  @Test
  @Transactional
  void createAnimalImagen() throws Exception {
    int databaseSizeBeforeCreate = animalImagenRepository.findAll().size();
    // Create the AnimalImagen
    restAnimalImagenMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalImagen))
      )
      .andExpect(status().isCreated());

    // Validate the AnimalImagen in the database
    List<AnimalImagen> animalImagenList = animalImagenRepository.findAll();
    assertThat(animalImagenList).hasSize(databaseSizeBeforeCreate + 1);
    AnimalImagen testAnimalImagen = animalImagenList.get(animalImagenList.size() - 1);
    assertThat(testAnimalImagen.getFecha()).isEqualTo(DEFAULT_FECHA);
    assertThat(testAnimalImagen.getNota()).isEqualTo(DEFAULT_NOTA);
    assertThat(testAnimalImagen.getImagen()).isEqualTo(DEFAULT_IMAGEN);
    assertThat(testAnimalImagen.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
  }

  @Test
  @Transactional
  void createAnimalImagenWithExistingId() throws Exception {
    // Create the AnimalImagen with an existing ID
    animalImagen.setId(1L);

    int databaseSizeBeforeCreate = animalImagenRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restAnimalImagenMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalImagen))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalImagen in the database
    List<AnimalImagen> animalImagenList = animalImagenRepository.findAll();
    assertThat(animalImagenList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkFechaIsRequired() throws Exception {
    int databaseSizeBeforeTest = animalImagenRepository.findAll().size();
    // set the field null
    animalImagen.setFecha(null);

    // Create the AnimalImagen, which fails.

    restAnimalImagenMockMvc
      .perform(
        post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalImagen))
      )
      .andExpect(status().isBadRequest());

    List<AnimalImagen> animalImagenList = animalImagenRepository.findAll();
    assertThat(animalImagenList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllAnimalImagens() throws Exception {
    // Initialize the database
    animalImagenRepository.saveAndFlush(animalImagen);

    // Get all the animalImagenList
    restAnimalImagenMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(animalImagen.getId().intValue())))
      .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
      .andExpect(jsonPath("$.[*].nota").value(hasItem(DEFAULT_NOTA)))
      .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
      .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))));
  }

  @Test
  @Transactional
  void getAnimalImagen() throws Exception {
    // Initialize the database
    animalImagenRepository.saveAndFlush(animalImagen);

    // Get the animalImagen
    restAnimalImagenMockMvc
      .perform(get(ENTITY_API_URL_ID, animalImagen.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(animalImagen.getId().intValue()))
      .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
      .andExpect(jsonPath("$.nota").value(DEFAULT_NOTA))
      .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
      .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)));
  }

  @Test
  @Transactional
  void getNonExistingAnimalImagen() throws Exception {
    // Get the animalImagen
    restAnimalImagenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewAnimalImagen() throws Exception {
    // Initialize the database
    animalImagenRepository.saveAndFlush(animalImagen);

    int databaseSizeBeforeUpdate = animalImagenRepository.findAll().size();

    // Update the animalImagen
    AnimalImagen updatedAnimalImagen = animalImagenRepository.findById(animalImagen.getId()).get();
    // Disconnect from session so that the updates on updatedAnimalImagen are not directly saved in db
    em.detach(updatedAnimalImagen);
    updatedAnimalImagen.fecha(UPDATED_FECHA).nota(UPDATED_NOTA).imagen(UPDATED_IMAGEN).imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);

    restAnimalImagenMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedAnimalImagen.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedAnimalImagen))
      )
      .andExpect(status().isOk());

    // Validate the AnimalImagen in the database
    List<AnimalImagen> animalImagenList = animalImagenRepository.findAll();
    assertThat(animalImagenList).hasSize(databaseSizeBeforeUpdate);
    AnimalImagen testAnimalImagen = animalImagenList.get(animalImagenList.size() - 1);
    assertThat(testAnimalImagen.getFecha()).isEqualTo(UPDATED_FECHA);
    assertThat(testAnimalImagen.getNota()).isEqualTo(UPDATED_NOTA);
    assertThat(testAnimalImagen.getImagen()).isEqualTo(UPDATED_IMAGEN);
    assertThat(testAnimalImagen.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
  }

  @Test
  @Transactional
  void putNonExistingAnimalImagen() throws Exception {
    int databaseSizeBeforeUpdate = animalImagenRepository.findAll().size();
    animalImagen.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restAnimalImagenMockMvc
      .perform(
        put(ENTITY_API_URL_ID, animalImagen.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(animalImagen))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalImagen in the database
    List<AnimalImagen> animalImagenList = animalImagenRepository.findAll();
    assertThat(animalImagenList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchAnimalImagen() throws Exception {
    int databaseSizeBeforeUpdate = animalImagenRepository.findAll().size();
    animalImagen.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalImagenMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(animalImagen))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalImagen in the database
    List<AnimalImagen> animalImagenList = animalImagenRepository.findAll();
    assertThat(animalImagenList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamAnimalImagen() throws Exception {
    int databaseSizeBeforeUpdate = animalImagenRepository.findAll().size();
    animalImagen.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalImagenMockMvc
      .perform(
        put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animalImagen))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the AnimalImagen in the database
    List<AnimalImagen> animalImagenList = animalImagenRepository.findAll();
    assertThat(animalImagenList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateAnimalImagenWithPatch() throws Exception {
    // Initialize the database
    animalImagenRepository.saveAndFlush(animalImagen);

    int databaseSizeBeforeUpdate = animalImagenRepository.findAll().size();

    // Update the animalImagen using partial update
    AnimalImagen partialUpdatedAnimalImagen = new AnimalImagen();
    partialUpdatedAnimalImagen.setId(animalImagen.getId());

    partialUpdatedAnimalImagen.fecha(UPDATED_FECHA).nota(UPDATED_NOTA);

    restAnimalImagenMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedAnimalImagen.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnimalImagen))
      )
      .andExpect(status().isOk());

    // Validate the AnimalImagen in the database
    List<AnimalImagen> animalImagenList = animalImagenRepository.findAll();
    assertThat(animalImagenList).hasSize(databaseSizeBeforeUpdate);
    AnimalImagen testAnimalImagen = animalImagenList.get(animalImagenList.size() - 1);
    assertThat(testAnimalImagen.getFecha()).isEqualTo(UPDATED_FECHA);
    assertThat(testAnimalImagen.getNota()).isEqualTo(UPDATED_NOTA);
    assertThat(testAnimalImagen.getImagen()).isEqualTo(DEFAULT_IMAGEN);
    assertThat(testAnimalImagen.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
  }

  @Test
  @Transactional
  void fullUpdateAnimalImagenWithPatch() throws Exception {
    // Initialize the database
    animalImagenRepository.saveAndFlush(animalImagen);

    int databaseSizeBeforeUpdate = animalImagenRepository.findAll().size();

    // Update the animalImagen using partial update
    AnimalImagen partialUpdatedAnimalImagen = new AnimalImagen();
    partialUpdatedAnimalImagen.setId(animalImagen.getId());

    partialUpdatedAnimalImagen
      .fecha(UPDATED_FECHA)
      .nota(UPDATED_NOTA)
      .imagen(UPDATED_IMAGEN)
      .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);

    restAnimalImagenMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedAnimalImagen.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnimalImagen))
      )
      .andExpect(status().isOk());

    // Validate the AnimalImagen in the database
    List<AnimalImagen> animalImagenList = animalImagenRepository.findAll();
    assertThat(animalImagenList).hasSize(databaseSizeBeforeUpdate);
    AnimalImagen testAnimalImagen = animalImagenList.get(animalImagenList.size() - 1);
    assertThat(testAnimalImagen.getFecha()).isEqualTo(UPDATED_FECHA);
    assertThat(testAnimalImagen.getNota()).isEqualTo(UPDATED_NOTA);
    assertThat(testAnimalImagen.getImagen()).isEqualTo(UPDATED_IMAGEN);
    assertThat(testAnimalImagen.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
  }

  @Test
  @Transactional
  void patchNonExistingAnimalImagen() throws Exception {
    int databaseSizeBeforeUpdate = animalImagenRepository.findAll().size();
    animalImagen.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restAnimalImagenMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, animalImagen.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animalImagen))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalImagen in the database
    List<AnimalImagen> animalImagenList = animalImagenRepository.findAll();
    assertThat(animalImagenList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchAnimalImagen() throws Exception {
    int databaseSizeBeforeUpdate = animalImagenRepository.findAll().size();
    animalImagen.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalImagenMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animalImagen))
      )
      .andExpect(status().isBadRequest());

    // Validate the AnimalImagen in the database
    List<AnimalImagen> animalImagenList = animalImagenRepository.findAll();
    assertThat(animalImagenList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamAnimalImagen() throws Exception {
    int databaseSizeBeforeUpdate = animalImagenRepository.findAll().size();
    animalImagen.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalImagenMockMvc
      .perform(
        patch(ENTITY_API_URL)
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animalImagen))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the AnimalImagen in the database
    List<AnimalImagen> animalImagenList = animalImagenRepository.findAll();
    assertThat(animalImagenList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteAnimalImagen() throws Exception {
    // Initialize the database
    animalImagenRepository.saveAndFlush(animalImagen);

    int databaseSizeBeforeDelete = animalImagenRepository.findAll().size();

    // Delete the animalImagen
    restAnimalImagenMockMvc
      .perform(delete(ENTITY_API_URL_ID, animalImagen.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<AnimalImagen> animalImagenList = animalImagenRepository.findAll();
    assertThat(animalImagenList).hasSize(databaseSizeBeforeDelete - 1);
  }
}

package co.cima.agrofinca.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.cima.agrofinca.IntegrationTest;
import co.cima.agrofinca.domain.Animal;
import co.cima.agrofinca.domain.enumeration.ESTADOANIMAL;
import co.cima.agrofinca.domain.enumeration.SEXO;
import co.cima.agrofinca.domain.enumeration.SINO;
import co.cima.agrofinca.domain.enumeration.SINO;
import co.cima.agrofinca.repository.AnimalRepository;
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
 * Integration tests for the {@link AnimalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnimalResourceIT {

  private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
  private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

  private static final String DEFAULT_CARACTERIZACION = "AAAAAAAAAA";
  private static final String UPDATED_CARACTERIZACION = "BBBBBBBBBB";

  private static final SINO DEFAULT_HIERRO = SINO.S;
  private static final SINO UPDATED_HIERRO = SINO.N;

  private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

  private static final LocalDate DEFAULT_FECHA_COMPRA = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_FECHA_COMPRA = LocalDate.now(ZoneId.systemDefault());

  private static final SEXO DEFAULT_SEXO = SEXO.MACHO;
  private static final SEXO UPDATED_SEXO = SEXO.HEMBRA;

  private static final SINO DEFAULT_CASTRADO = SINO.S;
  private static final SINO UPDATED_CASTRADO = SINO.N;

  private static final LocalDate DEFAULT_FECHA_CASTRACION = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_FECHA_CASTRACION = LocalDate.now(ZoneId.systemDefault());

  private static final ESTADOANIMAL DEFAULT_ESTADO = ESTADOANIMAL.VIVO;
  private static final ESTADOANIMAL UPDATED_ESTADO = ESTADOANIMAL.MUERTO;

  private static final String ENTITY_API_URL = "/api/animals";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private AnimalRepository animalRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restAnimalMockMvc;

  private Animal animal;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Animal createEntity(EntityManager em) {
    Animal animal = new Animal()
      .nombre(DEFAULT_NOMBRE)
      .caracterizacion(DEFAULT_CARACTERIZACION)
      .hierro(DEFAULT_HIERRO)
      .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
      .fechaCompra(DEFAULT_FECHA_COMPRA)
      .sexo(DEFAULT_SEXO)
      .castrado(DEFAULT_CASTRADO)
      .fechaCastracion(DEFAULT_FECHA_CASTRACION)
      .estado(DEFAULT_ESTADO);
    return animal;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Animal createUpdatedEntity(EntityManager em) {
    Animal animal = new Animal()
      .nombre(UPDATED_NOMBRE)
      .caracterizacion(UPDATED_CARACTERIZACION)
      .hierro(UPDATED_HIERRO)
      .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
      .fechaCompra(UPDATED_FECHA_COMPRA)
      .sexo(UPDATED_SEXO)
      .castrado(UPDATED_CASTRADO)
      .fechaCastracion(UPDATED_FECHA_CASTRACION)
      .estado(UPDATED_ESTADO);
    return animal;
  }

  @BeforeEach
  public void initTest() {
    animal = createEntity(em);
  }

  @Test
  @Transactional
  void createAnimal() throws Exception {
    int databaseSizeBeforeCreate = animalRepository.findAll().size();
    // Create the Animal
    restAnimalMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animal)))
      .andExpect(status().isCreated());

    // Validate the Animal in the database
    List<Animal> animalList = animalRepository.findAll();
    assertThat(animalList).hasSize(databaseSizeBeforeCreate + 1);
    Animal testAnimal = animalList.get(animalList.size() - 1);
    assertThat(testAnimal.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    assertThat(testAnimal.getCaracterizacion()).isEqualTo(DEFAULT_CARACTERIZACION);
    assertThat(testAnimal.getHierro()).isEqualTo(DEFAULT_HIERRO);
    assertThat(testAnimal.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
    assertThat(testAnimal.getFechaCompra()).isEqualTo(DEFAULT_FECHA_COMPRA);
    assertThat(testAnimal.getSexo()).isEqualTo(DEFAULT_SEXO);
    assertThat(testAnimal.getCastrado()).isEqualTo(DEFAULT_CASTRADO);
    assertThat(testAnimal.getFechaCastracion()).isEqualTo(DEFAULT_FECHA_CASTRACION);
    assertThat(testAnimal.getEstado()).isEqualTo(DEFAULT_ESTADO);
  }

  @Test
  @Transactional
  void createAnimalWithExistingId() throws Exception {
    // Create the Animal with an existing ID
    animal.setId(1L);

    int databaseSizeBeforeCreate = animalRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restAnimalMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animal)))
      .andExpect(status().isBadRequest());

    // Validate the Animal in the database
    List<Animal> animalList = animalRepository.findAll();
    assertThat(animalList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkSexoIsRequired() throws Exception {
    int databaseSizeBeforeTest = animalRepository.findAll().size();
    // set the field null
    animal.setSexo(null);

    // Create the Animal, which fails.

    restAnimalMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animal)))
      .andExpect(status().isBadRequest());

    List<Animal> animalList = animalRepository.findAll();
    assertThat(animalList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkCastradoIsRequired() throws Exception {
    int databaseSizeBeforeTest = animalRepository.findAll().size();
    // set the field null
    animal.setCastrado(null);

    // Create the Animal, which fails.

    restAnimalMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animal)))
      .andExpect(status().isBadRequest());

    List<Animal> animalList = animalRepository.findAll();
    assertThat(animalList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkEstadoIsRequired() throws Exception {
    int databaseSizeBeforeTest = animalRepository.findAll().size();
    // set the field null
    animal.setEstado(null);

    // Create the Animal, which fails.

    restAnimalMockMvc
      .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animal)))
      .andExpect(status().isBadRequest());

    List<Animal> animalList = animalRepository.findAll();
    assertThat(animalList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllAnimals() throws Exception {
    // Initialize the database
    animalRepository.saveAndFlush(animal);

    // Get all the animalList
    restAnimalMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(animal.getId().intValue())))
      .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
      .andExpect(jsonPath("$.[*].caracterizacion").value(hasItem(DEFAULT_CARACTERIZACION)))
      .andExpect(jsonPath("$.[*].hierro").value(hasItem(DEFAULT_HIERRO.toString())))
      .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
      .andExpect(jsonPath("$.[*].fechaCompra").value(hasItem(DEFAULT_FECHA_COMPRA.toString())))
      .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
      .andExpect(jsonPath("$.[*].castrado").value(hasItem(DEFAULT_CASTRADO.toString())))
      .andExpect(jsonPath("$.[*].fechaCastracion").value(hasItem(DEFAULT_FECHA_CASTRACION.toString())))
      .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
  }

  @Test
  @Transactional
  void getAnimal() throws Exception {
    // Initialize the database
    animalRepository.saveAndFlush(animal);

    // Get the animal
    restAnimalMockMvc
      .perform(get(ENTITY_API_URL_ID, animal.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(animal.getId().intValue()))
      .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
      .andExpect(jsonPath("$.caracterizacion").value(DEFAULT_CARACTERIZACION))
      .andExpect(jsonPath("$.hierro").value(DEFAULT_HIERRO.toString()))
      .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
      .andExpect(jsonPath("$.fechaCompra").value(DEFAULT_FECHA_COMPRA.toString()))
      .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
      .andExpect(jsonPath("$.castrado").value(DEFAULT_CASTRADO.toString()))
      .andExpect(jsonPath("$.fechaCastracion").value(DEFAULT_FECHA_CASTRACION.toString()))
      .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
  }

  @Test
  @Transactional
  void getNonExistingAnimal() throws Exception {
    // Get the animal
    restAnimalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewAnimal() throws Exception {
    // Initialize the database
    animalRepository.saveAndFlush(animal);

    int databaseSizeBeforeUpdate = animalRepository.findAll().size();

    // Update the animal
    Animal updatedAnimal = animalRepository.findById(animal.getId()).get();
    // Disconnect from session so that the updates on updatedAnimal are not directly saved in db
    em.detach(updatedAnimal);
    updatedAnimal
      .nombre(UPDATED_NOMBRE)
      .caracterizacion(UPDATED_CARACTERIZACION)
      .hierro(UPDATED_HIERRO)
      .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
      .fechaCompra(UPDATED_FECHA_COMPRA)
      .sexo(UPDATED_SEXO)
      .castrado(UPDATED_CASTRADO)
      .fechaCastracion(UPDATED_FECHA_CASTRACION)
      .estado(UPDATED_ESTADO);

    restAnimalMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedAnimal.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedAnimal))
      )
      .andExpect(status().isOk());

    // Validate the Animal in the database
    List<Animal> animalList = animalRepository.findAll();
    assertThat(animalList).hasSize(databaseSizeBeforeUpdate);
    Animal testAnimal = animalList.get(animalList.size() - 1);
    assertThat(testAnimal.getNombre()).isEqualTo(UPDATED_NOMBRE);
    assertThat(testAnimal.getCaracterizacion()).isEqualTo(UPDATED_CARACTERIZACION);
    assertThat(testAnimal.getHierro()).isEqualTo(UPDATED_HIERRO);
    assertThat(testAnimal.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
    assertThat(testAnimal.getFechaCompra()).isEqualTo(UPDATED_FECHA_COMPRA);
    assertThat(testAnimal.getSexo()).isEqualTo(UPDATED_SEXO);
    assertThat(testAnimal.getCastrado()).isEqualTo(UPDATED_CASTRADO);
    assertThat(testAnimal.getFechaCastracion()).isEqualTo(UPDATED_FECHA_CASTRACION);
    assertThat(testAnimal.getEstado()).isEqualTo(UPDATED_ESTADO);
  }

  @Test
  @Transactional
  void putNonExistingAnimal() throws Exception {
    int databaseSizeBeforeUpdate = animalRepository.findAll().size();
    animal.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restAnimalMockMvc
      .perform(
        put(ENTITY_API_URL_ID, animal.getId())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(animal))
      )
      .andExpect(status().isBadRequest());

    // Validate the Animal in the database
    List<Animal> animalList = animalRepository.findAll();
    assertThat(animalList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchAnimal() throws Exception {
    int databaseSizeBeforeUpdate = animalRepository.findAll().size();
    animal.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(animal))
      )
      .andExpect(status().isBadRequest());

    // Validate the Animal in the database
    List<Animal> animalList = animalRepository.findAll();
    assertThat(animalList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamAnimal() throws Exception {
    int databaseSizeBeforeUpdate = animalRepository.findAll().size();
    animal.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalMockMvc
      .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(animal)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the Animal in the database
    List<Animal> animalList = animalRepository.findAll();
    assertThat(animalList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateAnimalWithPatch() throws Exception {
    // Initialize the database
    animalRepository.saveAndFlush(animal);

    int databaseSizeBeforeUpdate = animalRepository.findAll().size();

    // Update the animal using partial update
    Animal partialUpdatedAnimal = new Animal();
    partialUpdatedAnimal.setId(animal.getId());

    partialUpdatedAnimal
      .caracterizacion(UPDATED_CARACTERIZACION)
      .fechaCompra(UPDATED_FECHA_COMPRA)
      .castrado(UPDATED_CASTRADO)
      .fechaCastracion(UPDATED_FECHA_CASTRACION);

    restAnimalMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedAnimal.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnimal))
      )
      .andExpect(status().isOk());

    // Validate the Animal in the database
    List<Animal> animalList = animalRepository.findAll();
    assertThat(animalList).hasSize(databaseSizeBeforeUpdate);
    Animal testAnimal = animalList.get(animalList.size() - 1);
    assertThat(testAnimal.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    assertThat(testAnimal.getCaracterizacion()).isEqualTo(UPDATED_CARACTERIZACION);
    assertThat(testAnimal.getHierro()).isEqualTo(DEFAULT_HIERRO);
    assertThat(testAnimal.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
    assertThat(testAnimal.getFechaCompra()).isEqualTo(UPDATED_FECHA_COMPRA);
    assertThat(testAnimal.getSexo()).isEqualTo(DEFAULT_SEXO);
    assertThat(testAnimal.getCastrado()).isEqualTo(UPDATED_CASTRADO);
    assertThat(testAnimal.getFechaCastracion()).isEqualTo(UPDATED_FECHA_CASTRACION);
    assertThat(testAnimal.getEstado()).isEqualTo(DEFAULT_ESTADO);
  }

  @Test
  @Transactional
  void fullUpdateAnimalWithPatch() throws Exception {
    // Initialize the database
    animalRepository.saveAndFlush(animal);

    int databaseSizeBeforeUpdate = animalRepository.findAll().size();

    // Update the animal using partial update
    Animal partialUpdatedAnimal = new Animal();
    partialUpdatedAnimal.setId(animal.getId());

    partialUpdatedAnimal
      .nombre(UPDATED_NOMBRE)
      .caracterizacion(UPDATED_CARACTERIZACION)
      .hierro(UPDATED_HIERRO)
      .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
      .fechaCompra(UPDATED_FECHA_COMPRA)
      .sexo(UPDATED_SEXO)
      .castrado(UPDATED_CASTRADO)
      .fechaCastracion(UPDATED_FECHA_CASTRACION)
      .estado(UPDATED_ESTADO);

    restAnimalMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedAnimal.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnimal))
      )
      .andExpect(status().isOk());

    // Validate the Animal in the database
    List<Animal> animalList = animalRepository.findAll();
    assertThat(animalList).hasSize(databaseSizeBeforeUpdate);
    Animal testAnimal = animalList.get(animalList.size() - 1);
    assertThat(testAnimal.getNombre()).isEqualTo(UPDATED_NOMBRE);
    assertThat(testAnimal.getCaracterizacion()).isEqualTo(UPDATED_CARACTERIZACION);
    assertThat(testAnimal.getHierro()).isEqualTo(UPDATED_HIERRO);
    assertThat(testAnimal.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
    assertThat(testAnimal.getFechaCompra()).isEqualTo(UPDATED_FECHA_COMPRA);
    assertThat(testAnimal.getSexo()).isEqualTo(UPDATED_SEXO);
    assertThat(testAnimal.getCastrado()).isEqualTo(UPDATED_CASTRADO);
    assertThat(testAnimal.getFechaCastracion()).isEqualTo(UPDATED_FECHA_CASTRACION);
    assertThat(testAnimal.getEstado()).isEqualTo(UPDATED_ESTADO);
  }

  @Test
  @Transactional
  void patchNonExistingAnimal() throws Exception {
    int databaseSizeBeforeUpdate = animalRepository.findAll().size();
    animal.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restAnimalMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, animal.getId())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animal))
      )
      .andExpect(status().isBadRequest());

    // Validate the Animal in the database
    List<Animal> animalList = animalRepository.findAll();
    assertThat(animalList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchAnimal() throws Exception {
    int databaseSizeBeforeUpdate = animalRepository.findAll().size();
    animal.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .with(csrf())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(animal))
      )
      .andExpect(status().isBadRequest());

    // Validate the Animal in the database
    List<Animal> animalList = animalRepository.findAll();
    assertThat(animalList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamAnimal() throws Exception {
    int databaseSizeBeforeUpdate = animalRepository.findAll().size();
    animal.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAnimalMockMvc
      .perform(
        patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(animal))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the Animal in the database
    List<Animal> animalList = animalRepository.findAll();
    assertThat(animalList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteAnimal() throws Exception {
    // Initialize the database
    animalRepository.saveAndFlush(animal);

    int databaseSizeBeforeDelete = animalRepository.findAll().size();

    // Delete the animal
    restAnimalMockMvc
      .perform(delete(ENTITY_API_URL_ID, animal.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<Animal> animalList = animalRepository.findAll();
    assertThat(animalList).hasSize(databaseSizeBeforeDelete - 1);
  }
}

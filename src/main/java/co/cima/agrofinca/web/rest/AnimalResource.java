package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.domain.Animal;
import co.cima.agrofinca.repository.AnimalRepository;
import co.cima.agrofinca.service.AnimalService;
import co.cima.agrofinca.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.cima.agrofinca.domain.Animal}.
 */
@RestController
@RequestMapping("/api")
public class AnimalResource {

  private final Logger log = LoggerFactory.getLogger(AnimalResource.class);

  private static final String ENTITY_NAME = "animal";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final AnimalService animalService;

  private final AnimalRepository animalRepository;

  public AnimalResource(AnimalService animalService, AnimalRepository animalRepository) {
    this.animalService = animalService;
    this.animalRepository = animalRepository;
  }

  /**
   * {@code POST  /animals} : Create a new animal.
   *
   * @param animal the animal to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new animal, or with status {@code 400 (Bad Request)} if the animal has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/animals")
  public ResponseEntity<Animal> createAnimal(@Valid @RequestBody Animal animal) throws URISyntaxException {
    log.debug("REST request to save Animal : {}", animal);
    if (animal.getId() != null) {
      throw new BadRequestAlertException("A new animal cannot already have an ID", ENTITY_NAME, "idexists");
    }
    Animal result = animalService.save(animal);
    return ResponseEntity
      .created(new URI("/api/animals/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /animals/:id} : Updates an existing animal.
   *
   * @param id the id of the animal to save.
   * @param animal the animal to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animal,
   * or with status {@code 400 (Bad Request)} if the animal is not valid,
   * or with status {@code 500 (Internal Server Error)} if the animal couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/animals/{id}")
  public ResponseEntity<Animal> updateAnimal(
    @PathVariable(value = "id", required = false) final Long id,
    @Valid @RequestBody Animal animal
  ) throws URISyntaxException {
    log.debug("REST request to update Animal : {}, {}", id, animal);
    if (animal.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, animal.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!animalRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Animal result = animalService.save(animal);
    return ResponseEntity
      .ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animal.getId().toString()))
      .body(result);
  }

  /**
   * {@code PATCH  /animals/:id} : Partial updates given fields of an existing animal, field will ignore if it is null
   *
   * @param id the id of the animal to save.
   * @param animal the animal to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animal,
   * or with status {@code 400 (Bad Request)} if the animal is not valid,
   * or with status {@code 404 (Not Found)} if the animal is not found,
   * or with status {@code 500 (Internal Server Error)} if the animal couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/animals/{id}", consumes = "application/merge-patch+json")
  public ResponseEntity<Animal> partialUpdateAnimal(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody Animal animal
  ) throws URISyntaxException {
    log.debug("REST request to partial update Animal partially : {}, {}", id, animal);
    if (animal.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, animal.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!animalRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<Animal> result = animalService.partialUpdate(animal);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animal.getId().toString())
    );
  }

  /**
   * {@code GET  /animals} : get all the animals.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of animals in body.
   */
  @GetMapping("/animals")
  public ResponseEntity<List<Animal>> getAllAnimals(Pageable pageable) {
    log.debug("REST request to get a page of Animals");
    Page<Animal> page = animalService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /animals/:id} : get the "id" animal.
   *
   * @param id the id of the animal to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the animal, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/animals/{id}")
  public ResponseEntity<Animal> getAnimal(@PathVariable Long id) {
    log.debug("REST request to get Animal : {}", id);
    Optional<Animal> animal = animalService.findOne(id);
    return ResponseUtil.wrapOrNotFound(animal);
  }

  /**
   * {@code DELETE  /animals/:id} : delete the "id" animal.
   *
   * @param id the id of the animal to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/animals/{id}")
  public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
    log.debug("REST request to delete Animal : {}", id);
    animalService.delete(id);
    return ResponseEntity
      .noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}

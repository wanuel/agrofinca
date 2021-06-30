package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.domain.AnimalPeso;
import co.cima.agrofinca.repository.AnimalPesoRepository;
import co.cima.agrofinca.service.AnimalPesoService;
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
 * REST controller for managing {@link co.cima.agrofinca.domain.AnimalPeso}.
 */
@RestController
@RequestMapping("/api")
public class AnimalPesoResource {

  private final Logger log = LoggerFactory.getLogger(AnimalPesoResource.class);

  private static final String ENTITY_NAME = "animalPeso";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final AnimalPesoService animalPesoService;

  private final AnimalPesoRepository animalPesoRepository;

  public AnimalPesoResource(AnimalPesoService animalPesoService, AnimalPesoRepository animalPesoRepository) {
    this.animalPesoService = animalPesoService;
    this.animalPesoRepository = animalPesoRepository;
  }

  /**
   * {@code POST  /animal-pesos} : Create a new animalPeso.
   *
   * @param animalPeso the animalPeso to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new animalPeso, or with status {@code 400 (Bad Request)} if the animalPeso has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/animal-pesos")
  public ResponseEntity<AnimalPeso> createAnimalPeso(@Valid @RequestBody AnimalPeso animalPeso) throws URISyntaxException {
    log.debug("REST request to save AnimalPeso : {}", animalPeso);
    if (animalPeso.getId() != null) {
      throw new BadRequestAlertException("A new animalPeso cannot already have an ID", ENTITY_NAME, "idexists");
    }
    AnimalPeso result = animalPesoService.save(animalPeso);
    return ResponseEntity
      .created(new URI("/api/animal-pesos/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /animal-pesos/:id} : Updates an existing animalPeso.
   *
   * @param id the id of the animalPeso to save.
   * @param animalPeso the animalPeso to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animalPeso,
   * or with status {@code 400 (Bad Request)} if the animalPeso is not valid,
   * or with status {@code 500 (Internal Server Error)} if the animalPeso couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/animal-pesos/{id}")
  public ResponseEntity<AnimalPeso> updateAnimalPeso(
    @PathVariable(value = "id", required = false) final Long id,
    @Valid @RequestBody AnimalPeso animalPeso
  ) throws URISyntaxException {
    log.debug("REST request to update AnimalPeso : {}, {}", id, animalPeso);
    if (animalPeso.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, animalPeso.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!animalPesoRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    AnimalPeso result = animalPesoService.save(animalPeso);
    return ResponseEntity
      .ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animalPeso.getId().toString()))
      .body(result);
  }

  /**
   * {@code PATCH  /animal-pesos/:id} : Partial updates given fields of an existing animalPeso, field will ignore if it is null
   *
   * @param id the id of the animalPeso to save.
   * @param animalPeso the animalPeso to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animalPeso,
   * or with status {@code 400 (Bad Request)} if the animalPeso is not valid,
   * or with status {@code 404 (Not Found)} if the animalPeso is not found,
   * or with status {@code 500 (Internal Server Error)} if the animalPeso couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/animal-pesos/{id}", consumes = "application/merge-patch+json")
  public ResponseEntity<AnimalPeso> partialUpdateAnimalPeso(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody AnimalPeso animalPeso
  ) throws URISyntaxException {
    log.debug("REST request to partial update AnimalPeso partially : {}, {}", id, animalPeso);
    if (animalPeso.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, animalPeso.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!animalPesoRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<AnimalPeso> result = animalPesoService.partialUpdate(animalPeso);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animalPeso.getId().toString())
    );
  }

  /**
   * {@code GET  /animal-pesos} : get all the animalPesos.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of animalPesos in body.
   */
  @GetMapping("/animal-pesos")
  public ResponseEntity<List<AnimalPeso>> getAllAnimalPesos(Pageable pageable) {
    log.debug("REST request to get a page of AnimalPesos");
    Page<AnimalPeso> page = animalPesoService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /animal-pesos/:id} : get the "id" animalPeso.
   *
   * @param id the id of the animalPeso to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the animalPeso, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/animal-pesos/{id}")
  public ResponseEntity<AnimalPeso> getAnimalPeso(@PathVariable Long id) {
    log.debug("REST request to get AnimalPeso : {}", id);
    Optional<AnimalPeso> animalPeso = animalPesoService.findOne(id);
    return ResponseUtil.wrapOrNotFound(animalPeso);
  }

  /**
   * {@code DELETE  /animal-pesos/:id} : delete the "id" animalPeso.
   *
   * @param id the id of the animalPeso to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/animal-pesos/{id}")
  public ResponseEntity<Void> deleteAnimalPeso(@PathVariable Long id) {
    log.debug("REST request to delete AnimalPeso : {}", id);
    animalPesoService.delete(id);
    return ResponseEntity
      .noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}

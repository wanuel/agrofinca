package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.domain.AnimalCostos;
import co.cima.agrofinca.repository.AnimalCostosRepository;
import co.cima.agrofinca.service.AnimalCostosService;
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
 * REST controller for managing {@link co.cima.agrofinca.domain.AnimalCostos}.
 */
@RestController
@RequestMapping("/api")
public class AnimalCostosResource {

  private final Logger log = LoggerFactory.getLogger(AnimalCostosResource.class);

  private static final String ENTITY_NAME = "animalCostos";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final AnimalCostosService animalCostosService;

  private final AnimalCostosRepository animalCostosRepository;

  public AnimalCostosResource(AnimalCostosService animalCostosService, AnimalCostosRepository animalCostosRepository) {
    this.animalCostosService = animalCostosService;
    this.animalCostosRepository = animalCostosRepository;
  }

  /**
   * {@code POST  /animal-costos} : Create a new animalCostos.
   *
   * @param animalCostos the animalCostos to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new animalCostos, or with status {@code 400 (Bad Request)} if the animalCostos has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/animal-costos")
  public ResponseEntity<AnimalCostos> createAnimalCostos(@Valid @RequestBody AnimalCostos animalCostos) throws URISyntaxException {
    log.debug("REST request to save AnimalCostos : {}", animalCostos);
    if (animalCostos.getId() != null) {
      throw new BadRequestAlertException("A new animalCostos cannot already have an ID", ENTITY_NAME, "idexists");
    }
    AnimalCostos result = animalCostosService.save(animalCostos);
    return ResponseEntity
      .created(new URI("/api/animal-costos/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /animal-costos/:id} : Updates an existing animalCostos.
   *
   * @param id the id of the animalCostos to save.
   * @param animalCostos the animalCostos to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animalCostos,
   * or with status {@code 400 (Bad Request)} if the animalCostos is not valid,
   * or with status {@code 500 (Internal Server Error)} if the animalCostos couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/animal-costos/{id}")
  public ResponseEntity<AnimalCostos> updateAnimalCostos(
    @PathVariable(value = "id", required = false) final Long id,
    @Valid @RequestBody AnimalCostos animalCostos
  ) throws URISyntaxException {
    log.debug("REST request to update AnimalCostos : {}, {}", id, animalCostos);
    if (animalCostos.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, animalCostos.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!animalCostosRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    AnimalCostos result = animalCostosService.save(animalCostos);
    return ResponseEntity
      .ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animalCostos.getId().toString()))
      .body(result);
  }

  /**
   * {@code PATCH  /animal-costos/:id} : Partial updates given fields of an existing animalCostos, field will ignore if it is null
   *
   * @param id the id of the animalCostos to save.
   * @param animalCostos the animalCostos to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animalCostos,
   * or with status {@code 400 (Bad Request)} if the animalCostos is not valid,
   * or with status {@code 404 (Not Found)} if the animalCostos is not found,
   * or with status {@code 500 (Internal Server Error)} if the animalCostos couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/animal-costos/{id}", consumes = "application/merge-patch+json")
  public ResponseEntity<AnimalCostos> partialUpdateAnimalCostos(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody AnimalCostos animalCostos
  ) throws URISyntaxException {
    log.debug("REST request to partial update AnimalCostos partially : {}, {}", id, animalCostos);
    if (animalCostos.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, animalCostos.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!animalCostosRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<AnimalCostos> result = animalCostosService.partialUpdate(animalCostos);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animalCostos.getId().toString())
    );
  }

  /**
   * {@code GET  /animal-costos} : get all the animalCostos.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of animalCostos in body.
   */
  @GetMapping("/animal-costos")
  public ResponseEntity<List<AnimalCostos>> getAllAnimalCostos(Pageable pageable) {
    log.debug("REST request to get a page of AnimalCostos");
    Page<AnimalCostos> page = animalCostosService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /animal-costos/:id} : get the "id" animalCostos.
   *
   * @param id the id of the animalCostos to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the animalCostos, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/animal-costos/{id}")
  public ResponseEntity<AnimalCostos> getAnimalCostos(@PathVariable Long id) {
    log.debug("REST request to get AnimalCostos : {}", id);
    Optional<AnimalCostos> animalCostos = animalCostosService.findOne(id);
    return ResponseUtil.wrapOrNotFound(animalCostos);
  }

  /**
   * {@code DELETE  /animal-costos/:id} : delete the "id" animalCostos.
   *
   * @param id the id of the animalCostos to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/animal-costos/{id}")
  public ResponseEntity<Void> deleteAnimalCostos(@PathVariable Long id) {
    log.debug("REST request to delete AnimalCostos : {}", id);
    animalCostosService.delete(id);
    return ResponseEntity
      .noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}

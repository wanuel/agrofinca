package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.domain.PotreroActividadAnimal;
import co.cima.agrofinca.repository.PotreroActividadAnimalRepository;
import co.cima.agrofinca.service.PotreroActividadAnimalService;
import co.cima.agrofinca.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link co.cima.agrofinca.domain.PotreroActividadAnimal}.
 */
@RestController
@RequestMapping("/api")
public class PotreroActividadAnimalResource {

  private final Logger log = LoggerFactory.getLogger(PotreroActividadAnimalResource.class);

  private static final String ENTITY_NAME = "potreroActividadAnimal";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final PotreroActividadAnimalService potreroActividadAnimalService;

  private final PotreroActividadAnimalRepository potreroActividadAnimalRepository;

  public PotreroActividadAnimalResource(
    PotreroActividadAnimalService potreroActividadAnimalService,
    PotreroActividadAnimalRepository potreroActividadAnimalRepository
  ) {
    this.potreroActividadAnimalService = potreroActividadAnimalService;
    this.potreroActividadAnimalRepository = potreroActividadAnimalRepository;
  }

  /**
   * {@code POST  /potrero-actividad-animals} : Create a new potreroActividadAnimal.
   *
   * @param potreroActividadAnimal the potreroActividadAnimal to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new potreroActividadAnimal, or with status {@code 400 (Bad Request)} if the potreroActividadAnimal has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/potrero-actividad-animals")
  public ResponseEntity<PotreroActividadAnimal> createPotreroActividadAnimal(@RequestBody PotreroActividadAnimal potreroActividadAnimal)
    throws URISyntaxException {
    log.debug("REST request to save PotreroActividadAnimal : {}", potreroActividadAnimal);
    if (potreroActividadAnimal.getId() != null) {
      throw new BadRequestAlertException("A new potreroActividadAnimal cannot already have an ID", ENTITY_NAME, "idexists");
    }
    PotreroActividadAnimal result = potreroActividadAnimalService.save(potreroActividadAnimal);
    return ResponseEntity
      .created(new URI("/api/potrero-actividad-animals/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /potrero-actividad-animals/:id} : Updates an existing potreroActividadAnimal.
   *
   * @param id the id of the potreroActividadAnimal to save.
   * @param potreroActividadAnimal the potreroActividadAnimal to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated potreroActividadAnimal,
   * or with status {@code 400 (Bad Request)} if the potreroActividadAnimal is not valid,
   * or with status {@code 500 (Internal Server Error)} if the potreroActividadAnimal couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/potrero-actividad-animals/{id}")
  public ResponseEntity<PotreroActividadAnimal> updatePotreroActividadAnimal(
    @PathVariable(value = "id", required = false) final Long id,
    @RequestBody PotreroActividadAnimal potreroActividadAnimal
  ) throws URISyntaxException {
    log.debug("REST request to update PotreroActividadAnimal : {}, {}", id, potreroActividadAnimal);
    if (potreroActividadAnimal.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, potreroActividadAnimal.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!potreroActividadAnimalRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    PotreroActividadAnimal result = potreroActividadAnimalService.save(potreroActividadAnimal);
    return ResponseEntity
      .ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, potreroActividadAnimal.getId().toString()))
      .body(result);
  }

  /**
   * {@code PATCH  /potrero-actividad-animals/:id} : Partial updates given fields of an existing potreroActividadAnimal, field will ignore if it is null
   *
   * @param id the id of the potreroActividadAnimal to save.
   * @param potreroActividadAnimal the potreroActividadAnimal to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated potreroActividadAnimal,
   * or with status {@code 400 (Bad Request)} if the potreroActividadAnimal is not valid,
   * or with status {@code 404 (Not Found)} if the potreroActividadAnimal is not found,
   * or with status {@code 500 (Internal Server Error)} if the potreroActividadAnimal couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/potrero-actividad-animals/{id}", consumes = "application/merge-patch+json")
  public ResponseEntity<PotreroActividadAnimal> partialUpdatePotreroActividadAnimal(
    @PathVariable(value = "id", required = false) final Long id,
    @RequestBody PotreroActividadAnimal potreroActividadAnimal
  ) throws URISyntaxException {
    log.debug("REST request to partial update PotreroActividadAnimal partially : {}, {}", id, potreroActividadAnimal);
    if (potreroActividadAnimal.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, potreroActividadAnimal.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!potreroActividadAnimalRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<PotreroActividadAnimal> result = potreroActividadAnimalService.partialUpdate(potreroActividadAnimal);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, potreroActividadAnimal.getId().toString())
    );
  }

  /**
   * {@code GET  /potrero-actividad-animals} : get all the potreroActividadAnimals.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of potreroActividadAnimals in body.
   */
  @GetMapping("/potrero-actividad-animals")
  public ResponseEntity<List<PotreroActividadAnimal>> getAllPotreroActividadAnimals(Pageable pageable) {
    log.debug("REST request to get a page of PotreroActividadAnimals");
    Page<PotreroActividadAnimal> page = potreroActividadAnimalService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /potrero-actividad-animals/:id} : get the "id" potreroActividadAnimal.
   *
   * @param id the id of the potreroActividadAnimal to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the potreroActividadAnimal, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/potrero-actividad-animals/{id}")
  public ResponseEntity<PotreroActividadAnimal> getPotreroActividadAnimal(@PathVariable Long id) {
    log.debug("REST request to get PotreroActividadAnimal : {}", id);
    Optional<PotreroActividadAnimal> potreroActividadAnimal = potreroActividadAnimalService.findOne(id);
    return ResponseUtil.wrapOrNotFound(potreroActividadAnimal);
  }

  /**
   * {@code DELETE  /potrero-actividad-animals/:id} : delete the "id" potreroActividadAnimal.
   *
   * @param id the id of the potreroActividadAnimal to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/potrero-actividad-animals/{id}")
  public ResponseEntity<Void> deletePotreroActividadAnimal(@PathVariable Long id) {
    log.debug("REST request to delete PotreroActividadAnimal : {}", id);
    potreroActividadAnimalService.delete(id);
    return ResponseEntity
      .noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}

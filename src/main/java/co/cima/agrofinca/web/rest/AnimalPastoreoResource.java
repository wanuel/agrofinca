package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.domain.AnimalPastoreo;
import co.cima.agrofinca.repository.AnimalPastoreoRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.cima.agrofinca.domain.AnimalPastoreo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AnimalPastoreoResource {

  private final Logger log = LoggerFactory.getLogger(AnimalPastoreoResource.class);

  private static final String ENTITY_NAME = "animalPastoreo";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final AnimalPastoreoRepository animalPastoreoRepository;

  public AnimalPastoreoResource(AnimalPastoreoRepository animalPastoreoRepository) {
    this.animalPastoreoRepository = animalPastoreoRepository;
  }

  /**
   * {@code POST  /animal-pastoreos} : Create a new animalPastoreo.
   *
   * @param animalPastoreo the animalPastoreo to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new animalPastoreo, or with status {@code 400 (Bad Request)} if the animalPastoreo has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/animal-pastoreos")
  public ResponseEntity<AnimalPastoreo> createAnimalPastoreo(@RequestBody AnimalPastoreo animalPastoreo) throws URISyntaxException {
    log.debug("REST request to save AnimalPastoreo : {}", animalPastoreo);
    if (animalPastoreo.getId() != null) {
      throw new BadRequestAlertException("A new animalPastoreo cannot already have an ID", ENTITY_NAME, "idexists");
    }
    AnimalPastoreo result = animalPastoreoRepository.save(animalPastoreo);
    return ResponseEntity
      .created(new URI("/api/animal-pastoreos/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /animal-pastoreos/:id} : Updates an existing animalPastoreo.
   *
   * @param id the id of the animalPastoreo to save.
   * @param animalPastoreo the animalPastoreo to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animalPastoreo,
   * or with status {@code 400 (Bad Request)} if the animalPastoreo is not valid,
   * or with status {@code 500 (Internal Server Error)} if the animalPastoreo couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/animal-pastoreos/{id}")
  public ResponseEntity<AnimalPastoreo> updateAnimalPastoreo(
    @PathVariable(value = "id", required = false) final Long id,
    @RequestBody AnimalPastoreo animalPastoreo
  ) throws URISyntaxException {
    log.debug("REST request to update AnimalPastoreo : {}, {}", id, animalPastoreo);
    if (animalPastoreo.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, animalPastoreo.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!animalPastoreoRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    AnimalPastoreo result = animalPastoreoRepository.save(animalPastoreo);
    return ResponseEntity
      .ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animalPastoreo.getId().toString()))
      .body(result);
  }

  /**
   * {@code PATCH  /animal-pastoreos/:id} : Partial updates given fields of an existing animalPastoreo, field will ignore if it is null
   *
   * @param id the id of the animalPastoreo to save.
   * @param animalPastoreo the animalPastoreo to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animalPastoreo,
   * or with status {@code 400 (Bad Request)} if the animalPastoreo is not valid,
   * or with status {@code 404 (Not Found)} if the animalPastoreo is not found,
   * or with status {@code 500 (Internal Server Error)} if the animalPastoreo couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/animal-pastoreos/{id}", consumes = "application/merge-patch+json")
  public ResponseEntity<AnimalPastoreo> partialUpdateAnimalPastoreo(
    @PathVariable(value = "id", required = false) final Long id,
    @RequestBody AnimalPastoreo animalPastoreo
  ) throws URISyntaxException {
    log.debug("REST request to partial update AnimalPastoreo partially : {}, {}", id, animalPastoreo);
    if (animalPastoreo.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, animalPastoreo.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!animalPastoreoRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<AnimalPastoreo> result = animalPastoreoRepository
      .findById(animalPastoreo.getId())
      .map(
        existingAnimalPastoreo -> {
          return existingAnimalPastoreo;
        }
      )
      .map(animalPastoreoRepository::save);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animalPastoreo.getId().toString())
    );
  }

  /**
   * {@code GET  /animal-pastoreos} : get all the animalPastoreos.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of animalPastoreos in body.
   */
  @GetMapping("/animal-pastoreos")
  public ResponseEntity<List<AnimalPastoreo>> getAllAnimalPastoreos(Pageable pageable) {
    log.debug("REST request to get a page of AnimalPastoreos");
    Page<AnimalPastoreo> page = animalPastoreoRepository.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /animal-pastoreos/:id} : get the "id" animalPastoreo.
   *
   * @param id the id of the animalPastoreo to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the animalPastoreo, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/animal-pastoreos/{id}")
  public ResponseEntity<AnimalPastoreo> getAnimalPastoreo(@PathVariable Long id) {
    log.debug("REST request to get AnimalPastoreo : {}", id);
    Optional<AnimalPastoreo> animalPastoreo = animalPastoreoRepository.findById(id);
    return ResponseUtil.wrapOrNotFound(animalPastoreo);
  }

  /**
   * {@code DELETE  /animal-pastoreos/:id} : delete the "id" animalPastoreo.
   *
   * @param id the id of the animalPastoreo to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/animal-pastoreos/{id}")
  public ResponseEntity<Void> deleteAnimalPastoreo(@PathVariable Long id) {
    log.debug("REST request to delete AnimalPastoreo : {}", id);
    animalPastoreoRepository.deleteById(id);
    return ResponseEntity
      .noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}

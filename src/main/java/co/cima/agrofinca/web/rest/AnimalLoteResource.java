package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.domain.AnimalLote;
import co.cima.agrofinca.repository.AnimalLoteRepository;
import co.cima.agrofinca.service.AnimalLoteService;
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
 * REST controller for managing {@link co.cima.agrofinca.domain.AnimalLote}.
 */
@RestController
@RequestMapping("/api")
public class AnimalLoteResource {

  private final Logger log = LoggerFactory.getLogger(AnimalLoteResource.class);

  private static final String ENTITY_NAME = "animalLote";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final AnimalLoteService animalLoteService;

  private final AnimalLoteRepository animalLoteRepository;

  public AnimalLoteResource(AnimalLoteService animalLoteService, AnimalLoteRepository animalLoteRepository) {
    this.animalLoteService = animalLoteService;
    this.animalLoteRepository = animalLoteRepository;
  }

  /**
   * {@code POST  /animal-lotes} : Create a new animalLote.
   *
   * @param animalLote the animalLote to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new animalLote, or with status {@code 400 (Bad Request)} if the animalLote has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/animal-lotes")
  public ResponseEntity<AnimalLote> createAnimalLote(@Valid @RequestBody AnimalLote animalLote) throws URISyntaxException {
    log.debug("REST request to save AnimalLote : {}", animalLote);
    if (animalLote.getId() != null) {
      throw new BadRequestAlertException("A new animalLote cannot already have an ID", ENTITY_NAME, "idexists");
    }
    AnimalLote result = animalLoteService.save(animalLote);
    return ResponseEntity
      .created(new URI("/api/animal-lotes/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /animal-lotes/:id} : Updates an existing animalLote.
   *
   * @param id the id of the animalLote to save.
   * @param animalLote the animalLote to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animalLote,
   * or with status {@code 400 (Bad Request)} if the animalLote is not valid,
   * or with status {@code 500 (Internal Server Error)} if the animalLote couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/animal-lotes/{id}")
  public ResponseEntity<AnimalLote> updateAnimalLote(
    @PathVariable(value = "id", required = false) final Long id,
    @Valid @RequestBody AnimalLote animalLote
  ) throws URISyntaxException {
    log.debug("REST request to update AnimalLote : {}, {}", id, animalLote);
    if (animalLote.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, animalLote.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!animalLoteRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    AnimalLote result = animalLoteService.save(animalLote);
    return ResponseEntity
      .ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animalLote.getId().toString()))
      .body(result);
  }

  /**
   * {@code PATCH  /animal-lotes/:id} : Partial updates given fields of an existing animalLote, field will ignore if it is null
   *
   * @param id the id of the animalLote to save.
   * @param animalLote the animalLote to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animalLote,
   * or with status {@code 400 (Bad Request)} if the animalLote is not valid,
   * or with status {@code 404 (Not Found)} if the animalLote is not found,
   * or with status {@code 500 (Internal Server Error)} if the animalLote couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/animal-lotes/{id}", consumes = "application/merge-patch+json")
  public ResponseEntity<AnimalLote> partialUpdateAnimalLote(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody AnimalLote animalLote
  ) throws URISyntaxException {
    log.debug("REST request to partial update AnimalLote partially : {}, {}", id, animalLote);
    if (animalLote.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, animalLote.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!animalLoteRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<AnimalLote> result = animalLoteService.partialUpdate(animalLote);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animalLote.getId().toString())
    );
  }

  /**
   * {@code GET  /animal-lotes} : get all the animalLotes.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of animalLotes in body.
   */
  @GetMapping("/animal-lotes")
  public ResponseEntity<List<AnimalLote>> getAllAnimalLotes(Pageable pageable) {
    log.debug("REST request to get a page of AnimalLotes");
    Page<AnimalLote> page = animalLoteService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /animal-lotes/:id} : get the "id" animalLote.
   *
   * @param id the id of the animalLote to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the animalLote, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/animal-lotes/{id}")
  public ResponseEntity<AnimalLote> getAnimalLote(@PathVariable Long id) {
    log.debug("REST request to get AnimalLote : {}", id);
    Optional<AnimalLote> animalLote = animalLoteService.findOne(id);
    return ResponseUtil.wrapOrNotFound(animalLote);
  }

  /**
   * {@code DELETE  /animal-lotes/:id} : delete the "id" animalLote.
   *
   * @param id the id of the animalLote to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/animal-lotes/{id}")
  public ResponseEntity<Void> deleteAnimalLote(@PathVariable Long id) {
    log.debug("REST request to delete AnimalLote : {}", id);
    animalLoteService.delete(id);
    return ResponseEntity
      .noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}

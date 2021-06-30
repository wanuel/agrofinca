package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.domain.Potrero;
import co.cima.agrofinca.repository.PotreroRepository;
import co.cima.agrofinca.service.PotreroService;
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
 * REST controller for managing {@link co.cima.agrofinca.domain.Potrero}.
 */
@RestController
@RequestMapping("/api")
public class PotreroResource {

  private final Logger log = LoggerFactory.getLogger(PotreroResource.class);

  private static final String ENTITY_NAME = "potrero";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final PotreroService potreroService;

  private final PotreroRepository potreroRepository;

  public PotreroResource(PotreroService potreroService, PotreroRepository potreroRepository) {
    this.potreroService = potreroService;
    this.potreroRepository = potreroRepository;
  }

  /**
   * {@code POST  /potreros} : Create a new potrero.
   *
   * @param potrero the potrero to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new potrero, or with status {@code 400 (Bad Request)} if the potrero has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/potreros")
  public ResponseEntity<Potrero> createPotrero(@Valid @RequestBody Potrero potrero) throws URISyntaxException {
    log.debug("REST request to save Potrero : {}", potrero);
    if (potrero.getId() != null) {
      throw new BadRequestAlertException("A new potrero cannot already have an ID", ENTITY_NAME, "idexists");
    }
    Potrero result = potreroService.save(potrero);
    return ResponseEntity
      .created(new URI("/api/potreros/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /potreros/:id} : Updates an existing potrero.
   *
   * @param id the id of the potrero to save.
   * @param potrero the potrero to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated potrero,
   * or with status {@code 400 (Bad Request)} if the potrero is not valid,
   * or with status {@code 500 (Internal Server Error)} if the potrero couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/potreros/{id}")
  public ResponseEntity<Potrero> updatePotrero(
    @PathVariable(value = "id", required = false) final Long id,
    @Valid @RequestBody Potrero potrero
  ) throws URISyntaxException {
    log.debug("REST request to update Potrero : {}, {}", id, potrero);
    if (potrero.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, potrero.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!potreroRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Potrero result = potreroService.save(potrero);
    return ResponseEntity
      .ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, potrero.getId().toString()))
      .body(result);
  }

  /**
   * {@code PATCH  /potreros/:id} : Partial updates given fields of an existing potrero, field will ignore if it is null
   *
   * @param id the id of the potrero to save.
   * @param potrero the potrero to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated potrero,
   * or with status {@code 400 (Bad Request)} if the potrero is not valid,
   * or with status {@code 404 (Not Found)} if the potrero is not found,
   * or with status {@code 500 (Internal Server Error)} if the potrero couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/potreros/{id}", consumes = "application/merge-patch+json")
  public ResponseEntity<Potrero> partialUpdatePotrero(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody Potrero potrero
  ) throws URISyntaxException {
    log.debug("REST request to partial update Potrero partially : {}, {}", id, potrero);
    if (potrero.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, potrero.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!potreroRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<Potrero> result = potreroService.partialUpdate(potrero);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, potrero.getId().toString())
    );
  }

  /**
   * {@code GET  /potreros} : get all the potreros.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of potreros in body.
   */
  @GetMapping("/potreros")
  public ResponseEntity<List<Potrero>> getAllPotreros(Pageable pageable) {
    log.debug("REST request to get a page of Potreros");
    Page<Potrero> page = potreroService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /potreros/:id} : get the "id" potrero.
   *
   * @param id the id of the potrero to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the potrero, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/potreros/{id}")
  public ResponseEntity<Potrero> getPotrero(@PathVariable Long id) {
    log.debug("REST request to get Potrero : {}", id);
    Optional<Potrero> potrero = potreroService.findOne(id);
    return ResponseUtil.wrapOrNotFound(potrero);
  }

  /**
   * {@code DELETE  /potreros/:id} : delete the "id" potrero.
   *
   * @param id the id of the potrero to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/potreros/{id}")
  public ResponseEntity<Void> deletePotrero(@PathVariable Long id) {
    log.debug("REST request to delete Potrero : {}", id);
    potreroService.delete(id);
    return ResponseEntity
      .noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}

package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.domain.PotreroActividad;
import co.cima.agrofinca.repository.PotreroActividadRepository;
import co.cima.agrofinca.service.PotreroActividadService;
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
 * REST controller for managing {@link co.cima.agrofinca.domain.PotreroActividad}.
 */
@RestController
@RequestMapping("/api")
public class PotreroActividadResource {

  private final Logger log = LoggerFactory.getLogger(PotreroActividadResource.class);

  private static final String ENTITY_NAME = "potreroActividad";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final PotreroActividadService potreroActividadService;

  private final PotreroActividadRepository potreroActividadRepository;

  public PotreroActividadResource(PotreroActividadService potreroActividadService, PotreroActividadRepository potreroActividadRepository) {
    this.potreroActividadService = potreroActividadService;
    this.potreroActividadRepository = potreroActividadRepository;
  }

  /**
   * {@code POST  /potrero-actividads} : Create a new potreroActividad.
   *
   * @param potreroActividad the potreroActividad to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new potreroActividad, or with status {@code 400 (Bad Request)} if the potreroActividad has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/potrero-actividads")
  public ResponseEntity<PotreroActividad> createPotreroActividad(@Valid @RequestBody PotreroActividad potreroActividad)
    throws URISyntaxException {
    log.debug("REST request to save PotreroActividad : {}", potreroActividad);
    if (potreroActividad.getId() != null) {
      throw new BadRequestAlertException("A new potreroActividad cannot already have an ID", ENTITY_NAME, "idexists");
    }
    PotreroActividad result = potreroActividadService.save(potreroActividad);
    return ResponseEntity
      .created(new URI("/api/potrero-actividads/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /potrero-actividads/:id} : Updates an existing potreroActividad.
   *
   * @param id the id of the potreroActividad to save.
   * @param potreroActividad the potreroActividad to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated potreroActividad,
   * or with status {@code 400 (Bad Request)} if the potreroActividad is not valid,
   * or with status {@code 500 (Internal Server Error)} if the potreroActividad couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/potrero-actividads/{id}")
  public ResponseEntity<PotreroActividad> updatePotreroActividad(
    @PathVariable(value = "id", required = false) final Long id,
    @Valid @RequestBody PotreroActividad potreroActividad
  ) throws URISyntaxException {
    log.debug("REST request to update PotreroActividad : {}, {}", id, potreroActividad);
    if (potreroActividad.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, potreroActividad.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!potreroActividadRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    PotreroActividad result = potreroActividadService.save(potreroActividad);
    return ResponseEntity
      .ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, potreroActividad.getId().toString()))
      .body(result);
  }

  /**
   * {@code PATCH  /potrero-actividads/:id} : Partial updates given fields of an existing potreroActividad, field will ignore if it is null
   *
   * @param id the id of the potreroActividad to save.
   * @param potreroActividad the potreroActividad to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated potreroActividad,
   * or with status {@code 400 (Bad Request)} if the potreroActividad is not valid,
   * or with status {@code 404 (Not Found)} if the potreroActividad is not found,
   * or with status {@code 500 (Internal Server Error)} if the potreroActividad couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/potrero-actividads/{id}", consumes = "application/merge-patch+json")
  public ResponseEntity<PotreroActividad> partialUpdatePotreroActividad(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody PotreroActividad potreroActividad
  ) throws URISyntaxException {
    log.debug("REST request to partial update PotreroActividad partially : {}, {}", id, potreroActividad);
    if (potreroActividad.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, potreroActividad.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!potreroActividadRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<PotreroActividad> result = potreroActividadService.partialUpdate(potreroActividad);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, potreroActividad.getId().toString())
    );
  }

  /**
   * {@code GET  /potrero-actividads} : get all the potreroActividads.
   *
   * @param pageable the pagination information.
   * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of potreroActividads in body.
   */
  @GetMapping("/potrero-actividads")
  public ResponseEntity<List<PotreroActividad>> getAllPotreroActividads(
    Pageable pageable,
    @RequestParam(required = false, defaultValue = "false") boolean eagerload
  ) {
    log.debug("REST request to get a page of PotreroActividads");
    Page<PotreroActividad> page;
    if (eagerload) {
      page = potreroActividadService.findAllWithEagerRelationships(pageable);
    } else {
      page = potreroActividadService.findAll(pageable);
    }
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /potrero-actividads/:id} : get the "id" potreroActividad.
   *
   * @param id the id of the potreroActividad to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the potreroActividad, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/potrero-actividads/{id}")
  public ResponseEntity<PotreroActividad> getPotreroActividad(@PathVariable Long id) {
    log.debug("REST request to get PotreroActividad : {}", id);
    Optional<PotreroActividad> potreroActividad = potreroActividadService.findOne(id);
    return ResponseUtil.wrapOrNotFound(potreroActividad);
  }

  /**
   * {@code DELETE  /potrero-actividads/:id} : delete the "id" potreroActividad.
   *
   * @param id the id of the potreroActividad to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/potrero-actividads/{id}")
  public ResponseEntity<Void> deletePotreroActividad(@PathVariable Long id) {
    log.debug("REST request to delete PotreroActividad : {}", id);
    potreroActividadService.delete(id);
    return ResponseEntity
      .noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}

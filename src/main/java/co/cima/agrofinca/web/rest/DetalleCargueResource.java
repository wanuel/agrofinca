package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.domain.DetalleCargue;
import co.cima.agrofinca.repository.DetalleCargueRepository;
import co.cima.agrofinca.service.DetalleCargueService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.cima.agrofinca.domain.DetalleCargue}.
 */
@RestController
@RequestMapping("/api")
public class DetalleCargueResource {

  private final Logger log = LoggerFactory.getLogger(DetalleCargueResource.class);

  private static final String ENTITY_NAME = "detalleCargue";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final DetalleCargueService detalleCargueService;

  private final DetalleCargueRepository detalleCargueRepository;

  public DetalleCargueResource(DetalleCargueService detalleCargueService, DetalleCargueRepository detalleCargueRepository) {
    this.detalleCargueService = detalleCargueService;
    this.detalleCargueRepository = detalleCargueRepository;
  }

  /**
   * {@code POST  /detalle-cargues} : Create a new detalleCargue.
   *
   * @param detalleCargue the detalleCargue to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detalleCargue, or with status {@code 400 (Bad Request)} if the detalleCargue has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/detalle-cargues")
  public ResponseEntity<DetalleCargue> createDetalleCargue(@Valid @RequestBody DetalleCargue detalleCargue) throws URISyntaxException {
    log.debug("REST request to save DetalleCargue : {}", detalleCargue);
    if (detalleCargue.getId() != null) {
      throw new BadRequestAlertException("A new detalleCargue cannot already have an ID", ENTITY_NAME, "idexists");
    }
    DetalleCargue result = detalleCargueService.save(detalleCargue);
    return ResponseEntity
      .created(new URI("/api/detalle-cargues/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /detalle-cargues/:id} : Updates an existing detalleCargue.
   *
   * @param id the id of the detalleCargue to save.
   * @param detalleCargue the detalleCargue to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detalleCargue,
   * or with status {@code 400 (Bad Request)} if the detalleCargue is not valid,
   * or with status {@code 500 (Internal Server Error)} if the detalleCargue couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/detalle-cargues/{id}")
  public ResponseEntity<DetalleCargue> updateDetalleCargue(
    @PathVariable(value = "id", required = false) final Long id,
    @Valid @RequestBody DetalleCargue detalleCargue
  ) throws URISyntaxException {
    log.debug("REST request to update DetalleCargue : {}, {}", id, detalleCargue);
    if (detalleCargue.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, detalleCargue.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!detalleCargueRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    DetalleCargue result = detalleCargueService.save(detalleCargue);
    return ResponseEntity
      .ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detalleCargue.getId().toString()))
      .body(result);
  }

  /**
   * {@code PATCH  /detalle-cargues/:id} : Partial updates given fields of an existing detalleCargue, field will ignore if it is null
   *
   * @param id the id of the detalleCargue to save.
   * @param detalleCargue the detalleCargue to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detalleCargue,
   * or with status {@code 400 (Bad Request)} if the detalleCargue is not valid,
   * or with status {@code 404 (Not Found)} if the detalleCargue is not found,
   * or with status {@code 500 (Internal Server Error)} if the detalleCargue couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/detalle-cargues/{id}", consumes = "application/merge-patch+json")
  public ResponseEntity<DetalleCargue> partialUpdateDetalleCargue(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody DetalleCargue detalleCargue
  ) throws URISyntaxException {
    log.debug("REST request to partial update DetalleCargue partially : {}, {}", id, detalleCargue);
    if (detalleCargue.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, detalleCargue.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!detalleCargueRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<DetalleCargue> result = detalleCargueService.partialUpdate(detalleCargue);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detalleCargue.getId().toString())
    );
  }

  /**
   * {@code GET  /detalle-cargues} : get all the detalleCargues.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detalleCargues in body.
   */
  @GetMapping("/detalle-cargues")
  public List<DetalleCargue> getAllDetalleCargues() {
    log.debug("REST request to get all DetalleCargues");
    return detalleCargueService.findAll();
  }

  /**
   * {@code GET  /detalle-cargues/:id} : get the "id" detalleCargue.
   *
   * @param id the id of the detalleCargue to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detalleCargue, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/detalle-cargues/{id}")
  public ResponseEntity<DetalleCargue> getDetalleCargue(@PathVariable Long id) {
    log.debug("REST request to get DetalleCargue : {}", id);
    Optional<DetalleCargue> detalleCargue = detalleCargueService.findOne(id);
    return ResponseUtil.wrapOrNotFound(detalleCargue);
  }

  /**
   * {@code DELETE  /detalle-cargues/:id} : delete the "id" detalleCargue.
   *
   * @param id the id of the detalleCargue to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/detalle-cargues/{id}")
  public ResponseEntity<Void> deleteDetalleCargue(@PathVariable Long id) {
    log.debug("REST request to delete DetalleCargue : {}", id);
    detalleCargueService.delete(id);
    return ResponseEntity
      .noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}

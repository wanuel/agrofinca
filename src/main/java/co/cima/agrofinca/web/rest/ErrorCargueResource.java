package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.domain.ErrorCargue;
import co.cima.agrofinca.repository.ErrorCargueRepository;
import co.cima.agrofinca.service.ErrorCargueService;
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
 * REST controller for managing {@link co.cima.agrofinca.domain.ErrorCargue}.
 */
@RestController
@RequestMapping("/api")
public class ErrorCargueResource {

  private final Logger log = LoggerFactory.getLogger(ErrorCargueResource.class);

  private static final String ENTITY_NAME = "errorCargue";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final ErrorCargueService errorCargueService;

  private final ErrorCargueRepository errorCargueRepository;

  public ErrorCargueResource(ErrorCargueService errorCargueService, ErrorCargueRepository errorCargueRepository) {
    this.errorCargueService = errorCargueService;
    this.errorCargueRepository = errorCargueRepository;
  }

  /**
   * {@code POST  /error-cargues} : Create a new errorCargue.
   *
   * @param errorCargue the errorCargue to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new errorCargue, or with status {@code 400 (Bad Request)} if the errorCargue has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/error-cargues")
  public ResponseEntity<ErrorCargue> createErrorCargue(@Valid @RequestBody ErrorCargue errorCargue) throws URISyntaxException {
    log.debug("REST request to save ErrorCargue : {}", errorCargue);
    if (errorCargue.getId() != null) {
      throw new BadRequestAlertException("A new errorCargue cannot already have an ID", ENTITY_NAME, "idexists");
    }
    ErrorCargue result = errorCargueService.save(errorCargue);
    return ResponseEntity
      .created(new URI("/api/error-cargues/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /error-cargues/:id} : Updates an existing errorCargue.
   *
   * @param id the id of the errorCargue to save.
   * @param errorCargue the errorCargue to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated errorCargue,
   * or with status {@code 400 (Bad Request)} if the errorCargue is not valid,
   * or with status {@code 500 (Internal Server Error)} if the errorCargue couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/error-cargues/{id}")
  public ResponseEntity<ErrorCargue> updateErrorCargue(
    @PathVariable(value = "id", required = false) final Long id,
    @Valid @RequestBody ErrorCargue errorCargue
  ) throws URISyntaxException {
    log.debug("REST request to update ErrorCargue : {}, {}", id, errorCargue);
    if (errorCargue.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, errorCargue.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!errorCargueRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    ErrorCargue result = errorCargueService.save(errorCargue);
    return ResponseEntity
      .ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, errorCargue.getId().toString()))
      .body(result);
  }

  /**
   * {@code PATCH  /error-cargues/:id} : Partial updates given fields of an existing errorCargue, field will ignore if it is null
   *
   * @param id the id of the errorCargue to save.
   * @param errorCargue the errorCargue to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated errorCargue,
   * or with status {@code 400 (Bad Request)} if the errorCargue is not valid,
   * or with status {@code 404 (Not Found)} if the errorCargue is not found,
   * or with status {@code 500 (Internal Server Error)} if the errorCargue couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/error-cargues/{id}", consumes = "application/merge-patch+json")
  public ResponseEntity<ErrorCargue> partialUpdateErrorCargue(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody ErrorCargue errorCargue
  ) throws URISyntaxException {
    log.debug("REST request to partial update ErrorCargue partially : {}, {}", id, errorCargue);
    if (errorCargue.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, errorCargue.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!errorCargueRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<ErrorCargue> result = errorCargueService.partialUpdate(errorCargue);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, errorCargue.getId().toString())
    );
  }

  /**
   * {@code GET  /error-cargues} : get all the errorCargues.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of errorCargues in body.
   */
  @GetMapping("/error-cargues")
  public List<ErrorCargue> getAllErrorCargues() {
    log.debug("REST request to get all ErrorCargues");
    return errorCargueService.findAll();
  }

  /**
   * {@code GET  /error-cargues/:id} : get the "id" errorCargue.
   *
   * @param id the id of the errorCargue to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the errorCargue, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/error-cargues/{id}")
  public ResponseEntity<ErrorCargue> getErrorCargue(@PathVariable Long id) {
    log.debug("REST request to get ErrorCargue : {}", id);
    Optional<ErrorCargue> errorCargue = errorCargueService.findOne(id);
    return ResponseUtil.wrapOrNotFound(errorCargue);
  }

  /**
   * {@code DELETE  /error-cargues/:id} : delete the "id" errorCargue.
   *
   * @param id the id of the errorCargue to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/error-cargues/{id}")
  public ResponseEntity<Void> deleteErrorCargue(@PathVariable Long id) {
    log.debug("REST request to delete ErrorCargue : {}", id);
    errorCargueService.delete(id);
    return ResponseEntity
      .noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}

package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.domain.Cargue;
import co.cima.agrofinca.repository.CargueRepository;
import co.cima.agrofinca.service.CargueService;
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
 * REST controller for managing {@link co.cima.agrofinca.domain.Cargue}.
 */
@RestController
@RequestMapping("/api")
public class CargueResource {

  private final Logger log = LoggerFactory.getLogger(CargueResource.class);

  private static final String ENTITY_NAME = "cargue";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final CargueService cargueService;

  private final CargueRepository cargueRepository;

  public CargueResource(CargueService cargueService, CargueRepository cargueRepository) {
    this.cargueService = cargueService;
    this.cargueRepository = cargueRepository;
  }

  /**
   * {@code POST  /cargues} : Create a new cargue.
   *
   * @param cargue the cargue to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cargue, or with status {@code 400 (Bad Request)} if the cargue has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/cargues")
  public ResponseEntity<Cargue> createCargue(@Valid @RequestBody Cargue cargue) throws URISyntaxException {
    log.debug("REST request to save Cargue : {}", cargue);
    if (cargue.getId() != null) {
      throw new BadRequestAlertException("A new cargue cannot already have an ID", ENTITY_NAME, "idexists");
    }
    Cargue result = cargueService.save(cargue);
    return ResponseEntity
      .created(new URI("/api/cargues/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /cargues/:id} : Updates an existing cargue.
   *
   * @param id the id of the cargue to save.
   * @param cargue the cargue to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cargue,
   * or with status {@code 400 (Bad Request)} if the cargue is not valid,
   * or with status {@code 500 (Internal Server Error)} if the cargue couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/cargues/{id}")
  public ResponseEntity<Cargue> updateCargue(
    @PathVariable(value = "id", required = false) final Long id,
    @Valid @RequestBody Cargue cargue
  ) throws URISyntaxException {
    log.debug("REST request to update Cargue : {}, {}", id, cargue);
    if (cargue.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, cargue.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!cargueRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Cargue result = cargueService.save(cargue);
    return ResponseEntity
      .ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cargue.getId().toString()))
      .body(result);
  }

  /**
   * {@code PATCH  /cargues/:id} : Partial updates given fields of an existing cargue, field will ignore if it is null
   *
   * @param id the id of the cargue to save.
   * @param cargue the cargue to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cargue,
   * or with status {@code 400 (Bad Request)} if the cargue is not valid,
   * or with status {@code 404 (Not Found)} if the cargue is not found,
   * or with status {@code 500 (Internal Server Error)} if the cargue couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/cargues/{id}", consumes = "application/merge-patch+json")
  public ResponseEntity<Cargue> partialUpdateCargue(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody Cargue cargue
  ) throws URISyntaxException {
    log.debug("REST request to partial update Cargue partially : {}, {}", id, cargue);
    if (cargue.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, cargue.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!cargueRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<Cargue> result = cargueService.partialUpdate(cargue);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cargue.getId().toString())
    );
  }

  /**
   * {@code GET  /cargues} : get all the cargues.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cargues in body.
   */
  @GetMapping("/cargues")
  public List<Cargue> getAllCargues() {
    log.debug("REST request to get all Cargues");
    return cargueService.findAll();
  }

  /**
   * {@code GET  /cargues/:id} : get the "id" cargue.
   *
   * @param id the id of the cargue to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cargue, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/cargues/{id}")
  public ResponseEntity<Cargue> getCargue(@PathVariable Long id) {
    log.debug("REST request to get Cargue : {}", id);
    Optional<Cargue> cargue = cargueService.findOne(id);
    return ResponseUtil.wrapOrNotFound(cargue);
  }

  /**
   * {@code DELETE  /cargues/:id} : delete the "id" cargue.
   *
   * @param id the id of the cargue to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/cargues/{id}")
  public ResponseEntity<Void> deleteCargue(@PathVariable Long id) {
    log.debug("REST request to delete Cargue : {}", id);
    cargueService.delete(id);
    return ResponseEntity
      .noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}

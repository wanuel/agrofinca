package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.domain.Finca;
import co.cima.agrofinca.repository.FincaRepository;
import co.cima.agrofinca.service.FincaService;
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
 * REST controller for managing {@link co.cima.agrofinca.domain.Finca}.
 */
@RestController
@RequestMapping("/api")
public class FincaResource {

  private final Logger log = LoggerFactory.getLogger(FincaResource.class);

  private static final String ENTITY_NAME = "finca";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final FincaService fincaService;

  private final FincaRepository fincaRepository;

  public FincaResource(FincaService fincaService, FincaRepository fincaRepository) {
    this.fincaService = fincaService;
    this.fincaRepository = fincaRepository;
  }

  /**
   * {@code POST  /fincas} : Create a new finca.
   *
   * @param finca the finca to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new finca, or with status {@code 400 (Bad Request)} if the finca has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/fincas")
  public ResponseEntity<Finca> createFinca(@Valid @RequestBody Finca finca) throws URISyntaxException {
    log.debug("REST request to save Finca : {}", finca);
    if (finca.getId() != null) {
      throw new BadRequestAlertException("A new finca cannot already have an ID", ENTITY_NAME, "idexists");
    }
    Finca result = fincaService.save(finca);
    return ResponseEntity
      .created(new URI("/api/fincas/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /fincas/:id} : Updates an existing finca.
   *
   * @param id the id of the finca to save.
   * @param finca the finca to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated finca,
   * or with status {@code 400 (Bad Request)} if the finca is not valid,
   * or with status {@code 500 (Internal Server Error)} if the finca couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/fincas/{id}")
  public ResponseEntity<Finca> updateFinca(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Finca finca)
    throws URISyntaxException {
    log.debug("REST request to update Finca : {}, {}", id, finca);
    if (finca.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, finca.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!fincaRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Finca result = fincaService.save(finca);
    return ResponseEntity
      .ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, finca.getId().toString()))
      .body(result);
  }

  /**
   * {@code PATCH  /fincas/:id} : Partial updates given fields of an existing finca, field will ignore if it is null
   *
   * @param id the id of the finca to save.
   * @param finca the finca to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated finca,
   * or with status {@code 400 (Bad Request)} if the finca is not valid,
   * or with status {@code 404 (Not Found)} if the finca is not found,
   * or with status {@code 500 (Internal Server Error)} if the finca couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/fincas/{id}", consumes = "application/merge-patch+json")
  public ResponseEntity<Finca> partialUpdateFinca(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody Finca finca
  ) throws URISyntaxException {
    log.debug("REST request to partial update Finca partially : {}, {}", id, finca);
    if (finca.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, finca.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!fincaRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<Finca> result = fincaService.partialUpdate(finca);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, finca.getId().toString())
    );
  }

  /**
   * {@code GET  /fincas} : get all the fincas.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fincas in body.
   */
  @GetMapping("/fincas")
  public ResponseEntity<List<Finca>> getAllFincas(Pageable pageable) {
    log.debug("REST request to get a page of Fincas");
    Page<Finca> page = fincaService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /fincas/:id} : get the "id" finca.
   *
   * @param id the id of the finca to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the finca, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/fincas/{id}")
  public ResponseEntity<Finca> getFinca(@PathVariable Long id) {
    log.debug("REST request to get Finca : {}", id);
    Optional<Finca> finca = fincaService.findOne(id);
    return ResponseUtil.wrapOrNotFound(finca);
  }

  /**
   * {@code DELETE  /fincas/:id} : delete the "id" finca.
   *
   * @param id the id of the finca to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/fincas/{id}")
  public ResponseEntity<Void> deleteFinca(@PathVariable Long id) {
    log.debug("REST request to delete Finca : {}", id);
    fincaService.delete(id);
    return ResponseEntity
      .noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}

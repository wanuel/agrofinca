package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.domain.ParametroDominio;
import co.cima.agrofinca.repository.ParametroDominioRepository;
import co.cima.agrofinca.service.ParametroDominioService;
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
 * REST controller for managing {@link co.cima.agrofinca.domain.ParametroDominio}.
 */
@RestController
@RequestMapping("/api")
public class ParametroDominioResource {

  private final Logger log = LoggerFactory.getLogger(ParametroDominioResource.class);

  private static final String ENTITY_NAME = "parametroDominio";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final ParametroDominioService parametroDominioService;

  private final ParametroDominioRepository parametroDominioRepository;

  public ParametroDominioResource(ParametroDominioService parametroDominioService, ParametroDominioRepository parametroDominioRepository) {
    this.parametroDominioService = parametroDominioService;
    this.parametroDominioRepository = parametroDominioRepository;
  }

  /**
   * {@code POST  /parametro-dominios} : Create a new parametroDominio.
   *
   * @param parametroDominio the parametroDominio to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parametroDominio, or with status {@code 400 (Bad Request)} if the parametroDominio has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/parametro-dominios")
  public ResponseEntity<ParametroDominio> createParametroDominio(@Valid @RequestBody ParametroDominio parametroDominio)
    throws URISyntaxException {
    log.debug("REST request to save ParametroDominio : {}", parametroDominio);
    if (parametroDominio.getId() != null) {
      throw new BadRequestAlertException("A new parametroDominio cannot already have an ID", ENTITY_NAME, "idexists");
    }
    ParametroDominio result = parametroDominioService.save(parametroDominio);
    return ResponseEntity
      .created(new URI("/api/parametro-dominios/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /parametro-dominios/:id} : Updates an existing parametroDominio.
   *
   * @param id the id of the parametroDominio to save.
   * @param parametroDominio the parametroDominio to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parametroDominio,
   * or with status {@code 400 (Bad Request)} if the parametroDominio is not valid,
   * or with status {@code 500 (Internal Server Error)} if the parametroDominio couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/parametro-dominios/{id}")
  public ResponseEntity<ParametroDominio> updateParametroDominio(
    @PathVariable(value = "id", required = false) final Long id,
    @Valid @RequestBody ParametroDominio parametroDominio
  ) throws URISyntaxException {
    log.debug("REST request to update ParametroDominio : {}, {}", id, parametroDominio);
    if (parametroDominio.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, parametroDominio.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!parametroDominioRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    ParametroDominio result = parametroDominioService.save(parametroDominio);
    return ResponseEntity
      .ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parametroDominio.getId().toString()))
      .body(result);
  }

  /**
   * {@code PATCH  /parametro-dominios/:id} : Partial updates given fields of an existing parametroDominio, field will ignore if it is null
   *
   * @param id the id of the parametroDominio to save.
   * @param parametroDominio the parametroDominio to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parametroDominio,
   * or with status {@code 400 (Bad Request)} if the parametroDominio is not valid,
   * or with status {@code 404 (Not Found)} if the parametroDominio is not found,
   * or with status {@code 500 (Internal Server Error)} if the parametroDominio couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/parametro-dominios/{id}", consumes = "application/merge-patch+json")
  public ResponseEntity<ParametroDominio> partialUpdateParametroDominio(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody ParametroDominio parametroDominio
  ) throws URISyntaxException {
    log.debug("REST request to partial update ParametroDominio partially : {}, {}", id, parametroDominio);
    if (parametroDominio.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, parametroDominio.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!parametroDominioRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<ParametroDominio> result = parametroDominioService.partialUpdate(parametroDominio);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parametroDominio.getId().toString())
    );
  }

  /**
   * {@code GET  /parametro-dominios} : get all the parametroDominios.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parametroDominios in body.
   */
  @GetMapping("/parametro-dominios")
  public List<ParametroDominio> getAllParametroDominios() {
    log.debug("REST request to get all ParametroDominios");
    return parametroDominioService.findAll();
  }

  /**
   * {@code GET  /parametro-dominios/:id} : get the "id" parametroDominio.
   *
   * @param id the id of the parametroDominio to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parametroDominio, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/parametro-dominios/{id}")
  public ResponseEntity<ParametroDominio> getParametroDominio(@PathVariable Long id) {
    log.debug("REST request to get ParametroDominio : {}", id);
    Optional<ParametroDominio> parametroDominio = parametroDominioService.findOne(id);
    return ResponseUtil.wrapOrNotFound(parametroDominio);
  }

  /**
   * {@code DELETE  /parametro-dominios/:id} : delete the "id" parametroDominio.
   *
   * @param id the id of the parametroDominio to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/parametro-dominios/{id}")
  public ResponseEntity<Void> deleteParametroDominio(@PathVariable Long id) {
    log.debug("REST request to delete ParametroDominio : {}", id);
    parametroDominioService.delete(id);
    return ResponseEntity
      .noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}

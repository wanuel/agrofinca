package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.domain.TipoParametro;
import co.cima.agrofinca.repository.TipoParametroRepository;
import co.cima.agrofinca.service.TipoParametroService;
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
 * REST controller for managing {@link co.cima.agrofinca.domain.TipoParametro}.
 */
@RestController
@RequestMapping("/api")
public class TipoParametroResource {

  private final Logger log = LoggerFactory.getLogger(TipoParametroResource.class);

  private static final String ENTITY_NAME = "tipoParametro";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final TipoParametroService tipoParametroService;

  private final TipoParametroRepository tipoParametroRepository;

  public TipoParametroResource(TipoParametroService tipoParametroService, TipoParametroRepository tipoParametroRepository) {
    this.tipoParametroService = tipoParametroService;
    this.tipoParametroRepository = tipoParametroRepository;
  }

  /**
   * {@code POST  /tipo-parametros} : Create a new tipoParametro.
   *
   * @param tipoParametro the tipoParametro to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoParametro, or with status {@code 400 (Bad Request)} if the tipoParametro has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/tipo-parametros")
  public ResponseEntity<TipoParametro> createTipoParametro(@Valid @RequestBody TipoParametro tipoParametro) throws URISyntaxException {
    log.debug("REST request to save TipoParametro : {}", tipoParametro);
    if (tipoParametro.getId() != null) {
      throw new BadRequestAlertException("A new tipoParametro cannot already have an ID", ENTITY_NAME, "idexists");
    }
    TipoParametro result = tipoParametroService.save(tipoParametro);
    return ResponseEntity
      .created(new URI("/api/tipo-parametros/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /tipo-parametros/:id} : Updates an existing tipoParametro.
   *
   * @param id the id of the tipoParametro to save.
   * @param tipoParametro the tipoParametro to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoParametro,
   * or with status {@code 400 (Bad Request)} if the tipoParametro is not valid,
   * or with status {@code 500 (Internal Server Error)} if the tipoParametro couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/tipo-parametros/{id}")
  public ResponseEntity<TipoParametro> updateTipoParametro(
    @PathVariable(value = "id", required = false) final Long id,
    @Valid @RequestBody TipoParametro tipoParametro
  ) throws URISyntaxException {
    log.debug("REST request to update TipoParametro : {}, {}", id, tipoParametro);
    if (tipoParametro.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, tipoParametro.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!tipoParametroRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    TipoParametro result = tipoParametroService.save(tipoParametro);
    return ResponseEntity
      .ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoParametro.getId().toString()))
      .body(result);
  }

  /**
   * {@code PATCH  /tipo-parametros/:id} : Partial updates given fields of an existing tipoParametro, field will ignore if it is null
   *
   * @param id the id of the tipoParametro to save.
   * @param tipoParametro the tipoParametro to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoParametro,
   * or with status {@code 400 (Bad Request)} if the tipoParametro is not valid,
   * or with status {@code 404 (Not Found)} if the tipoParametro is not found,
   * or with status {@code 500 (Internal Server Error)} if the tipoParametro couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/tipo-parametros/{id}", consumes = "application/merge-patch+json")
  public ResponseEntity<TipoParametro> partialUpdateTipoParametro(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody TipoParametro tipoParametro
  ) throws URISyntaxException {
    log.debug("REST request to partial update TipoParametro partially : {}, {}", id, tipoParametro);
    if (tipoParametro.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, tipoParametro.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!tipoParametroRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<TipoParametro> result = tipoParametroService.partialUpdate(tipoParametro);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoParametro.getId().toString())
    );
  }

  /**
   * {@code GET  /tipo-parametros} : get all the tipoParametros.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoParametros in body.
   */
  @GetMapping("/tipo-parametros")
  public List<TipoParametro> getAllTipoParametros() {
    log.debug("REST request to get all TipoParametros");
    return tipoParametroService.findAll();
  }

  /**
   * {@code GET  /tipo-parametros/:id} : get the "id" tipoParametro.
   *
   * @param id the id of the tipoParametro to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoParametro, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/tipo-parametros/{id}")
  public ResponseEntity<TipoParametro> getTipoParametro(@PathVariable Long id) {
    log.debug("REST request to get TipoParametro : {}", id);
    Optional<TipoParametro> tipoParametro = tipoParametroService.findOne(id);
    return ResponseUtil.wrapOrNotFound(tipoParametro);
  }

  /**
   * {@code DELETE  /tipo-parametros/:id} : delete the "id" tipoParametro.
   *
   * @param id the id of the tipoParametro to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/tipo-parametros/{id}")
  public ResponseEntity<Void> deleteTipoParametro(@PathVariable Long id) {
    log.debug("REST request to delete TipoParametro : {}", id);
    tipoParametroService.delete(id);
    return ResponseEntity
      .noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}

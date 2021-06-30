package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.domain.Annimal;
import co.cima.agrofinca.repository.AnnimalRepository;
import co.cima.agrofinca.service.AnnimalService;
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
 * REST controller for managing {@link co.cima.agrofinca.domain.Annimal}.
 */
@RestController
@RequestMapping("/api")
public class AnnimalResource {

  private final Logger log = LoggerFactory.getLogger(AnnimalResource.class);

  private static final String ENTITY_NAME = "annimal";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final AnnimalService annimalService;

  private final AnnimalRepository annimalRepository;

  public AnnimalResource(AnnimalService annimalService, AnnimalRepository annimalRepository) {
    this.annimalService = annimalService;
    this.annimalRepository = annimalRepository;
  }

  /**
   * {@code POST  /annimals} : Create a new annimal.
   *
   * @param annimal the annimal to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new annimal, or with status {@code 400 (Bad Request)} if the annimal has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/annimals")
  public ResponseEntity<Annimal> createAnnimal(@Valid @RequestBody Annimal annimal) throws URISyntaxException {
    log.debug("REST request to save Annimal : {}", annimal);
    if (annimal.getId() != null) {
      throw new BadRequestAlertException("A new annimal cannot already have an ID", ENTITY_NAME, "idexists");
    }
    Annimal result = annimalService.save(annimal);
    return ResponseEntity
      .created(new URI("/api/annimals/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /annimals/:id} : Updates an existing annimal.
   *
   * @param id the id of the annimal to save.
   * @param annimal the annimal to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated annimal,
   * or with status {@code 400 (Bad Request)} if the annimal is not valid,
   * or with status {@code 500 (Internal Server Error)} if the annimal couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/annimals/{id}")
  public ResponseEntity<Annimal> updateAnnimal(
    @PathVariable(value = "id", required = false) final Long id,
    @Valid @RequestBody Annimal annimal
  ) throws URISyntaxException {
    log.debug("REST request to update Annimal : {}, {}", id, annimal);
    if (annimal.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, annimal.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!annimalRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Annimal result = annimalService.save(annimal);
    return ResponseEntity
      .ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, annimal.getId().toString()))
      .body(result);
  }

  /**
   * {@code PATCH  /annimals/:id} : Partial updates given fields of an existing annimal, field will ignore if it is null
   *
   * @param id the id of the annimal to save.
   * @param annimal the annimal to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated annimal,
   * or with status {@code 400 (Bad Request)} if the annimal is not valid,
   * or with status {@code 404 (Not Found)} if the annimal is not found,
   * or with status {@code 500 (Internal Server Error)} if the annimal couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/annimals/{id}", consumes = "application/merge-patch+json")
  public ResponseEntity<Annimal> partialUpdateAnnimal(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody Annimal annimal
  ) throws URISyntaxException {
    log.debug("REST request to partial update Annimal partially : {}, {}", id, annimal);
    if (annimal.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, annimal.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!annimalRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<Annimal> result = annimalService.partialUpdate(annimal);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, annimal.getId().toString())
    );
  }

  /**
   * {@code GET  /annimals} : get all the annimals.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of annimals in body.
   */
  @GetMapping("/annimals")
  public ResponseEntity<List<Annimal>> getAllAnnimals(Pageable pageable) {
    log.debug("REST request to get a page of Annimals");
    Page<Annimal> page = annimalService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /annimals/:id} : get the "id" annimal.
   *
   * @param id the id of the annimal to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the annimal, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/annimals/{id}")
  public ResponseEntity<Annimal> getAnnimal(@PathVariable Long id) {
    log.debug("REST request to get Annimal : {}", id);
    Optional<Annimal> annimal = annimalService.findOne(id);
    return ResponseUtil.wrapOrNotFound(annimal);
  }

  /**
   * {@code DELETE  /annimals/:id} : delete the "id" annimal.
   *
   * @param id the id of the annimal to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/annimals/{id}")
  public ResponseEntity<Void> deleteAnnimal(@PathVariable Long id) {
    log.debug("REST request to delete Annimal : {}", id);
    annimalService.delete(id);
    return ResponseEntity
      .noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}

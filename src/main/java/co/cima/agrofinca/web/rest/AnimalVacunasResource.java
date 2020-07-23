package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.domain.AnimalVacunas;
import co.cima.agrofinca.service.AnimalVacunasService;
import co.cima.agrofinca.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.cima.agrofinca.domain.AnimalVacunas}.
 */
@RestController
@RequestMapping("/api")
public class AnimalVacunasResource {

    private final Logger log = LoggerFactory.getLogger(AnimalVacunasResource.class);

    private static final String ENTITY_NAME = "animalVacunas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnimalVacunasService animalVacunasService;

    public AnimalVacunasResource(AnimalVacunasService animalVacunasService) {
        this.animalVacunasService = animalVacunasService;
    }

    /**
     * {@code POST  /animal-vacunas} : Create a new animalVacunas.
     *
     * @param animalVacunas the animalVacunas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new animalVacunas, or with status {@code 400 (Bad Request)} if the animalVacunas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/animal-vacunas")
    public ResponseEntity<AnimalVacunas> createAnimalVacunas(@Valid @RequestBody AnimalVacunas animalVacunas) throws URISyntaxException {
        log.debug("REST request to save AnimalVacunas : {}", animalVacunas);
        if (animalVacunas.getId() != null) {
            throw new BadRequestAlertException("A new animalVacunas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnimalVacunas result = animalVacunasService.save(animalVacunas);
        return ResponseEntity.created(new URI("/api/animal-vacunas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /animal-vacunas} : Updates an existing animalVacunas.
     *
     * @param animalVacunas the animalVacunas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animalVacunas,
     * or with status {@code 400 (Bad Request)} if the animalVacunas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the animalVacunas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/animal-vacunas")
    public ResponseEntity<AnimalVacunas> updateAnimalVacunas(@Valid @RequestBody AnimalVacunas animalVacunas) throws URISyntaxException {
        log.debug("REST request to update AnimalVacunas : {}", animalVacunas);
        if (animalVacunas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnimalVacunas result = animalVacunasService.save(animalVacunas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animalVacunas.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /animal-vacunas} : get all the animalVacunas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of animalVacunas in body.
     */
    @GetMapping("/animal-vacunas")
    public ResponseEntity<List<AnimalVacunas>> getAllAnimalVacunas(Pageable pageable) {
        log.debug("REST request to get a page of AnimalVacunas");
        Page<AnimalVacunas> page = animalVacunasService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /animal-vacunas/:id} : get the "id" animalVacunas.
     *
     * @param id the id of the animalVacunas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the animalVacunas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/animal-vacunas/{id}")
    public ResponseEntity<AnimalVacunas> getAnimalVacunas(@PathVariable Long id) {
        log.debug("REST request to get AnimalVacunas : {}", id);
        Optional<AnimalVacunas> animalVacunas = animalVacunasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(animalVacunas);
    }

    /**
     * {@code DELETE  /animal-vacunas/:id} : delete the "id" animalVacunas.
     *
     * @param id the id of the animalVacunas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/animal-vacunas/{id}")
    public ResponseEntity<Void> deleteAnimalVacunas(@PathVariable Long id) {
        log.debug("REST request to delete AnimalVacunas : {}", id);
        animalVacunasService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

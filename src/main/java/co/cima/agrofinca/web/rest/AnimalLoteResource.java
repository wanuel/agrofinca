package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.domain.AnimalLote;
import co.cima.agrofinca.service.AnimalLoteService;
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
 * REST controller for managing {@link co.cima.agrofinca.domain.AnimalLote}.
 */
@RestController
@RequestMapping("/api")
public class AnimalLoteResource {

    private final Logger log = LoggerFactory.getLogger(AnimalLoteResource.class);

    private static final String ENTITY_NAME = "animalLote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnimalLoteService animalLoteService;

    public AnimalLoteResource(AnimalLoteService animalLoteService) {
        this.animalLoteService = animalLoteService;
    }

    /**
     * {@code POST  /animal-lotes} : Create a new animalLote.
     *
     * @param animalLote the animalLote to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new animalLote, or with status {@code 400 (Bad Request)} if the animalLote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/animal-lotes")
    public ResponseEntity<AnimalLote> createAnimalLote(@Valid @RequestBody AnimalLote animalLote) throws URISyntaxException {
        log.debug("REST request to save AnimalLote : {}", animalLote);
        if (animalLote.getId() != null) {
            throw new BadRequestAlertException("A new animalLote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnimalLote result = animalLoteService.save(animalLote);
        return ResponseEntity.created(new URI("/api/animal-lotes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /animal-lotes} : Updates an existing animalLote.
     *
     * @param animalLote the animalLote to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animalLote,
     * or with status {@code 400 (Bad Request)} if the animalLote is not valid,
     * or with status {@code 500 (Internal Server Error)} if the animalLote couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/animal-lotes")
    public ResponseEntity<AnimalLote> updateAnimalLote(@Valid @RequestBody AnimalLote animalLote) throws URISyntaxException {
        log.debug("REST request to update AnimalLote : {}", animalLote);
        if (animalLote.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnimalLote result = animalLoteService.save(animalLote);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animalLote.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /animal-lotes} : get all the animalLotes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of animalLotes in body.
     */
    @GetMapping("/animal-lotes")
    public ResponseEntity<List<AnimalLote>> getAllAnimalLotes(Pageable pageable) {
        log.debug("REST request to get a page of AnimalLotes");
        Page<AnimalLote> page = animalLoteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /animal-lotes/:id} : get the "id" animalLote.
     *
     * @param id the id of the animalLote to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the animalLote, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/animal-lotes/{id}")
    public ResponseEntity<AnimalLote> getAnimalLote(@PathVariable Long id) {
        log.debug("REST request to get AnimalLote : {}", id);
        Optional<AnimalLote> animalLote = animalLoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(animalLote);
    }

    /**
     * {@code DELETE  /animal-lotes/:id} : delete the "id" animalLote.
     *
     * @param id the id of the animalLote to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/animal-lotes/{id}")
    public ResponseEntity<Void> deleteAnimalLote(@PathVariable Long id) {
        log.debug("REST request to delete AnimalLote : {}", id);
        animalLoteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

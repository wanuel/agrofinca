package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.domain.AnimalEvento;
import co.cima.agrofinca.service.AnimalEventoService;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.cima.agrofinca.domain.AnimalEvento}.
 */
@RestController
@RequestMapping("/api")
public class AnimalEventoResource {

    private final Logger log = LoggerFactory.getLogger(AnimalEventoResource.class);

    private static final String ENTITY_NAME = "animalEvento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnimalEventoService animalEventoService;

    public AnimalEventoResource(AnimalEventoService animalEventoService) {
        this.animalEventoService = animalEventoService;
    }

    /**
     * {@code POST  /animal-eventos} : Create a new animalEvento.
     *
     * @param animalEvento the animalEvento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new animalEvento, or with status {@code 400 (Bad Request)} if the animalEvento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/animal-eventos")
    public ResponseEntity<AnimalEvento> createAnimalEvento(@RequestBody AnimalEvento animalEvento) throws URISyntaxException {
        log.debug("REST request to save AnimalEvento : {}", animalEvento);
        if (animalEvento.getId() != null) {
            throw new BadRequestAlertException("A new animalEvento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnimalEvento result = animalEventoService.save(animalEvento);
        return ResponseEntity.created(new URI("/api/animal-eventos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /animal-eventos} : Updates an existing animalEvento.
     *
     * @param animalEvento the animalEvento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animalEvento,
     * or with status {@code 400 (Bad Request)} if the animalEvento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the animalEvento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/animal-eventos")
    public ResponseEntity<AnimalEvento> updateAnimalEvento(@RequestBody AnimalEvento animalEvento) throws URISyntaxException {
        log.debug("REST request to update AnimalEvento : {}", animalEvento);
        if (animalEvento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnimalEvento result = animalEventoService.save(animalEvento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animalEvento.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /animal-eventos} : get all the animalEventos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of animalEventos in body.
     */
    @GetMapping("/animal-eventos")
    public ResponseEntity<List<AnimalEvento>> getAllAnimalEventos(Pageable pageable) {
        log.debug("REST request to get a page of AnimalEventos");
        Page<AnimalEvento> page = animalEventoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /animal-eventos/:id} : get the "id" animalEvento.
     *
     * @param id the id of the animalEvento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the animalEvento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/animal-eventos/{id}")
    public ResponseEntity<AnimalEvento> getAnimalEvento(@PathVariable Long id) {
        log.debug("REST request to get AnimalEvento : {}", id);
        Optional<AnimalEvento> animalEvento = animalEventoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(animalEvento);
    }

    /**
     * {@code DELETE  /animal-eventos/:id} : delete the "id" animalEvento.
     *
     * @param id the id of the animalEvento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/animal-eventos/{id}")
    public ResponseEntity<Void> deleteAnimalEvento(@PathVariable Long id) {
        log.debug("REST request to delete AnimalEvento : {}", id);
        animalEventoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

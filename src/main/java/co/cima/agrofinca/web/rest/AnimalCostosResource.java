package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.domain.AnimalCostos;
import co.cima.agrofinca.service.AnimalCostosService;
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
 * REST controller for managing {@link co.cima.agrofinca.domain.AnimalCostos}.
 */
@RestController
@RequestMapping("/api")
public class AnimalCostosResource {

    private final Logger log = LoggerFactory.getLogger(AnimalCostosResource.class);

    private static final String ENTITY_NAME = "animalCostos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnimalCostosService animalCostosService;

    public AnimalCostosResource(AnimalCostosService animalCostosService) {
        this.animalCostosService = animalCostosService;
    }

    /**
     * {@code POST  /animal-costos} : Create a new animalCostos.
     *
     * @param animalCostos the animalCostos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new animalCostos, or with status {@code 400 (Bad Request)} if the animalCostos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/animal-costos")
    public ResponseEntity<AnimalCostos> createAnimalCostos(@Valid @RequestBody AnimalCostos animalCostos) throws URISyntaxException {
        log.debug("REST request to save AnimalCostos : {}", animalCostos);
        if (animalCostos.getId() != null) {
            throw new BadRequestAlertException("A new animalCostos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnimalCostos result = animalCostosService.save(animalCostos);
        return ResponseEntity.created(new URI("/api/animal-costos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /animal-costos} : Updates an existing animalCostos.
     *
     * @param animalCostos the animalCostos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animalCostos,
     * or with status {@code 400 (Bad Request)} if the animalCostos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the animalCostos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/animal-costos")
    public ResponseEntity<AnimalCostos> updateAnimalCostos(@Valid @RequestBody AnimalCostos animalCostos) throws URISyntaxException {
        log.debug("REST request to update AnimalCostos : {}", animalCostos);
        if (animalCostos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnimalCostos result = animalCostosService.save(animalCostos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, animalCostos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /animal-costos} : get all the animalCostos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of animalCostos in body.
     */
    @GetMapping("/animal-costos")
    public ResponseEntity<List<AnimalCostos>> getAllAnimalCostos(Pageable pageable) {
        log.debug("REST request to get a page of AnimalCostos");
        Page<AnimalCostos> page = animalCostosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /animal-costos/:id} : get the "id" animalCostos.
     *
     * @param id the id of the animalCostos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the animalCostos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/animal-costos/{id}")
    public ResponseEntity<AnimalCostos> getAnimalCostos(@PathVariable Long id) {
        log.debug("REST request to get AnimalCostos : {}", id);
        Optional<AnimalCostos> animalCostos = animalCostosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(animalCostos);
    }

    /**
     * {@code DELETE  /animal-costos/:id} : delete the "id" animalCostos.
     *
     * @param id the id of the animalCostos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/animal-costos/{id}")
    public ResponseEntity<Void> deleteAnimalCostos(@PathVariable Long id) {
        log.debug("REST request to delete AnimalCostos : {}", id);
        animalCostosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

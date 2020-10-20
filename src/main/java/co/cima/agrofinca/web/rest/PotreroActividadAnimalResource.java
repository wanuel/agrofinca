package co.cima.agrofinca.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import co.cima.agrofinca.domain.PotreroActividadAnimal;
import co.cima.agrofinca.service.PotreroActividadAnimalService;
import co.cima.agrofinca.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.cima.agrofinca.domain.PotreroActividadAnimal}.
 */
@RestController
@RequestMapping("/api")
public class PotreroActividadAnimalResource {

    private final Logger log = LoggerFactory.getLogger(PotreroActividadAnimalResource.class);

    private static final String ENTITY_NAME = "potreroActividadAnimal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PotreroActividadAnimalService potreroActividadanimaleservice;

    public PotreroActividadAnimalResource(PotreroActividadAnimalService potreroActividadanimaleservice) {
        this.potreroActividadanimaleservice = potreroActividadanimaleservice;
    }

    /**
     * {@code POST  /potrero-actividad-animales} : Create a new potreroActividadAnimal.
     *
     * @param potreroActividadAnimal the potreroActividadAnimal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new potreroActividadAnimal, or with status {@code 400 (Bad Request)} if the potreroActividadAnimal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/potrero-actividad-animales")
    public ResponseEntity<PotreroActividadAnimal> createPotreroActividadAnimal(@RequestBody PotreroActividadAnimal potreroActividadAnimal) throws URISyntaxException {
        log.debug("REST request to save PotreroActividadAnimal : {}", potreroActividadAnimal);
        if (potreroActividadAnimal.getId() != null) {
            throw new BadRequestAlertException("A new potreroActividadAnimal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PotreroActividadAnimal result = potreroActividadanimaleservice.save(potreroActividadAnimal);
        return ResponseEntity.created(new URI("/api/potrero-actividad-animales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /potrero-actividad-animales} : Updates an existing potreroActividadAnimal.
     *
     * @param potreroActividadAnimal the potreroActividadAnimal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated potreroActividadAnimal,
     * or with status {@code 400 (Bad Request)} if the potreroActividadAnimal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the potreroActividadAnimal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/potrero-actividad-animales")
    public ResponseEntity<PotreroActividadAnimal> updatePotreroActividadAnimal(@RequestBody PotreroActividadAnimal potreroActividadAnimal) throws URISyntaxException {
        log.debug("REST request to update PotreroActividadAnimal : {}", potreroActividadAnimal);
        if (potreroActividadAnimal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PotreroActividadAnimal result = potreroActividadanimaleservice.save(potreroActividadAnimal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, potreroActividadAnimal.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /potrero-actividad-animales} : get all the potreroActividadanimales.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of potreroActividadanimales in body.
     */
    @GetMapping("/potrero-actividad-animales")
    public ResponseEntity<List<PotreroActividadAnimal>> getAllPotreroActividadanimales(Pageable pageable) {
        log.debug("REST request to get a page of PotreroActividadanimales");
        Page<PotreroActividadAnimal> page = potreroActividadanimaleservice.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /potrero-actividad-animales/:id} : get the "id" potreroActividadAnimal.
     *
     * @param id the id of the potreroActividadAnimal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the potreroActividadAnimal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/potrero-actividad-animales/{id}")
    public ResponseEntity<PotreroActividadAnimal> getPotreroActividadAnimal(@PathVariable Long id) {
        log.debug("REST request to get PotreroActividadAnimal : {}", id);
        Optional<PotreroActividadAnimal> potreroActividadAnimal = potreroActividadanimaleservice.findOne(id);
        return ResponseUtil.wrapOrNotFound(potreroActividadAnimal);
    }

    /**
     * {@code DELETE  /potrero-actividad-animales/:id} : delete the "id" potreroActividadAnimal.
     *
     * @param id the id of the potreroActividadAnimal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/potrero-actividad-animales/{id}")
    public ResponseEntity<Void> deletePotreroActividadAnimal(@PathVariable Long id) {
        log.debug("REST request to delete PotreroActividadAnimal : {}", id);
        potreroActividadanimaleservice.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

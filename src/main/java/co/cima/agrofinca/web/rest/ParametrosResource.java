package co.cima.agrofinca.web.rest;

import co.cima.agrofinca.domain.Parametros;
import co.cima.agrofinca.service.ParametrosService;
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
 * REST controller for managing {@link co.cima.agrofinca.domain.Parametros}.
 */
@RestController
@RequestMapping("/api")
public class ParametrosResource {

    private final Logger log = LoggerFactory.getLogger(ParametrosResource.class);

    private static final String ENTITY_NAME = "parametros";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParametrosService parametrosService;

    public ParametrosResource(ParametrosService parametrosService) {
        this.parametrosService = parametrosService;
    }

    /**
     * {@code POST  /parametros} : Create a new parametros.
     *
     * @param parametros the parametros to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parametros, or with status {@code 400 (Bad Request)} if the parametros has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parametros")
    public ResponseEntity<Parametros> createParametros(@Valid @RequestBody Parametros parametros) throws URISyntaxException {
        log.debug("REST request to save Parametros : {}", parametros);
        if (parametros.getId() != null) {
            throw new BadRequestAlertException("A new parametros cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Parametros result = parametrosService.save(parametros);
        return ResponseEntity.created(new URI("/api/parametros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parametros} : Updates an existing parametros.
     *
     * @param parametros the parametros to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parametros,
     * or with status {@code 400 (Bad Request)} if the parametros is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parametros couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parametros")
    public ResponseEntity<Parametros> updateParametros(@Valid @RequestBody Parametros parametros) throws URISyntaxException {
        log.debug("REST request to update Parametros : {}", parametros);
        if (parametros.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Parametros result = parametrosService.save(parametros);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parametros.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /parametros} : get all the parametros.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parametros in body.
     */
    @GetMapping("/parametros")
    public ResponseEntity<List<Parametros>> getAllParametros(Pageable pageable) {
        log.debug("REST request to get a page of Parametros");
        Page<Parametros> page = parametrosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /parametros/:id} : get the "id" parametros.
     *
     * @param id the id of the parametros to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parametros, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parametros/{id}")
    public ResponseEntity<Parametros> getParametros(@PathVariable Long id) {
        log.debug("REST request to get Parametros : {}", id);
        Optional<Parametros> parametros = parametrosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parametros);
    }

    /**
     * {@code DELETE  /parametros/:id} : delete the "id" parametros.
     *
     * @param id the id of the parametros to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parametros/{id}")
    public ResponseEntity<Void> deleteParametros(@PathVariable Long id) {
        log.debug("REST request to delete Parametros : {}", id);
        parametrosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

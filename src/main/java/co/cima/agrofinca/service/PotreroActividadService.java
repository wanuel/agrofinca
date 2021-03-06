package co.cima.agrofinca.service;

import co.cima.agrofinca.domain.PotreroActividad;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link PotreroActividad}.
 */
public interface PotreroActividadService {

    /**
     * Save a potreroActividad.
     *
     * @param potreroActividad the entity to save.
     * @return the persisted entity.
     */
    PotreroActividad save(PotreroActividad potreroActividad);

    /**
     * Get all the potreroActividads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PotreroActividad> findAll(Pageable pageable);

    /**
     * Get all the potreroActividads with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<PotreroActividad> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" potreroActividad.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PotreroActividad> findOne(Long id);

    /**
     * Delete the "id" potreroActividad.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

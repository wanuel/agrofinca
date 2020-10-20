package co.cima.agrofinca.service;

import co.cima.agrofinca.domain.PotreroActividadAnimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link PotreroActividadAnimal}.
 */
public interface PotreroActividadAnimalService {

    /**
     * Save a potreroActividadAnimal.
     *
     * @param potreroActividadAnimal the entity to save.
     * @return the persisted entity.
     */
    PotreroActividadAnimal save(PotreroActividadAnimal potreroActividadAnimal);

    /**
     * Get all the potreroActividadanimales.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PotreroActividadAnimal> findAll(Pageable pageable);


    /**
     * Get the "id" potreroActividadAnimal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PotreroActividadAnimal> findOne(Long id);

    /**
     * Delete the "id" potreroActividadAnimal.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

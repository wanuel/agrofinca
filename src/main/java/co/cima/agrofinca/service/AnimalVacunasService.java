package co.cima.agrofinca.service;

import co.cima.agrofinca.domain.AnimalVacunas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link AnimalVacunas}.
 */
public interface AnimalVacunasService {

    /**
     * Save a animalVacunas.
     *
     * @param animalVacunas the entity to save.
     * @return the persisted entity.
     */
    AnimalVacunas save(AnimalVacunas animalVacunas);

    /**
     * Get all the animalVacunas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnimalVacunas> findAll(Pageable pageable);


    /**
     * Get the "id" animalVacunas.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnimalVacunas> findOne(Long id);

    /**
     * Delete the "id" animalVacunas.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

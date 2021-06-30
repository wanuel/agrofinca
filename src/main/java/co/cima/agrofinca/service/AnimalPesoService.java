package co.cima.agrofinca.service;

import co.cima.agrofinca.domain.AnimalPeso;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link AnimalPeso}.
 */
public interface AnimalPesoService {
  /**
   * Save a animalPeso.
   *
   * @param animalPeso the entity to save.
   * @return the persisted entity.
   */
  AnimalPeso save(AnimalPeso animalPeso);

  /**
   * Partially updates a animalPeso.
   *
   * @param animalPeso the entity to update partially.
   * @return the persisted entity.
   */
  Optional<AnimalPeso> partialUpdate(AnimalPeso animalPeso);

  /**
   * Get all the animalPesos.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<AnimalPeso> findAll(Pageable pageable);

  /**
   * Get the "id" animalPeso.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<AnimalPeso> findOne(Long id);

  /**
   * Delete the "id" animalPeso.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}

package co.cima.agrofinca.service;

import co.cima.agrofinca.domain.AnimalCostos;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link AnimalCostos}.
 */
public interface AnimalCostosService {
  /**
   * Save a animalCostos.
   *
   * @param animalCostos the entity to save.
   * @return the persisted entity.
   */
  AnimalCostos save(AnimalCostos animalCostos);

  /**
   * Partially updates a animalCostos.
   *
   * @param animalCostos the entity to update partially.
   * @return the persisted entity.
   */
  Optional<AnimalCostos> partialUpdate(AnimalCostos animalCostos);

  /**
   * Get all the animalCostos.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<AnimalCostos> findAll(Pageable pageable);

  /**
   * Get the "id" animalCostos.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<AnimalCostos> findOne(Long id);

  /**
   * Delete the "id" animalCostos.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}

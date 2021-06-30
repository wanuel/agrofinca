package co.cima.agrofinca.service;

import co.cima.agrofinca.domain.Animal;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Animal}.
 */
public interface AnimalService {
  /**
   * Save a animal.
   *
   * @param animal the entity to save.
   * @return the persisted entity.
   */
  Animal save(Animal animal);

  /**
   * Partially updates a animal.
   *
   * @param animal the entity to update partially.
   * @return the persisted entity.
   */
  Optional<Animal> partialUpdate(Animal animal);

  /**
   * Get all the animals.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<Animal> findAll(Pageable pageable);

  /**
   * Get the "id" animal.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<Animal> findOne(Long id);

  /**
   * Delete the "id" animal.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}

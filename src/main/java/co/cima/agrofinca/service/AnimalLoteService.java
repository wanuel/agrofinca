package co.cima.agrofinca.service;

import co.cima.agrofinca.domain.AnimalLote;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link AnimalLote}.
 */
public interface AnimalLoteService {
  /**
   * Save a animalLote.
   *
   * @param animalLote the entity to save.
   * @return the persisted entity.
   */
  AnimalLote save(AnimalLote animalLote);

  /**
   * Partially updates a animalLote.
   *
   * @param animalLote the entity to update partially.
   * @return the persisted entity.
   */
  Optional<AnimalLote> partialUpdate(AnimalLote animalLote);

  /**
   * Get all the animalLotes.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<AnimalLote> findAll(Pageable pageable);

  /**
   * Get the "id" animalLote.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<AnimalLote> findOne(Long id);

  /**
   * Delete the "id" animalLote.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}

package co.cima.agrofinca.service;

import co.cima.agrofinca.domain.Annimal;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Annimal}.
 */
public interface AnnimalService {
  /**
   * Save a annimal.
   *
   * @param annimal the entity to save.
   * @return the persisted entity.
   */
  Annimal save(Annimal annimal);

  /**
   * Partially updates a annimal.
   *
   * @param annimal the entity to update partially.
   * @return the persisted entity.
   */
  Optional<Annimal> partialUpdate(Annimal annimal);

  /**
   * Get all the annimals.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<Annimal> findAll(Pageable pageable);

  /**
   * Get the "id" annimal.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<Annimal> findOne(Long id);

  /**
   * Delete the "id" annimal.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
